package com.uchicn.myobra.core.domain.model.columna

import com.uchicn.myobra.core.common.partida.PartidaMaterial

class CalcularColumna {
    fun ejecutar(
        columna: Columna,
        partida: PartidaMaterial
    ): List<String> {

        val volumen = columna.volumen()

        return partida.consumos.map {
            val total = it.cantidad * volumen
            "${it.material}: %.2f ${it.unidad}".format(total)
        }
    }
}