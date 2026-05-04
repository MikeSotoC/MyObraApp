package com.uchicn.myobra.core.domain.model.piso

data class Piso(
    val area: Double,
    val espesor: Double
) {
    fun volumen(): Double = area * espesor
}