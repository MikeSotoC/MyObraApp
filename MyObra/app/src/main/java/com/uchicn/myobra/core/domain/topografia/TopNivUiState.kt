package com.uchicn.myobra.core.domain.topografia

import com.uchicn.myobra.core.domain.model.topo.ResultadoMateriales

sealed class TopNivUiState {
    object Idle : TopNivUiState()
    
    data class MostrarResultados(
        val titulo: String,
        val resultados: List<String>,
        val pendientePorcentaje: Double
    ) : TopNivUiState()

    data class MostrarMateriales(
        val materiales: ResultadoMateriales
    ) : TopNivUiState()

    data class Error(
        val mensaje: String
    ) : TopNivUiState()
    
    data class ListoParaIngresarLecturas(
        val cantidad: Int
    ) : TopNivUiState()
}