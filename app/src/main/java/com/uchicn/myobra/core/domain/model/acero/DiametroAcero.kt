package com.uchicn.myobra.core.domain.model.acero

enum class DiametroAcero(
    val mm: Double,
    val pesoKgPorMetro: Double
) {
    D6(6.0, 0.222),
    D8(8.0, 0.395),
    D3_8(9.5, 0.557),
    D1_2(12.7, 0.994),
    D5_8(15.9, 1.552),
    D3_4(19.1, 2.235),
    D1(25.4, 3.973);

    override fun toString(): String =
        when (this) {
            D6 -> "Ø 6 mm"
            D8 -> "Ø 8 mm"
            D3_8 -> "Ø 3/8\""
            D1_2 -> "Ø 1/2\""
            D5_8 -> "Ø 5/8\""
            D3_4 -> "Ø 3/4\""
            D1 -> "Ø 1\""
        }
}