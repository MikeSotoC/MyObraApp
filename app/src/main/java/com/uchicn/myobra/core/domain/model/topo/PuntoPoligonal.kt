package com.uchicn.myobra.core.domain.model.topo

/**
 * Punto de poligonal para levantamientos rápidos
 */
data class PuntoPoligonal(
    val estacion: String,
    val anguloHorizontal: Double, // grados decimales
    val distanciaInclinada: Double, // metros
    val anguloVertical: Double, // grados decimales (90 = horizontal)
    val alturaInstrumento: Double, // metros
    val alturaPrisma: Double, // metros
    val coordenadaN: Double? = null,
    val coordenadaE: Double? = null,
    val cota: Double? = null
)

/**
 * Resultado de poligonal compensada
 */
data class ResultadoPoligonal(
    val puntosCompensados: List<PuntoPoligonalCompensado>,
    val errorAngular: Double, // segundos
    val errorLineal: Double, // metros
    val precision: String, // ej: "1:5000"
    val metodoCompensacion: String
)

data class PuntoPoligonalCompensado(
    val estacion: String,
    val coordenadaN: Double,
    val coordenadaE: Double,
    val cota: Double,
    val correccionN: Double,
    val correccionE: Double
)
