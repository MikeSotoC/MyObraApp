package com.uchicn.myobra.core.domain.model.muro

data class MuroInput(
    val areaMuroM2: Double,
    val ladrillo: LadrilloInput,
    val asentado: TipoAsentado,
    val juntaHorizontalCm: Double,
    val juntaVerticalCm: Double,
    val tipoMortero: TipoMortero,
    val desperdicioLadrilloPct: Double,
    val desperdicioMorteroPct: Double
)
