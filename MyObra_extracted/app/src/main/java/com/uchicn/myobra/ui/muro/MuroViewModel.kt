// MuroViewModel.kt
package com.uchicn.myobra.ui.muro

import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.muro.LadrilloInput
import com.uchicn.myobra.core.domain.model.muro.MuroInput
import com.uchicn.myobra.core.domain.model.muro.TipoAsentado
import com.uchicn.myobra.core.domain.model.muro.TipoMortero
import com.uchicn.myobra.core.domain.model.muro.CalcularMuro

class MuroViewModel : ViewModel() {

    private val useCase = CalcularMuro()

    private var asentadoSeleccionado: TipoAsentado? = null
    private var morteroSeleccionado: TipoMortero? = null

    fun setAsentado(asentado: TipoAsentado) {
        asentadoSeleccionado = asentado
    }

    fun setMortero(mortero: TipoMortero) {
        morteroSeleccionado = mortero
    }

    fun calcular(
        areaMuroM2: Double?,
        largoLadCm: Double?,
        anchoLadCm: Double?,
        altoLadCm: Double?,
        juntaHorizontalCm: Double?,
        juntaVerticalCm: Double?,
        desperdicioLadrilloPct: Double,
        desperdicioMorteroPct: Double
    ): List<String> {

        if (
            areaMuroM2 == null ||
            largoLadCm == null || anchoLadCm == null || altoLadCm == null ||
            juntaHorizontalCm == null || juntaVerticalCm == null
        ) {
            return listOf("Datos incompletos")
        }

        if (asentadoSeleccionado == null || morteroSeleccionado == null) {
            return listOf("Seleccione tipo de asentado y mortero")
        }

        val input = MuroInput(
            areaMuroM2 = areaMuroM2,
            ladrillo = LadrilloInput(
                largoCm = largoLadCm,
                anchoCm = anchoLadCm,
                altoCm = altoLadCm
            ),
            asentado = asentadoSeleccionado!!,
            juntaHorizontalCm = juntaHorizontalCm,
            juntaVerticalCm = juntaVerticalCm,
            tipoMortero = morteroSeleccionado!!,
            desperdicioLadrilloPct = desperdicioLadrilloPct,
            desperdicioMorteroPct = desperdicioMorteroPct
        )

        return useCase.ejecutar(input)
    }
}
