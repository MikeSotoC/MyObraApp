// SobrecimientoViewModel.kt
package com.uchicn.myobra.ui.sobrecimiento

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.sobrecimiento.Sobrecimiento
import com.uchicn.myobra.core.common.partida.PartidaMaterial
import com.uchicn.myobra.core.domain.model.sobrecimiento.CalcularSobrecimientoUseCase

class SobrecimientoViewModel : ViewModel() {

    private val useCase = CalcularSobrecimientoUseCase()

    private var partidaSeleccionada: PartidaMaterial? = null

    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun setPartida(partida: PartidaMaterial) {
        partidaSeleccionada = partida
    }

    fun calcular(
        largo: Double?,
        ancho: Double?,
        altura: Double?
    ) {
        if (largo == null || ancho == null || altura == null) {
            _resultados.value = listOf("Datos incompletos")
            return
        }

        if (partidaSeleccionada == null) {
            _resultados.value = listOf("Seleccione el tipo de sobrecimiento")
            return
        }

        val sobrecimiento = Sobrecimiento(
            largo = largo,
            ancho = ancho,
            altura = altura
        )

        _resultados.value = useCase.ejecutar(
            sobrecimiento = sobrecimiento,
            partida = partidaSeleccionada!!
        )
    }
}
