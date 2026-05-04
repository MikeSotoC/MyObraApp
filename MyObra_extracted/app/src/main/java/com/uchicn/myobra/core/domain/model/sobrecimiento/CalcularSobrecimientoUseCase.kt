package com.uchicn.myobra.core.domain.model.sobrecimiento

import com.uchicn.myobra.core.domain.model.sobrecimiento.Sobrecimiento
import com.uchicn.myobra.core.common.partida.PartidaMaterial

class CalcularSobrecimientoUseCase {

    fun ejecutar(
        sobrecimiento: Sobrecimiento,
        partida: PartidaMaterial
    ): List<String> {

        val volumen = sobrecimiento.volumen()

        return partida.consumos.map { consumo ->
            val total = consumo.cantidad * volumen
            "${consumo.material}: %.2f ${consumo.unidad.simbolo}".format(total)
        }
    }
}