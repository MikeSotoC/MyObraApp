package com.uchicn.myobra.core.domain.model.zapata

data class Zapata(
    val largo: Double,
    val ancho: Double,
    val altura: Double
) {fun volumen(): Double = largo * ancho * altura}