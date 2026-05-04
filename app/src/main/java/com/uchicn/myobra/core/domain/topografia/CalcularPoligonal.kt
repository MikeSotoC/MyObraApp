package com.uchicn.myobra.core.domain.topografia

import com.uchicn.myobra.core.domain.model.topo.*
import kotlin.math.*

/**
 * Cálculos de poligonal rápida para levantamientos en campo
 * Método de compensación: Brújula (Bowditch)
 */
class CalcularPoligonal {

    /**
     * Calcular poligonal cerrada con compensación método brújula
     */
    fun calcularPoligonalCerrada(
        puntosMedidos: List<PuntoPoligonal>,
        azimutInicial: Double? = null,
        coordenadaInicioN: Double = 0.0,
        coordenadaInicioE: Double = 0.0,
        cotaInicio: Double = 0.0
    ): ResultadoPoligonal {
        
        require(puntosMedidos.size >= 3) { "Se necesitan al menos 3 puntos para una poligonal" }
        
        // 1. Calcular ángulos internos y error angular
        val angulosInternos = calcularAngulosInternos(puntosMedidos)
        val sumaTeorica = (puntosMedidos.size - 2) * 180.0
        val sumaReal = angulosInternos.sum()
        val errorAngular = sumaReal - sumaTeorica
        
        // 2. Compensar ángulos (distribuir error proporcionalmente)
        val correccionAngular = -errorAngular / puntosMedidos.size
        val angulosCompensados = angulosInternos.map { it + correccionAngular }
        
        // 3. Calcular azimuts
        val azimuts = calcularAzimuts(angulosCompensados, azimutInicial)
        
        // 4. Calcular proyecciones N y E
        val proyecciones = puntosMedidos.mapIndexed { index, punto ->
            val azimut = Math.toRadians(azimuts[index])
            val distanciaHorizontal = punto.distanciaInclinada * sin(Math.toRadians(punto.anguloVertical))
            
            val deltaN = distanciaHorizontal * cos(azimut)
            val deltaE = distanciaHorizontal * sin(azimut)
            
            Triple(deltaN, deltaE, distanciaHorizontal)
        }
        
        // 5. Calcular error lineal
        val sumaDeltaN = proyecciones.sumOf { it.first }
        val sumaDeltaE = proyecciones.sumOf { it.second }
        val errorLineal = sqrt(sumaDeltaN.pow(2) + sumaDeltaE.pow(2))
        val distanciaTotal = proyecciones.sumOf { it.third }
        val precision = if (errorLineal > 0) "1:${(distanciaTotal / errorLineal).toInt()}" else "Perfecta"
        
        // 6. Compensar proyecciones (método brújula)
        val proyeccionesCompensadas = proyecciones.mapIndexed { index, proj ->
            val correccionN = -sumaDeltaN * proj.third / distanciaTotal
            val correccionE = -sumaDeltaE * proj.third / distanciaTotal
            Triple(proj.first + correccionN, proj.second + correccionE, correccionN to correccionE)
        }
        
        // 7. Calcular coordenadas finales
        val puntosCompensados = mutableListOf<PuntoPoligonalCompensado>()
        var nActual = coordenadaInicioN
        var eActual = coordenadaInicioE
        var cotaActual = cotaInicio
        
        puntosMedidos.forEachIndexed { index, punto ->
            if (index > 0) {
                val proj = proyeccionesCompensadas[index - 1]
                nActual += proj.first
                eActual += proj.second
            }
            
            // Calcular cota
            val deltaCota = punto.distanciaInclinada * cos(Math.toRadians(punto.anguloVertical)) + 
                           punto.alturaInstrumento - punto.alturaPrisma
            if (index > 0) cotaActual += deltaCota
            
            val correcciones = if (index < proyeccionesCompensadas.size) 
                proyeccionesCompensadas[index].third 
            else 0.0 to 0.0
            
            puntosCompensados.add(
                PuntoPoligonalCompensado(
                    estacion = punto.estacion,
                    coordenadaN = nActual,
                    coordenadaE = eActual,
                    cota = cotaActual,
                    correccionN = correcciones.first,
                    correccionE = correcciones.second
                )
            )
        }
        
        return ResultadoPoligonal(
            puntosCompensados = puntosCompensados,
            errorAngular = abs(errorAngular) * 3600, // convertir a segundos
            errorLineal = errorLineal,
            precision = precision,
            metodoCompensacion = "Brújula (Bowditch)"
        )
    }
    
    /**
     * Calcular poligonal abierta (sin cierre)
     */
    fun calcularPoligonalAbierta(
        puntosMedidos: List<PuntoPoligonal>,
        azimutInicial: Double,
        coordenadaInicioN: Double,
        coordenadaInicioE: Double,
        cotaInicio: Double
    ): List<PuntoPoligonalCompensado> {
        
        require(puntosMedidos.isNotEmpty()) { "Se necesita al menos 1 punto" }
        
        val puntosCompensados = mutableListOf<PuntoPoligonalCompensado>()
        var nActual = coordenadaInicioN
        var eActual = coordenadaInicioE
        var cotaActual = cotaInicio
        var azimutActual = azimutInicial
        
        puntosMedidos.forEachIndexed { index, punto ->
            if (index > 0) {
                // Calcular nuevo azimut basado en ángulo horizontal
                azimutActual = (azimutActual + punto.anguloHorizontal) % 360.0
                if (azimutActual < 0) azimutActual += 360.0
            }
            
            val azimutRad = Math.toRadians(azimutActual)
            val distanciaHorizontal = punto.distanciaInclinada * sin(Math.toRadians(punto.anguloVertical))
            
            val deltaN = distanciaHorizontal * cos(azimutRad)
            val deltaE = distanciaHorizontal * sin(azimutRad)
            
            if (index > 0) {
                nActual += deltaN
                eActual += deltaE
            }
            
            // Calcular cota
            val deltaCota = punto.distanciaInclinada * cos(Math.toRadians(punto.anguloVertical)) + 
                           punto.alturaInstrumento - punto.alturaPrisma
            if (index > 0) cotaActual += deltaCota
            
            puntosCompensados.add(
                PuntoPoligonalCompensado(
                    estacion = punto.estacion,
                    coordenadaN = nActual,
                    coordenadaE = eActual,
                    cota = cotaActual,
                    correccionN = 0.0,
                    correccionE = 0.0
                )
            )
        }
        
        return puntosCompensados
    }
    
    private fun calcularAngulosInternos(puntos: List<PuntoPoligonal>): List<Double> {
        // Simplificación: usar ángulos horizontales como ángulos internos
        return puntos.map { it.anguloHorizontal }
    }
    
    private fun calcularAzimuts(angulos: List<Double>, azimutInicial: Double?): List<Double> {
        val azimuts = mutableListOf<Double>()
        var azimutActual = azimutInicial ?: 0.0
        
        angulos.forEachIndexed { index, angulo ->
            if (index == 0 && azimutInicial != null) {
                azimuts.add(azimutInicial)
            } else if (index > 0) {
                azimutActual = (azimutActual + angulo) % 360.0
                if (azimutActual < 0) azimutActual += 360.0
                azimuts.add(azimutActual)
            }
        }
        
        return azimuts.ifEmpty { listOf(0.0) }
    }
}
