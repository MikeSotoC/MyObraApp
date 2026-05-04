package com.uchicn.myobra.ui.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uchicn.myobra.data.local.ProyectoNivelacionDao
import com.uchicn.myobra.data.local.ProyectoNivelacionEntity
import com.uchicn.myobra.data.local.ProyectoPoligonalDao
import com.uchicn.myobra.data.local.ProyectoPoligonalEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistorialUiState(
    val proyectosNivelacion: List<ProyectoNivelacionEntity> = emptyList(),
    val proyectosPoligonal: List<ProyectoPoligonalEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class HistorialViewModel @Inject constructor(
    private val nivelacionDao: ProyectoNivelacionDao,
    private val poligonalDao: ProyectoPoligonalDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistorialUiState())
    val uiState: StateFlow<HistorialUiState> = _uiState.asStateFlow()

    init {
        cargarProyectos()
    }

    fun cargarProyectos() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                combine(
                    nivelacionDao.getAllProyectos().catch { e -> emit(emptyList()) },
                    poligonalDao.getAllProyectos().catch { e -> emit(emptyList()) }
                ) { nivelacion, poligonal ->
                    HistorialUiState(
                        proyectosNivelacion = nivelacion,
                        proyectosPoligonal = poligonal,
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al cargar proyectos: ${e.message}"
                )
            }
        }
    }

    fun eliminarProyectoNivelacion(id: Long) {
        viewModelScope.launch {
            try {
                nivelacionDao.deleteProyectoById(id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error al eliminar: ${e.message}")
            }
        }
    }

    fun eliminarProyectoPoligonal(id: Long) {
        viewModelScope.launch {
            try {
                poligonalDao.deleteProyectoById(id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error al eliminar: ${e.message}")
            }
        }
    }
}
