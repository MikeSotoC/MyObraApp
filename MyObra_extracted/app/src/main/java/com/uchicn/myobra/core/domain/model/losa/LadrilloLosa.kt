package com.uchicn.myobra.core.domain.model.losa

data class LadrilloLosa(
    val largoCm: Double,
    val anchoCm: Double,
    val altoCm: Double
){
    val largoM get() = largoCm / 100.0
    val anchoM get() = anchoCm / 100.0
    val altoM get() = altoCm / 100.0
}
