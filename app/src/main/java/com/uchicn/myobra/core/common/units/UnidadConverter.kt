package com.uchicn.myobra.core.common.units

object UnidadConverter {
    fun mmToM(mm: Double): Double = mm / 1000.0
    fun cmToM(cm: Double): Double = cm / 100.0
    fun porcentaje(p: Double): Double = p / 100.0
}