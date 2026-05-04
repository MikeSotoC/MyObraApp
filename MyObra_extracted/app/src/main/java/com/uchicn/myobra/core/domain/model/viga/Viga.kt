package com.uchicn.myobra.core.domain.model.viga

data class Viga(
    val largo: Double,   // m
    val ancho: Double,   // m
    val altura: Double,  // m
    val cantidad: Int
){
    fun volumenTotal(): Double = largo * ancho * altura * cantidad
}
