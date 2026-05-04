package com.uchicn.myobra.core.domain.model.acero

data class ColumnaAceroInput(
    val alturaColumnaM: Double,
    val cantidadColumnas: Int,

    val longitudinal: AceroLongitudinal,
    val estribo: AceroEstribo,

    val perimetroEstriboM: Double,

    val longitudVarillaComercialM: Double = 9.0,
    val desperdicioPct: Double = 0.05,
    val alambreKgPorKgAcero: Double = 0.05
)
