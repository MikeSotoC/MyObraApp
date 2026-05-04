package com.uchicn.myobra.core.domain.topografia

import com.uchicn.myobra.core.domain.model.topo.*
import kotlin.math.*

/**
 * Cálculos de radiación desde estación total
 */
class CalcularRadiacion {

    /**
     * Calcular coordenadas de puntos por radiación
     */
    fun calcularRadiacion(
        estacionN: Double,
        estacionE: Double,
        estacionCota: Double,
        puntosMedidos: List<PuntoRadiacion>
    ): ResultadoRadiacion {
        
        require(puntosMedidos.isNotEmpty()) { "Se necesita al menos 1 punto" }
        
        val puntosCalculados = puntosMedidos.map { punto ->
            // Convertir ángulos a radianes
            val azimutRad = Math.toRadians(punto.anguloHorizontal)
            val verticalRad = Math.toRadians(punto.anguloVertical)
            
            // Calcular distancia horizontal
            val distanciaHorizontal = punto.distanciaInclinada * sin(verticalRad)
            
            // Calcular proyecciones
            val deltaN = distanciaHorizontal * cos(azimutRad)
            val deltaE = distanciaHorizontal * sin(azimutRad)
            
            // Coordenadas finales
            val nFinal = estacionN + deltaN
            val eFinal = estacionE + deltaE
            
            // Calcular cota
            val deltaCota = punto.distanciaInclinada * cos(verticalRad) + 
                           punto.alturaInstrumento - punto.alturaPrisma
            val cotaFinal = estacionCota + deltaCota
            
            PuntoRadiacionCalculado(
                puntoId = punto.puntoId,
                coordenadaN = nFinal,
                coordenadaE = eFinal,
                cota = cotaFinal,
                descripcion = punto.descripcion
            )
        }
        
        return ResultadoRadiacion(
            estacionN = estacionN,
            estacionE = estacionE,
            estacionCota = estacionCota,
            puntosCalculados = puntosCalculados,
            cantidadPuntos = puntosCalculados.size
        )
    }
    
    /**
     * Calcular un solo punto por radiación (útil para ingreso individual)
     */
    fun calcularPuntoIndividual(
        estacionN: Double,
        estacionE: Double,
        estacionCota: Double,
        punto: PuntoRadiacion
    ): PuntoRadiacionCalculado {
        
        val azimutRad = Math.toRadians(punto.anguloHorizontal)
        val verticalRad = Math.toRadians(punto.anguloVertical)
        
        val distanciaHorizontal = punto.distanciaInclinada * sin(verticalRad)
        val deltaN = distanciaHorizontal * cos(azimutRad)
        val deltaE = distanciaHorizontal * sin(azimutRad)
        
        val deltaCota = punto.distanciaInclinada * cos(verticalRad) + 
                       punto.alturaInstrumento - punto.alturaPrisma
        
        return PuntoRadiacionCalculado(
            puntoId = punto.puntoId,
            coordenadaN = estacionN + deltaN,
            coordenadaE = estacionE + deltaE,
            cota = estacionCota + deltaCota,
            descripcion = punto.descripcion
        )
    }
}
