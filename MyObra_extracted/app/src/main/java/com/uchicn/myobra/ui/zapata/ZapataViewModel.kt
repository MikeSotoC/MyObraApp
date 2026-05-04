// ZapataViewModel.kt
package com.uchicn.myobra.ui.zapata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.zapata.Zapata
import com.uchicn.myobra.core.common.partida.PartidaMaterial
import com.uchicn.myobra.core.domain.model.zapata.CalcularZapata

class ZapataViewModel : ViewModel() {

    private val useCase = CalcularZapata()
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
            _resultados.value = listOf("Seleccione la resistencia del concreto")
            return
        }

        val zapata = Zapata(largo, ancho, altura)

        _resultados.value = useCase.ejecutar(
            zapata = zapata,
            partida = partidaSeleccionada!!
        )
    }
}
