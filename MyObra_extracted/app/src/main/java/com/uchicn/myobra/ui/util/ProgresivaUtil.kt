package com.uchicn.myobra.ui.util

object ProgresivaUtil {

    fun generarProgresivas(
        distancia: Double,
        intervalo: Double
    ): List<Double> {

        val progresivas = mutableListOf<Double>()
        var prog = 0.0

        while (prog < distancia) {
            progresivas.add(prog)
            prog += intervalo
        }

        if (progresivas.lastOrNull() != distancia) {
            progresivas.add(distancia)
        }

        return progresivas
    }
}