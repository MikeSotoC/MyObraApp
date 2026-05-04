package com.uchicn.myobra.core.common.partida

import com.uchicn.myobra.core.common.partida.UnidadMedida
import com.uchicn.myobra.core.common.partida.ConsumoMaterial

data class PartidaMaterial(
    val nombre: String,
    val unidad: UnidadMedida,
    val consumos: List<ConsumoMaterial>
) {
    override fun toString(): String = nombre
}