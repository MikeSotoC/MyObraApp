package com.uchicn.myobra.core.domain.topografia

import com.uchicn.myobra.core.domain.model.topo.PuntoPerfil
import com.uchicn.myobra.core.domain.model.topo.ResultadoMateriales
import com.uchicn.myobra.core.domain.topografia.MaterialTramo

class CalcularMaterialesZanja {

    fun calcularPorTramos(
        perfil: List<PuntoPerfil>,
        ancho: Double,
        cama: Double,
        sobrecama: Double
    ): List<MaterialTramo> {

        require(perfil.size >= 2) { "El perfil debe tener al menos 2 puntos" }
        require(ancho > 0) { "El ancho debe ser mayor que 0" }

        val tramos = mutableListOf<MaterialTramo>()

        for (i in 0 until perfil.size - 1) {
            val p1 = perfil[i]
            val p2 = perfil[i + 1]
            val d = p2.progresiva - p1.progresiva

            require(d > 0) { "Las progresivas deben ser crecientes" }

            tramos.add(
                MaterialTramo(
                    desde = p1.progresiva,
                    hasta = p2.progresiva,
                    excavacion = ancho * (cama + sobrecama) * d,
                    cama = ancho * cama * d,
                    sobrecama = ancho * sobrecama * d
                )
            )
        }

        return tramos
    }

    fun calcular(
        perfil: List<PuntoPerfil>,
        ancho: Double,
        diametro: Double,
        cama: Double,
        sobrecama: Double,
        rellPropio: Double,
        rellPrest: Double,
        desperdicio: Double,
        espAsfalto: Double?
    ): ResultadoMateriales {

        require(perfil.size >= 2) { "El perfil debe tener al menos 2 puntos" }
        require(ancho > 0) { "El ancho debe ser mayor que 0" }
        require(diametro >= 0) { "El diámetro no puede ser negativo" }
        require(desperdicio >= 0) { "El desperdicio no puede ser negativo" }

        val factor = 1 + desperdicio

        var volExc = 0.0
        var volCama = 0.0
        var volSobrecama = 0.0
        var volRPropio = 0.0
        var volRPrest = 0.0
        var volAsfalto = 0.0

        for (i in 0 until perfil.size - 1) {
            val d = perfil[i + 1].progresiva - perfil[i].progresiva

            volExc += ancho *
                    (cama + diametro + sobrecama + rellPropio + rellPrest + (espAsfalto ?: 0.0)) * d

            volCama += ancho * cama * d * factor
            volSobrecama += ancho * sobrecama * d * factor
            volRPropio += ancho * rellPropio * d * factor
            volRPrest += ancho * rellPrest * d * factor

            if (espAsfalto != null) {
                volAsfalto += ancho * espAsfalto * d
            }
        }

        return ResultadoMateriales(
            excavacion = volExc,
            cama = volCama,
            sobrecama = volSobrecama,
            rellenoPropio = volRPropio,
            rellenoPrestamo = volRPrest,
            asfalto = espAsfalto?.let { volAsfalto }
        )
    }

    fun List<PuntoPerfil>.longitudTotal(): Double =
        if (size < 2) 0.0 else last().progresiva - first().progresiva
}
