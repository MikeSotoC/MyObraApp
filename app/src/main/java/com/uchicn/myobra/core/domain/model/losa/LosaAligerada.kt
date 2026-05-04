package com.uchicn.myobra.core.domain.model.losa

data class LosaAligerada(
    val largoM: Double,
    val anchoM: Double,
    val capaCompresionCm: Double,
    val vigueta: Vigueta
){
    val areaM2 get() = largoM * anchoM
    val capaCompresionM get() = capaCompresionCm / 100.0
}
