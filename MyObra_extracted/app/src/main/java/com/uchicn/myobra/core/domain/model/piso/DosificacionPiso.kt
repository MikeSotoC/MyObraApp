package com.uchicn.myobra.core.domain.model.piso

enum class DosificacionPiso(
    val descripcion: String,
    val cementoBolsaPorM3: Double,
    val agregadoM3PorM3: Double,
    val piedraM3PorM3: Double
) {
    // FALSOPISO (concreto simple – CAPECO)
    FALSOPISO_1_8("Falsopiso 1:8", 5.0, 1.13, 0.0),
    FALSOPISO_1_9("Falsopiso 1:9", 4.6, 1.16, 0.0),
    FALSOPISO_1_10("Falsopiso 1:10", 4.2, 1.19, 0.0),
    FALSOPISO_1_12("Falsopiso 1:12", 3.6, 1.23, 0.0),

    // CONTRAPISO / PISO (mortero – CAPECO)
    CONTRAPISO_1_4("Contrapiso 1:4", 8.9, 1.00, 0.0),
    CONTRAPISO_1_5("Contrapiso 1:5", 7.4, 1.05, 0.0),
    CONTRAPISO_1_6("Contrapiso 1:6", 6.3, 1.07, 0.0);

    override fun toString(): String = descripcion
}