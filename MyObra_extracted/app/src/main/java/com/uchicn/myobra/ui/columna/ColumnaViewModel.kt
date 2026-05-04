package com.uchicn.myobra.ui.columna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uchicn.myobra.core.domain.model.acero.AceroEstribo
import com.uchicn.myobra.core.domain.model.acero.AceroLongitudinal
import com.uchicn.myobra.core.domain.model.acero.ColumnaAceroInput
import com.uchicn.myobra.core.domain.model.acero.DiametroAcero
import com.uchicn.myobra.core.domain.model.acero.GanchoConfig
import com.uchicn.myobra.core.domain.model.acero.TipoGancho
import com.uchicn.myobra.core.domain.model.acero.TramoEspaciamiento
import com.uchicn.myobra.core.domain.model.columna.Columna
import com.uchicn.myobra.core.common.partida.PartidaMaterial
import com.uchicn.myobra.core.domain.model.columna.CalcularColumnaAcero
import com.uchicn.myobra.core.domain.model.columna.CalcularColumna

class ColumnaViewModel : ViewModel() {

    private val concretoUseCase = CalcularColumna()
    private val aceroUseCase = CalcularColumnaAcero()
    private var partidaConcreto: PartidaMaterial? = null
    private var diametroLongitudinal: DiametroAcero? = null
    private var diametroEstribo: DiametroAcero? = null
    private val _resultados = MutableLiveData<List<String>>()
    val resultados: LiveData<List<String>> = _resultados

    fun setPartidaConcreto(p: PartidaMaterial) {
        partidaConcreto = p
    }

    fun setDiametroLongitudinal(d: DiametroAcero) {
        diametroLongitudinal = d
    }

    fun setDiametroEstribo(d: DiametroAcero) {
        diametroEstribo = d
    }

    fun calcular(
        ancho: Double?,
        largo: Double?,
        altura: Double?,
        cantidad: Int?,

        cantidadBarras: Int?,
        perimetroEstribo: Double?,

        tramo1Cantidad: Int?,
        tramo1Esp: Double?,

        tramo2Cantidad: Int?,
        tramo2Esp: Double?
    ) {

        if (
            ancho == null || largo == null || altura == null || cantidad == null ||
            cantidadBarras == null || perimetroEstribo == null ||
            tramo1Cantidad == null || tramo1Esp == null ||
            tramo2Cantidad == null || tramo2Esp == null ||
            partidaConcreto == null ||
            diametroLongitudinal == null || diametroEstribo == null
        ) {
            _resultados.value = listOf("Datos incompletos")
            return
        }

        // ===== CONCRETO =====
        val columna = Columna(ancho, largo, altura, cantidad)
        val concreto = concretoUseCase.ejecutar(columna, partidaConcreto!!)

        // ===== ACERO =====
        val aceroInput = ColumnaAceroInput(
            alturaColumnaM = altura,
            cantidadColumnas = cantidad,

            longitudinal = AceroLongitudinal(
                diametro = diametroLongitudinal!!,
                cantidadBarras = cantidadBarras,
                gancho = GanchoConfig(1, TipoGancho.GANCHO_90)
            ),

            estribo = AceroEstribo(
                diametro = diametroEstribo!!,
                tramos = listOf(
                    TramoEspaciamiento(tramo1Cantidad, tramo1Esp),
                    TramoEspaciamiento(tramo2Cantidad, tramo2Esp)
                ),
                gancho = GanchoConfig(2, TipoGancho.GANCHO_135)
            ),

            perimetroEstriboM = perimetroEstribo
        )

        val acero = aceroUseCase.ejecutar(aceroInput)

        _resultados.value = concreto + listOf("---- ACERO ----") + acero
    }
}
