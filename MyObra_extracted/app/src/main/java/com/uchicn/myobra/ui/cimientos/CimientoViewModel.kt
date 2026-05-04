package com.uchicn.myobra.ui.cimientos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.cimiento.Cimiento
import com.uchicn.myobra.core.common.partida.PartidaMaterial
import com.uchicn.myobra.core.domain.model.cimiento.CalcularCimiento

class CimientoViewModel : ViewModel() {

    private val useCase = CalcularCimiento()

    private var dosificacionSeleccionada: PartidaMaterial? = null

    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun setDosificacion(partida: PartidaMaterial) {
        dosificacionSeleccionada = partida
    }

    fun calcularCimiento(
        largo: Double?,
        ancho: Double?,
        altura: Double?
    ) {
        if (largo == null || ancho == null || altura == null) {
            _resultados.value = listOf("Datos incompletos")
            return
        }

        if (dosificacionSeleccionada == null) {
            _resultados.value = listOf("Seleccione una dosificación de concreto")
            return
        }

        val cimiento = Cimiento(
            largo = largo,
            ancho = ancho,
            altura = altura
        )

        _resultados.value = useCase.calcularCimiento(
            cimiento = cimiento,
            partida = dosificacionSeleccionada!!
        )
    }
}

