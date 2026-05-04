package com.uchicn.myobra.core.domain.model.sobrecimiento

data class Sobrecimiento(
    val largo: Double,
    val ancho: Double,
    val altura: Double
) {
    fun volumen(): Double = largo * ancho * altura
}