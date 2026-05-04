package com.uchicn.myobra.ui.topniv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.uchicn.myobra.core.common.units.UnidadConverter
import com.uchicn.myobra.core.domain.model.topo.PuntoPerfil
import com.uchicn.myobra.core.domain.model.topo.PuntoRasante
import com.uchicn.myobra.core.domain.model.topo.ResultadoMateriales
import com.uchicn.myobra.core.domain.topografia.TopNivUiState
import com.uchicn.myobra.core.domain.topografia.CalcularTopografia
import com.uchicn.myobra.core.domain.topografia.CalcularMaterialesZanja
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopNivViewModel @Inject constructor(
    private val topografia: CalcularTopografia,
    private val materialesZanja: CalcularMaterialesZanja
) : ViewModel() {

    // ================= ESTADO =================
    private val _uiState = MutableStateFlow<TopNivUiState>(TopNivUiState.Idle)
    val uiState: StateFlow<TopNivUiState> = _uiState.asStateFlow()

    // ================= CACHE =================
    private var ultimoPerfil: List<PuntoPerfil>? = null
    private var ultimaRasante: List<PuntoRasante>? = null

    // ================= PERFIL =================
    fun generarPerfil(
        cotaBm: Double?,
        lecturaBm: Double?,
        intervalo: Double?,
        lecturas: List<Double>,
        distancia: Double?
    ): List<PuntoPerfil> {

        if (cotaBm == null || lecturaBm == null || intervalo == null || distancia == null) {
            _uiState.value = TopNivUiState.Error("Datos insuficientes para perfil")
            return emptyList()
        }

        val perfil = topografia.generarPerfil(
            cotaBm,
            lecturaBm,
            intervalo,
            lecturas,
            distancia
        )

        // cache del perfil
        ultimoPerfil = perfil

        return perfil
    }

    fun generarPerfilYMostrarDialogo(
        cotaBm: Double?,
        lecturaBm: Double?,
        intervalo: Double?,
        distancia: Double?
    ) {
        if (cotaBm == null || lecturaBm == null || intervalo == null || distancia == null) {
            _uiState.value = TopNivUiState.Error("Ingrese todos los datos de nivelación")
            return
        }
        // Esta función prepara el estado para mostrar el diálogo de lecturas
        // La lógica completa del diálogo debe implementarse en la UI Compose
        _uiState.value = TopNivUiState.ListoParaIngresarLecturas(
            cantidad = ((distancia / intervalo) + 1).toInt()
        )
    }

    // ================= NIVELACIÓN =================
    fun calcularNivelacion(
        cotaBm: Double?,
        lecturaBm: Double?,
        bzIni: Double?,
        bzFin: Double?,
        distancia: Double?,
        intervalo: Double?,
        diametroMm: Double?,
        camaCm: Double?,
        soloLecturas: Boolean
    ) {

        if (listOf(
                cotaBm,
                lecturaBm,
                bzIni,
                bzFin,
                distancia,
                intervalo,
                diametroMm,
                camaCm
            ).any { it == null }
        ) {
            _uiState.value = TopNivUiState.Error("Completa todos los datos")
            return
        }

        // 1️⃣ mostramos resultados
        val resultados = topografia.calcularNivelacion(
            cotaBm = cotaBm!!,
            lecturaBm = lecturaBm!!,
            bzIni = bzIni!!,
            bzFin = bzFin!!,
            distancia = distancia!!,
            intervalo = intervalo!!,
            diametro = UnidadConverter.mmToM(diametroMm!!),
            espCama = UnidadConverter.cmToM(camaCm!!),
            soloLecturas = soloLecturas
        )

        // 2️⃣ mostramos pendiente como dato propio
        val pendientePct = topografia.pendientePorcentaje(
            bzIni!!,
            bzFin!!,
            distancia!!
        )
        _uiState.value = TopNivUiState.MostrarResultados(
            titulo = if (soloLecturas) "Lecturas" else "Cotas",
            resultados = resultados,
            pendientePorcentaje = pendientePct
        )

        ultimaRasante = topografia.generarRasante(
            bzIni = bzIni!!,
            bzFin = bzFin!!,
            distancia = distancia!!,
            intervalo = intervalo!!
        )

    }
    
    fun obtenerRasante(): List<PuntoRasante>? = ultimaRasante
    
    // ================= MATERIALES =================
    fun calcularMateriales(
        perfil: List<PuntoPerfil>,
        ancho: Double?,
        diametroMm: Double?,
        camaCm: Double?,
        sobreCm: Double?,
        rellPropioCm: Double?,
        rellPrestCm: Double?,
        desperdicioPct: Double?,
        espAsfaltoCm: Double?
    ) {

        val perfilUsado = ultimoPerfil ?: perfil

        if (perfilUsado.isEmpty()) {
            _uiState.value = TopNivUiState.Error("No hay perfil generado")
            return
        }

        if (ancho == null || diametroMm == null) {
            _uiState.value = TopNivUiState.Error("Completa datos de zanja")
            return
        }

        val materiales: ResultadoMateriales = materialesZanja.calcular(
            perfil = perfilUsado,
            ancho = ancho,
            diametro = UnidadConverter.mmToM(diametroMm),
            cama = camaCm?.let { UnidadConverter.cmToM(it) } ?: 0.0,
            sobrecama = sobreCm?.let { UnidadConverter.cmToM(it) } ?: 0.0,
            rellPropio = rellPropioCm?.let { UnidadConverter.cmToM(it) } ?: 0.0,
            rellPrest = rellPrestCm?.let { UnidadConverter.cmToM(it) } ?: 0.0,
            desperdicio = desperdicioPct?.let { UnidadConverter.porcentaje(it) } ?: 0.0,
            espAsfalto = espAsfaltoCm?.let { UnidadConverter.cmToM(it) }
        )

        _uiState.value = TopNivUiState.MostrarMateriales(materiales)
    }
    
    fun clearError() {
        _uiState.value = TopNivUiState.Idle
    }
}
