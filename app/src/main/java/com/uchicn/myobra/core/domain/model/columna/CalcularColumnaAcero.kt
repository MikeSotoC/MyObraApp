package com.uchicn.myobra.core.domain.model.columna

import com.uchicn.myobra.core.domain.model.acero.ColumnaAceroInput

class CalcularColumnaAcero {

    fun ejecutar(input: ColumnaAceroInput): List<String> {

        // ===== LONGITUDINAL =====
        val ganchoLongM =
            input.longitudinal.gancho.tipoGancho.factorDiametro *
                    input.longitudinal.diametro.mm / 1000.0

        val longitudBarra =
            input.alturaColumnaM +
                    input.longitudinal.gancho.cantidadExtremos * ganchoLongM

        val longitudTotalLong =
            longitudBarra *
                    input.longitudinal.cantidadBarras *
                    input.cantidadColumnas

        val pesoLong =
            longitudTotalLong *
                    input.longitudinal.diametro.pesoKgPorMetro

        // ===== ESTRIBOS =====
        val cantidadEstribosPorCol =
            input.estribo.tramos.sumOf { it.cantidad }

        val ganchoEstriboM =
            input.estribo.gancho.tipoGancho.factorDiametro *
                    input.estribo.diametro.mm / 1000.0

        val longitudEstribo =
            input.perimetroEstriboM +
                    2 * ganchoEstriboM

        val longitudTotalEstribos =
            longitudEstribo *
                    cantidadEstribosPorCol *
                    input.cantidadColumnas

        val pesoEstribos =
            longitudTotalEstribos *
                    input.estribo.diametro.pesoKgPorMetro

        // ===== TOTALES =====
        val pesoAcero =
            (pesoLong + pesoEstribos) * (1 + input.desperdicioPct)

        val alambreKg =
            pesoAcero * input.alambreKgPorKgAcero

        return listOf(
            "ACERO EN COLUMNAS",
            "Longitudinal ${input.longitudinal.diametro}: %.2f kg".format(pesoLong),
            "Estribos ${input.estribo.diametro}: %.2f kg".format(pesoEstribos),
            "Desperdicio: ${(input.desperdicioPct * 100).toInt()} %",
            "TOTAL ACERO: %.2f kg".format(pesoAcero),
            "Alambre N°16: %.2f kg".format(alambreKg)
        )
    }
}