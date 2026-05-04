package com.uchicn.myobra.core.domain.model.acero

data class AceroEstribo(
    val diametro: DiametroAcero,
    val tramos: List<TramoEspaciamiento>,
    val gancho: GanchoConfig
)
