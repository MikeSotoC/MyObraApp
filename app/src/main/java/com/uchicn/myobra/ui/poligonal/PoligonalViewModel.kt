package com.uchicn.myobra.ui.poligonal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.uchicn.myobra.core.domain.model.topo.PuntoPoligonal
import com.uchicn.myobra.core.domain.model.topo.ResultadoPoligonal
import com.uchicn.myobra.core.domain.topografia.CalcularPoligonal

sealed class PoligonalUiState {
    object Idle : PoligonalUiState()
    data class MostrarResultado(val resultado: ResultadoPoligonal) : PoligonalUiState()
    data class Error(val mensaje: String) : PoligonalUiState()
}

@HiltViewModel
class PoligonalViewModel @Inject constructor(
    private val calcularPoligonal: CalcularPoligonal
) : ViewModel() {

    private val _uiState = MutableStateFlow<PoligonalUiState>(PoligonalUiState.Idle)
    val uiState: StateFlow<PoligonalUiState> = _uiState.asStateFlow()

    fun calcularPoligonalCerrada(
        puntosMedidos: List<PuntoPoligonal>,
        azimutInicial: Double?,
        coordenadaInicioN: Double,
        coordenadaInicioE: Double,
        cotaInicio: Double
    ) {
        viewModelScope.launch {
            try {
                val resultado = calcularPoligonal.calcularPoligonalCerrada(
                    puntosMedidos = puntosMedidos,
                    azimutInicial = azimutInicial,
                    coordenadaInicioN = coordenadaInicioN,
                    coordenadaInicioE = coordenadaInicioE,
                    cotaInicio = cotaInicio
                )
                _uiState.value = PoligonalUiState.MostrarResultado(resultado)
            } catch (e: Exception) {
                _uiState.value = PoligonalUiState.Error(e.message ?: "Error al calcular poligonal")
            }
        }
    }

    fun clearError() {
        _uiState.value = PoligonalUiState.Idle
    }
}
