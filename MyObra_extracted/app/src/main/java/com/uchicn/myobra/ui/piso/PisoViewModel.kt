package com.uchicn.myobra.ui.piso

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.piso.Piso
import com.uchicn.myobra.core.domain.model.piso.DosificacionPiso
import com.uchicn.myobra.core.domain.model.piso.CalcularPiso

class PisoViewModel : ViewModel() {

    private val useCase = CalcularPiso()

    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun calcular(
        area: Double?,
        espesor: Double?,
        dosificacion: DosificacionPiso?
    ) {
        if (area == null || espesor == null || dosificacion == null) {
            _resultados.value = listOf("Datos incompletos o dosificación no seleccionada")
            return
        }

        val piso = Piso(area, espesor)

        _resultados.value = useCase.ejecutar(piso, dosificacion)
    }
}
