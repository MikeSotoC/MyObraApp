package com.uchicn.myobra.core.domain.model.acero

enum class TipoGancho(
    val angulo: Int,
    val factorDiametro: Double
) {
    GANCHO_90(90, 12.0),
    GANCHO_135(135, 6.0)
}