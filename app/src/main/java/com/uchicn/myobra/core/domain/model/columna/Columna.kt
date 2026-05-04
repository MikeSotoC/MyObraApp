package com.uchicn.myobra.core.domain.model.columna

data class Columna(
    val ancho: Double,
    val largo: Double,
    val altura: Double,
    val cantidad: Int = 1
) {
    fun volumen(): Double = ancho * largo * altura * cantidad
}