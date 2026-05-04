package com.uchicn.myobra.ui.losa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.losa.LadrilloLosa
import com.uchicn.myobra.core.domain.model.losa.LosaAligerada
import com.uchicn.myobra.core.domain.model.losa.Vigueta
import com.uchicn.myobra.core.domain.model.losa.CalcularLosa
import com.uchicn.myobra.core.common.partida.PartidasConcreto
import com.uchicn.myobra.core.common.partida.PartidaMaterial

class LosaViewModel : ViewModel() {

    private val useCase = CalcularLosa()

    val concretosDisponibles: List<PartidaMaterial> =
        PartidasConcreto.TODAS

    private var concretoSeleccionado: PartidaMaterial =
        PartidasConcreto.FC_210

    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun setConcreto(concreto: PartidaMaterial) {
        concretoSeleccionado = concreto
    }

    fun calcular(
        largoM: Double?,
        anchoM: Double?,
        capaCompresionCm: Double?,
        anchoViguetaCm: Double?,
        alturaViguetaCm: Double?,
        separacionViguetaCm: Double?,
        largoLad: Double?,
        anchoLad: Double?,
        altoLad: Double?,
        desperdicioLad: Double?,
        desperdicioConc: Double?
    ) {

        if (
            largoM == null || anchoM == null ||
            capaCompresionCm == null ||
            anchoViguetaCm == null || alturaViguetaCm == null || separacionViguetaCm == null ||
            largoLad == null || anchoLad == null || altoLad == null ||
            desperdicioLad == null || desperdicioConc == null
        ) {
            _resultados.value = listOf("Datos incompletos")
            return
        }

        val vigueta = Vigueta(
            anchoCm = anchoViguetaCm,
            alturaCm = alturaViguetaCm,
            separacionCm = separacionViguetaCm
        )

        val losa = LosaAligerada(
            largoM = largoM,
            anchoM = anchoM,
            capaCompresionCm = capaCompresionCm,
            vigueta = vigueta
        )

        val ladrillo = LadrilloLosa(
            largoCm = largoLad,
            anchoCm = anchoLad,
            altoCm = altoLad
        )

        _resultados.value = useCase.ejecutar(
            losa = losa,
            ladrillo = ladrillo,
            desperdicioLadrillo = desperdicioLad / 100.0,
            desperdicioConcreto = desperdicioConc / 100.0,
            partidaConcreto = concretoSeleccionado
        )
    }
}

