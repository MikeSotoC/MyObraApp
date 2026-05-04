package com.uchicn.myobra.core.domain.model.cimiento

import com.uchicn.myobra.core.common.partida.PartidaMaterial

class CalcularCimiento {

    fun calcularCimiento(
        cimiento: Cimiento,
        partida: PartidaMaterial
    ): List<String> {

        val volumen = cimiento.volumen()

        return partida.consumos.map { consumo ->
            val total = consumo.cantidad * volumen
            "${consumo.material}: %.2f ${consumo.unidad.simbolo}".format(total)
        }
    }
}