package com.uchicn.myobra.core.common.partida

import com.uchicn.myobra.core.common.partida.UnidadMedida

data class ConsumoMaterial(
    val material: String,
    val cantidad : Double,
    val unidad: UnidadMedida
)