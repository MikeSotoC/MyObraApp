package com.uchicn.myobra.core.domain.model.piso

import com.uchicn.myobra.core.domain.model.piso.Piso

class CalcularPiso {

    fun ejecutar(
        piso: Piso,
        dosificacion: DosificacionPiso,
        desperdicio: Double = 0.05
    ): List<String> {

        val volumen = piso.volumen() * (1 + desperdicio)

        val cemento = volumen * dosificacion.cementoBolsaPorM3
        val agregado = volumen * dosificacion.agregadoM3PorM3
        val piedra = volumen * dosificacion.piedraM3PorM3

        val resultados = mutableListOf<String>()

        resultados.add("Área: %.2f m²".format(piso.area))
        resultados.add("Espesor: %.2f m".format(piso.espesor))
        resultados.add("Volumen: %.3f m³".format(volumen))
        resultados.add("Dosificación: ${dosificacion.descripcion}")
        resultados.add("Cemento: %.1f bolsas".format(cemento))
        resultados.add("Agregado: %.3f m³".format(agregado))

        if (piedra > 0) {
            resultados.add("Piedra: %.3f m³".format(piedra))
        }

        return resultados
    }
}