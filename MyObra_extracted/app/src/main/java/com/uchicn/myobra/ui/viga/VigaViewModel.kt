// VigaViewModel.kt
package com.uchicn.myobra.ui.viga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.viga.Viga
import com.uchicn.myobra.core.common.partida.PartidaMaterial
import com.uchicn.myobra.core.domain.model.viga.CalcularVigaConcreto

class VigaViewModel : ViewModel() {

    private val useCase = CalcularVigaConcreto()
    private var partidaSeleccionada: PartidaMaterial? = null

    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun setPartida(partida: PartidaMaterial) {
        partidaSeleccionada = partida
    }

    fun calcular(
        largo: Double?,
        ancho: Double?,
        altura: Double?,
        cantidad: Int?
    ) {
        if (largo == null || ancho == null || altura == null || cantidad == null) {
            _resultados.value = listOf("Datos incompletos")
            return
        }

        if (partidaSeleccionada == null) {
            _resultados.value = listOf("Seleccione la resistencia del concreto")
            return
        }

        val viga = Viga(
            largo = largo,
            ancho = ancho,
            altura = altura,
            cantidad = cantidad
        )

        _resultados.value = useCase.ejecutar(
            volumenConcretoM3 = viga.volumenTotal(),
            partida = partidaSeleccionada!!
        )
    }
}
