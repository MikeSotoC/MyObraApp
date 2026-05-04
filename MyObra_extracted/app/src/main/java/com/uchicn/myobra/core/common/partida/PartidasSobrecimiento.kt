package com.uchicn.myobra.core.common.partida

import com.uchicn.myobra.core.common.partida.UnidadMedida

object PartidasSobrecimiento {

    val CICLOPEO_1_8_25_PM = PartidaMaterial(
        "Sobrecimiento ciclópeo 1:8 + 25% PM",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 3.7, UnidadMedida.BOLSA),
            ConsumoMaterial("Hormigón", 0.85, UnidadMedida.M3),
            ConsumoMaterial("Piedra mediana", 0.42, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.13, UnidadMedida.M3)
        )
    )

    val CICLOPEO_1_10_30_PM = PartidaMaterial(
        "Sobrecimiento ciclópeo 1:10 + 30% PM",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 2.9, UnidadMedida.BOLSA),
            ConsumoMaterial("Hormigón", 0.83, UnidadMedida.M3),
            ConsumoMaterial("Piedra mediana", 0.48, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.10, UnidadMedida.M3)
        )
    )

    val TODAS = listOf(
        CICLOPEO_1_8_25_PM,
        CICLOPEO_1_10_30_PM
    )
}