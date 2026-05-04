package com.uchicn.myobra.core.domain.model.acero

data class VigaAceroInput(
    val largoVigaM: Double,
    val cantidadVigas: Int,

    val longitudinalInferior: AceroLongitudinal,
    val longitudinalSuperior: AceroLongitudinal,

    val estribo: AceroEstribo,

    val perimetroEstriboM: Double,

    val longitudVarillaComercialM: Double = 9.0,
    val desperdicioPct: Double = 0.05,
    val alambreKgPorKgAcero: Double = 0.05
)
