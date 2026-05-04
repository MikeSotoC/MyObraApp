package com.uchicn.myobra.core.domain.model.cimiento

data class Cimiento (
    val largo: Double,
    val ancho: Double,
    val altura: Double
) {fun volumen(): Double = largo * ancho * altura}