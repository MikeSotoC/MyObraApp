package com.uchicn.myobra.core.domain.model.solado

data class Solado (
    val area: Double,
    val espesor: Double
){
    fun volumen(): Double = area * espesor
}