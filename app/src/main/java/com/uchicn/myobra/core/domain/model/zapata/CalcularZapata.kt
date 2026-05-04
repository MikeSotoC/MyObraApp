package com.uchicn.myobra.core.domain.model.zapata

import com.uchicn.myobra.core.common.partida.PartidaMaterial

class CalcularZapata {
    fun ejecutar(
        zapata: Zapata,
        partida: PartidaMaterial
    ): List<String> {

        val volumen = zapata.volumen()

        return partida.consumos.map {
            val total = it.cantidad * volumen
            "${it.material}: %.2f ${it.unidad}".format(total)
        }
    }
}