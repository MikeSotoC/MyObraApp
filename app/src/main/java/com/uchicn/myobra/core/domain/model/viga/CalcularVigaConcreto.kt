package com.uchicn.myobra.core.domain.model.viga

import com.uchicn.myobra.core.common.partida.PartidaMaterial

class CalcularVigaConcreto {

    fun ejecutar(
        volumenConcretoM3: Double,
        partida: PartidaMaterial,
        desperdicio: Double = 0.05
    ): List<String> {

        val volumenFinal =
            volumenConcretoM3 * (1 + desperdicio)

        return buildList {
            add("Concreto según metrados: %.2f m³".format(volumenConcretoM3))
            add("Concreto final (incl. desperdicio): %.2f m³".format(volumenFinal))

            partida.consumos.forEach { consumo ->
                val total = consumo.cantidad * volumenFinal
                add(
                    "${consumo.material}: %.3f ${consumo.unidad.simbolo}"
                        .format(total)
                )
            }
        }
    }
}