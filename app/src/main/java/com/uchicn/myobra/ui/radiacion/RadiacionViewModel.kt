package com.uchicn.myobra.ui.radiacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.uchicn.myobra.core.domain.model.topo.PuntoRadiacion
import com.uchicn.myobra.core.domain.model.topo.ResultadoRadiacion
import com.uchicn.myobra.core.domain.topografia.CalcularRadiacion

sealed class RadiacionUiState {
    object Idle : RadiacionUiState()
    data class MostrarResultado(val resultado: ResultadoRadiacion) : RadiacionUiState()
    data class Error(val mensaje: String) : RadiacionUiState()
}

@HiltViewModel
class RadiacionViewModel @Inject constructor(
    private val calcularRadiacion: CalcularRadiacion
) : ViewModel() {

    private val _uiState = MutableStateFlow<RadiacionUiState>(RadiacionUiState.Idle)
    val uiState: StateFlow<RadiacionUiState> = _uiState.asStateFlow()

    fun calcularRadiacion(
        estacionN: Double,
        estacionE: Double,
        estacionCota: Double,
        puntosMedidos: List<PuntoRadiacion>
    ) {
        viewModelScope.launch {
            try {
                val resultado = calcularRadiacion.calcularRadiacion(
                    estacionN = estacionN,
                    estacionE = estacionE,
                    estacionCota = estacionCota,
                    puntosMedidos = puntosMedidos
                )
                _uiState.value = RadiacionUiState.MostrarResultado(resultado)
            } catch (e: Exception) {
                _uiState.value = RadiacionUiState.Error(e.message ?: "Error al calcular radiación")
            }
        }
    }

    fun clearError() {
        _uiState.value = RadiacionUiState.Idle
    }
}
