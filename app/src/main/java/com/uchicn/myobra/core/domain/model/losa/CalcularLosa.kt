package com.uchicn.myobra.core.domain.model.losa

import com.uchicn.myobra.core.common.partida.PartidaMaterial
import kotlin.math.ceil
import kotlin.math.floor

class CalcularLosa {

    fun ejecutar(
        losa: LosaAligerada,
        ladrillo: LadrilloLosa,
        desperdicioLadrillo: Double,
        desperdicioConcreto: Double,
        partidaConcreto: PartidaMaterial
    ): List<String> {

        val v = losa.vigueta

        // ===============================
        // VIGUETAS
        // ===============================
        val nViguetas =
            floor(losa.anchoM / v.separacionM).toInt() + 1

        // ===============================
        // CONCRETO
        // ===============================
        val volumenCapa =
            losa.largoM * losa.anchoM * losa.capaCompresionM

        val volumenUnaVigueta =
            losa.largoM * v.anchoM * v.alturaM

        val volumenViguetas =
            volumenUnaVigueta * nViguetas

        val volumenConcreto =
            (volumenCapa + volumenViguetas) * (1 + desperdicioConcreto)

        // ===============================
        // LADRILLOS
        // ===============================
        val anchoUtil =
            losa.anchoM - (nViguetas * v.anchoM)

        val areaLadrillos =
            losa.largoM * anchoUtil

        val areaLadrillo =
            ladrillo.largoM * ladrillo.anchoM

        val cantidadLadrillos =
            ceil((areaLadrillos / areaLadrillo) * (1 + desperdicioLadrillo))
                .toInt()

        // ===============================
        // MATERIALES DE CONCRETO
        // ===============================
        val materialesConcreto = partidaConcreto.consumos.map { consumo ->
            val total = consumo.cantidad * volumenConcreto
            "${consumo.material}: ${"%.2f".format(total)} ${consumo.unidad}"
        }

        // ===============================
        // RESULTADOS
        // ===============================
        return buildList {
            add("Área de losa: %.2f m²".format(losa.areaM2))
            add("Número de viguetas: $nViguetas")
            add("Volumen de concreto: %.3f m³".format(volumenConcreto))
            add("Ladrillos: $cantidadLadrillos und")
            add("---- ${partidaConcreto.nombre} ----")
            addAll(materialesConcreto)
        }
    }
}