package com.uchicn.myobra.core.common.partida

import com.uchicn.myobra.core.common.partida.UnidadMedida

object PartidasCimiento {

    val CICLOPEO_1_8 = PartidaMaterial(
        "Cimiento ciclópeo 1:8 + 25% PG",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 3.7, UnidadMedida.BOLSA),
            ConsumoMaterial("Hormigón", 0.85, UnidadMedida.M3),
            ConsumoMaterial("Piedra grande", 0.40, UnidadMedida.M3)
        )
    )

    val CICLOPEO_1_10 = PartidaMaterial(
        "Cimiento ciclópeo 1:10 + 30% PG",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 2.9, UnidadMedida.BOLSA),
            ConsumoMaterial("Hormigón", 0.83, UnidadMedida.M3),
            ConsumoMaterial("Piedra grande", 0.48, UnidadMedida.M3)
        )
    )

    val TODAS = listOf(CICLOPEO_1_8, CICLOPEO_1_10)
}