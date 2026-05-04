package com.uchicn.myobra.core.domain.model.muro

enum class TipoMortero(
    val nombre: String,      // 👁️ para UI
    val cementoKg: Double,     // 🧮 por m³ de mortero
    val arenaM3: Double,
    val aguaLitros: Double
) {
    M_1_3("Mortero 1:3 (Estructural)",454.0,1.10,250.0),
    M_1_4("Mortero 1:4 (Albañilería reforzada)",364.0,1.16,240.0),
    M_1_5("Mortero 1:5 (Muros comunes)",302.0,1.20,240.0),
    M_1_6("Mortero 1:6 (Asentado liviano)",261.0,1.20,235.0);

    override fun toString(): String = nombre
}
