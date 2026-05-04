package com.uchicn.myobra.core.domain.model.topo

/**
 * Punto de radiación desde estación total
 */
data class PuntoRadiacion(
    val puntoId: String,
    val anguloHorizontal: Double, // grados decimales
    val distanciaInclinada: Double, // metros
    val anguloVertical: Double, // grados decimales (90 = horizontal)
    val alturaInstrumento: Double, // metros
    val alturaPrisma: Double, // metros
    val descripcion: String = "",
    val coordenadaN: Double? = null,
    val coordenadaE: Double? = null,
    val cota: Double? = null
)

/**
 * Resultado de cálculo de radiaciones
 */
data class ResultadoRadiacion(
    val estacionN: Double,
    val estacionE: Double,
    val estacionCota: Double,
    val puntosCalculados: List<PuntoRadiacionCalculado>,
    val cantidadPuntos: Int
)

data class PuntoRadiacionCalculado(
    val puntoId: String,
    val coordenadaN: Double,
    val coordenadaE: Double,
    val cota: Double,
    val descripcion: String
)
