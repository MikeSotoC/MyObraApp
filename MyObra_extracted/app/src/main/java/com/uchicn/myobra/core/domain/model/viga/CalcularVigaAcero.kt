package com.uchicn.myobra.core.domain.model.viga

import com.uchicn.myobra.core.domain.model.acero.AceroLongitudinal
import com.uchicn.myobra.core.domain.model.acero.VigaAceroInput

class CalcularVigaAcero {

    fun ejecutar(input: VigaAceroInput): List<String> {

        fun calcularLongitudinal(longitudinal: AceroLongitudinal): Double {
            val ganchoM =
                longitudinal.gancho.tipoGancho.factorDiametro *
                        longitudinal.diametro.mm / 1000.0

            val longitudBarra =
                input.largoVigaM +
                        longitudinal.gancho.cantidadExtremos * ganchoM

            return longitudBarra *
                    longitudinal.cantidadBarras *
                    input.cantidadVigas *
                    longitudinal.diametro.pesoKgPorMetro
        }

        val pesoInferior = calcularLongitudinal(input.longitudinalInferior)
        val pesoSuperior = calcularLongitudinal(input.longitudinalSuperior)

        val cantidadEstribos =
            input.estribo.tramos.sumOf { it.cantidad }

        val ganchoEstriboM =
            input.estribo.gancho.tipoGancho.factorDiametro *
                    input.estribo.diametro.mm / 1000.0

        val longitudEstribo =
            input.perimetroEstriboM +
                    2 * ganchoEstriboM

        val pesoEstribos =
            longitudEstribo *
                    cantidadEstribos *
                    input.cantidadVigas *
                    input.estribo.diametro.pesoKgPorMetro

        val pesoTotal =
            (pesoInferior + pesoSuperior + pesoEstribos) *
                    (1 + input.desperdicioPct)

        val alambreKg =
            pesoTotal * input.alambreKgPorKgAcero

        return listOf(
            "ACERO EN VIGAS",
            "Longitudinal inferior: %.2f kg".format(pesoInferior),
            "Longitudinal superior: %.2f kg".format(pesoSuperior),
            "Estribos: %.2f kg".format(pesoEstribos),
            "Desperdicio: ${(input.desperdicioPct * 100).toInt()} %",
            "TOTAL ACERO: %.2f kg".format(pesoTotal),
            "Alambre N°16: %.2f kg".format(alambreKg)
        )
    }
}