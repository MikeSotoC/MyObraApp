package com.uchicn.myobra.core.domain.model.losa

data class Vigueta(
    val anchoCm: Double,
    val alturaCm: Double,
    val separacionCm: Double
){
    val anchoM get() = anchoCm / 100.0
    val alturaM get() = alturaCm / 100.0
    val separacionM get() = separacionCm / 100.0
}

