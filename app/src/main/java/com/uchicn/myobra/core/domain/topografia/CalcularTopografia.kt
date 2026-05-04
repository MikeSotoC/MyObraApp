package com.uchicn.myobra.domain.topografia

import com.uchicn.myobra.core.domain.model.topo.PuntoPerfil
import com.uchicn.myobra.core.domain.model.topo.PuntoRasante
import kotlin.math.floor

class CalcularTopografia {

    /** PERFIL LONGITUDINAL */
    fun generarPerfil(
        cotaBm: Double,
        lecturaBm: Double,
        intervalo: Double,
        lecturas: List<Double>,
        distanciaTotal: Double
    ): List<PuntoPerfil> {

        require(lecturas.isNotEmpty()) { "Debe haber al menos una lectura" }

        val esperados = floor(distanciaTotal / intervalo).toInt() + 1
        require(lecturas.size == esperados || lecturas.size == esperados + 1) {
            "Cantidad de lecturas no coincide con distancia e intervalo"
        }

        val hi = cotaBm + lecturaBm
        val perfil = mutableListOf<PuntoPerfil>()

        lecturas.forEachIndexed { index, lectura ->

            val progresiva =
                if (index == lecturas.lastIndex) distanciaTotal
                else index * intervalo

            val cota = hi - lectura

            perfil.add(
                PuntoPerfil(
                    progresiva = progresiva,
                    lectura = lectura,
                    cotaSuperficie = cota
                )
            )
        }

        return perfil
    }

    /** NIVELACIÓN (MISMA LÓGICA QUE ANTES, SIN STRINGS) */
    fun calcularNivelacion(
        cotaBm: Double,
        lecturaBm: Double,
        bzIni: Double,
        bzFin: Double,
        distancia: Double,
        intervalo: Double,
        diametro: Double,
        espCama: Double,
        soloLecturas: Boolean
    ): List<String> {

        require(distancia > 0) {
            "La distancia debe ser mayor que 0"
        }
        require(intervalo > 0) {
            "El intervalo debe ser mayor que 0"
        }
        require(intervalo <= distancia) {
            "El intervalo no puede ser mayor que la distancia"
        }

        val hi = cotaBm + lecturaBm
        val pendiente = (bzIni - bzFin) / distancia

        val resultados = mutableListOf<String>()
        var d = 0.0

        while (d <= distancia + 0.001) {

            val cotaCama = bzIni - pendiente * d
            val cotaTuberia = cotaCama + diametro
            val cotaExcavacion = cotaCama - espCama

            val lCama = hi - cotaCama
            val lTuberia = hi - cotaTuberia
            val lExcavacion = hi - cotaExcavacion

            resultados.add(
                "0+%.2f | Cama=%.3f | Tube=%.3f | Exca=%.3f".format(
                    d,
                    if (soloLecturas) lCama else cotaCama,
                    if (soloLecturas) lTuberia else cotaTuberia,
                    if (soloLecturas) lExcavacion else cotaExcavacion
                )
            )

            d += intervalo
        }

        return resultados
    }

    fun generarRasante(
        bzIni: Double,
        bzFin: Double,
        distancia: Double,
        intervalo: Double
    ): List<PuntoRasante> {

        require(distancia > 0)
        require(intervalo > 0)
        require(intervalo <= distancia)

        val pendiente = (bzIni - bzFin) / distancia
        val puntos = mutableListOf<PuntoRasante>()

        var d = 0.0
        while (d <= distancia + 0.001) {
            val cota = bzIni - pendiente * d
            puntos.add(PuntoRasante(d, cota))
            d += intervalo
        }

        // asegurar último punto exacto
        if (puntos.last().progresiva < distancia) {
            puntos.add(PuntoRasante(distancia, bzFin))
        }

        return puntos
    }


    fun calcularPendiente(
        bzIni: Double,
        bzFin: Double,
        distancia: Double
    ): Double {
        require(distancia > 0) { "La distancia debe ser mayor que 0" }
        return (bzFin - bzIni) / distancia
    }

    fun pendientePorcentaje(
        bzIni: Double,
        bzFin: Double,
        distancia: Double
    ): Double = calcularPendiente(bzIni, bzFin, distancia) * 100

}
