package com.uchicn.myobra.core.domain.model.topo

class ResultadoMateriales(
    val excavacion: Double,
    val cama: Double,
    val sobrecama: Double,
    val rellenoPropio: Double,
    val rellenoPrestamo: Double,
    val asfalto: Double? = null
)