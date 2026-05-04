package com.uchicn.myobra.core.common.partida

import com.uchicn.myobra.core.common.partida.UnidadMedida

object PartidasConcreto {
    val FC_140 = PartidaMaterial(
        "Concreto f'c = 140 kg/cm²",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 7.01, UnidadMedida.BOLSA),
            ConsumoMaterial("Arena", 0.56, UnidadMedida.M3),
            ConsumoMaterial("Piedra 1/2\"", 0.64, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.184, UnidadMedida.M3)
        )
    )

    val FC_175 = PartidaMaterial(
        "Concreto f'c = 175 kg/cm²",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 8.43, UnidadMedida.BOLSA),
            ConsumoMaterial("Arena", 0.54, UnidadMedida.M3),
            ConsumoMaterial("Piedra 1/2\"", 0.55, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.185, UnidadMedida.M3)
        )
    )

    val FC_210 = PartidaMaterial(
        "Concreto f'c = 210 kg/cm²",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 9.73, UnidadMedida.BOLSA),
            ConsumoMaterial("Arena", 0.52, UnidadMedida.M3),
            ConsumoMaterial("Piedra 1/2\"", 0.53, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.186, UnidadMedida.M3)
        )
    )

    val FC_245 = PartidaMaterial(
        "Concreto f'c = 245 kg/cm²",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 11.50, UnidadMedida.BOLSA),
            ConsumoMaterial("Arena", 0.50, UnidadMedida.M3),
            ConsumoMaterial("Piedra 1/2\"", 0.51, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.187, UnidadMedida.M3)
        )
    )

    val FC_280 = PartidaMaterial(
        "Concreto f'c = 280 kg/cm²",
        UnidadMedida.M3,
        listOf(
            ConsumoMaterial("Cemento", 13.34, UnidadMedida.BOLSA),
            ConsumoMaterial("Arena", 0.45, UnidadMedida.M3),
            ConsumoMaterial("Piedra 1/2\"", 0.51, UnidadMedida.M3),
            ConsumoMaterial("Agua", 0.189, UnidadMedida.M3)
        )
    )

    val TODAS = listOf(
        FC_140,
        FC_175,
        FC_210,
        FC_245,
        FC_280
    )
}