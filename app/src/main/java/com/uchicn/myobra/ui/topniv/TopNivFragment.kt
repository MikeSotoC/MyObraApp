package com.uchicn.myobra.ui.topniv

import android.os.Bundle
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uchicn.myobra.R
import com.uchicn.myobra.core.domain.model.topo.PuntoPerfil
import com.uchicn.myobra.core.domain.topografia.TopNivUiState
import com.uchicn.myobra.databinding.FragmentTopNivBinding
import com.uchicn.myobra.ui.util.*

class TopNivFragment : Fragment(R.layout.fragment_top_niv) {

    private val viewModel: TopNivViewModel by viewModels()
    private var _binding: FragmentTopNivBinding? = null
    private val binding get() = _binding!!

    // ===== ESTADO =====
    private val lecturasInputs = mutableListOf<EditText>()
    private val lecturasGuardadas = mutableListOf<Double>()
    private var perfilGenerado: List<PuntoPerfil>? = null

    private var ultimaDistancia: Double? = null
    private var ultimoIntervalo: Double? = null

    // ======================================================
    // LIFECYCLE
    // ======================================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopNivBinding.bind(view)

        setupFilters()
        setupInitialState()
        setupObservers()
        setupValidaciones()
        setupActions()

        actualizarEstadoGenerarLecturas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ======================================================
    // SETUP
    // ======================================================

    private fun setupFilters() {
        val f3 = arrayOf(DecimalDigitsInputFilter(3))
        with(binding) {
            etBzInicial.filters = f3
            etBzFinal.filters = f3
            etDistancia.filters = f3
            etIntervalo.filters = f3
            etCotaBm.filters = f3
            etLecturaBm.filters = f3
            etDiametro.filters = f3
            etCama.filters = f3
            etSobrecama.filters = f3
            etRellenoPropio.filters = f3
            etRellenoPrestamo.filters = f3
            etDesperdicio.filters = f3
            etEspesorAsfalto.filters = f3
        }
    }

    private fun setupInitialState() {
        with(binding) {
            btnGenerarLecturas.isEnabled = false
            btnVerPerfil.isEnabled = false
            btnMateriales.isEnabled = false

            chkMateriales.visibility = View.GONE
            chkMateriales.isChecked = false
            layoutMateriales.visibility = View.GONE
        }
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is TopNivUiState.MostrarResultados ->
                    mostrarResultados(state.resultados, state.titulo)
                /*is TopNivUiState.MostrarPendiente ->
                    SnackbarUtil.show(
                        requireView(),
                        "Pendiente: %.2f %%".format(state.pendientePorcentaje)
                    )*/
                is TopNivUiState.Error ->
                    SnackbarUtil.show(requireView(), state.mensaje)
                else -> Unit
            }
        }
    }

    private fun setupValidaciones() {
        val watcher: (CharSequence?) -> Unit = {
            actualizarEstadoGenerarLecturas()
        }

        with(binding) {
            etCotaBm.addTextChangedListener { watcher(it) }
            etLecturaBm.addTextChangedListener { watcher(it) }
            etBzInicial.addTextChangedListener { watcher(it) }
            etBzFinal.addTextChangedListener { watcher(it) }
            etDistancia.addTextChangedListener { watcher(it) }
            etIntervalo.addTextChangedListener { watcher(it) }
            etDiametro.addTextChangedListener { watcher(it) }
            etCama.addTextChangedListener { watcher(it) }
        }
    }

    private fun setupActions() {
        setupBtnGenerarLecturas()
        setupBtnNivelacion()
        setupBtnMateriales()
        setupBtnVerPerfil()
    }

    // ======================================================
    // BOTONES
    // ======================================================

    private fun setupBtnGenerarLecturas() {
        binding.btnGenerarLecturas.setOnClickListener {
            mostrarDialogoLecturas()
        }
    }

    private fun setupBtnNivelacion() {
        binding.btnLecturas.setOnClickListener {
            calcularNivelacion(true)
        }

        binding.btnCotas.setOnClickListener {
            calcularNivelacion(false)
        }
    }

    private fun setupBtnMateriales() {
        binding.chkMateriales.setOnCheckedChangeListener { _, checked ->
            binding.layoutMateriales.visibility =
                if (checked) View.VISIBLE else View.GONE
            binding.btnMateriales.isEnabled = checked
        }

        binding.btnMateriales.setOnClickListener {
            val perfil = perfilGenerado ?: run {
                SnackbarUtil.show(requireView(), "Primero genere el perfil")
                return@setOnClickListener
            }

            viewModel.calcularMateriales(
                perfil,
                binding.etAnchoZanja.text.toString().toDoubleOrNull(),
                binding.etDiametro.text.toString().toDoubleOrNull(),
                binding.etCama.text.toString().toDoubleOrNull(),
                binding.etSobrecama.text.toString().toDoubleOrNull(),
                binding.etRellenoPropio.text.toString().toDoubleOrNull(),
                binding.etRellenoPrestamo.text.toString().toDoubleOrNull(),
                binding.etDesperdicio.text.toString().toDoubleOrNull(),
                obtenerEspesorAsfalto()
            )
        }

        binding.chkAsfalto.setOnCheckedChangeListener { _, checked ->
            binding.layoutEspesorAsfalto.visibility =
                if (checked) View.VISIBLE else View.GONE

            if (!checked) {
                binding.etEspesorAsfalto.text = null
            }
        }

    }

    private fun setupBtnVerPerfil() {
        binding.btnVerPerfil.setOnClickListener {
            perfilGenerado?.let { mostrarPerfilGrafico(it) }
        }
    }

    // ======================================================
    // LÓGICA PRINCIPAL
    // ======================================================

    private fun mostrarDialogoLecturas() {

        val distancia = binding.etDistancia.text.toString().toDoubleOrNull()
        val intervalo = binding.etIntervalo.text.toString().toDoubleOrNull()

        if (distancia == null || intervalo == null || intervalo <= 0) {
            SnackbarUtil.show(requireView(), "Ingrese distancia e intervalo válidos")
            return
        }

        if (configCambio(distancia, intervalo)) {
            lecturasGuardadas.clear()
            ultimaDistancia = distancia
            ultimoIntervalo = intervalo

            SnackbarUtil.show(
                requireView(),
                "Distancia o intervalo cambiaron. Lecturas reiniciadas"
            )
        }

        lecturasInputs.clear()

        val container = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 16, 24, 0)
        }

        val progresivas = ProgresivaUtil.generarProgresivas(distancia, intervalo)

        progresivas.forEachIndexed { index, prog ->

            val til = TextInputLayout(requireContext()).apply {
                boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
                isHintEnabled = true
                hint = String.format("0+%06.2f", prog)
            }

            val et = TextInputEditText(requireContext()).apply {
                inputType =
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                filters = arrayOf(DecimalDigitsInputFilter(3))

                if (lecturasGuardadas.size > index) {
                    setText(lecturasGuardadas[index].toString())
                }
            }

            til.addView(et)
            container.addView(til)
            lecturasInputs.add(et)
        }

        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.ThemeOverlay_MyObra_InputDialog
        )
            .setTitle("Ingresar lecturas")
            .setView(ScrollView(requireContext()).apply { addView(container) })
            .setPositiveButton("Aceptar") { _, _ ->

                if (lecturasInputs.any { it.text.isNullOrBlank() }) {
                    SnackbarUtil.show(requireView(), "Complete todas las lecturas")
                    return@setPositiveButton
                }

                lecturasGuardadas.clear()
                lecturasGuardadas.addAll(
                    lecturasInputs.map { it.text.toString().toDouble() }
                )

                perfilGenerado = viewModel.generarPerfil(
                    binding.etCotaBm.text.toString().toDoubleOrNull(),
                    binding.etLecturaBm.text.toString().toDoubleOrNull(),
                    intervalo,
                    lecturasGuardadas,
                    distancia
                )

                binding.chkMateriales.visibility = View.VISIBLE
                binding.btnVerPerfil.isEnabled = true

                SnackbarUtil.show(requireView(), "Perfil generado")
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun obtenerEspesorAsfalto(): Double? {
        return if (binding.chkAsfalto.isChecked) {
            binding.etEspesorAsfalto.text.toString().toDoubleOrNull()
        } else {
            null
        }
    }

    private fun calcularNivelacion(soloLecturas: Boolean) {
        viewModel.calcularNivelacion(
            binding.etCotaBm.text.toString().toDoubleOrNull(),
            binding.etLecturaBm.text.toString().toDoubleOrNull(),
            binding.etBzInicial.text.toString().toDoubleOrNull(),
            binding.etBzFinal.text.toString().toDoubleOrNull(),
            binding.etDistancia.text.toString().toDoubleOrNull(),
            binding.etIntervalo.text.toString().toDoubleOrNull(),
            binding.etDiametro.text.toString().toDoubleOrNull(),
            binding.etCama.text.toString().toDoubleOrNull(),
            soloLecturas
        )
    }

    // ======================================================
    // VALIDACIÓN
    // ======================================================

    private fun actualizarEstadoGenerarLecturas() {
        binding.btnGenerarLecturas.isEnabled =
            !binding.etCotaBm.text.isNullOrBlank() &&
                    !binding.etLecturaBm.text.isNullOrBlank() &&
                    !binding.etBzInicial.text.isNullOrBlank() &&
                    !binding.etBzFinal.text.isNullOrBlank() &&
                    !binding.etDistancia.text.isNullOrBlank() &&
                    !binding.etIntervalo.text.isNullOrBlank() &&
                    !binding.etDiametro.text.isNullOrBlank() &&
                    !binding.etCama.text.isNullOrBlank()
    }

    private fun configCambio(distancia: Double, intervalo: Double): Boolean {
        return distancia != ultimaDistancia || intervalo != ultimoIntervalo
    }

    // ======================================================
    // RESULTADOS
    // ======================================================

    private fun mostrarResultados(resultados: List<String>, titulo: String) {

        val texto = resultados.joinToString("\n")

        val tv = TextView(requireContext()).apply {
            text = texto
            setPadding(24, 24, 24, 24)
            movementMethod = ScrollingMovementMethod()
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titulo)
            .setView(ScrollView(requireContext()).apply { addView(tv) })
            .setNeutralButton("Copiar") { _, _ ->
                ClipboardUtil.copiar(requireContext(), requireView(), texto)
                SnackbarUtil.show(requireView(), "Copiado")
            }
            .setNegativeButton("Exportar CSV") { _, _ ->
                DialogUtil.pedirNombreArchivo(requireContext()) { nombre ->
                    if (!StoragePermissionUtil.check(requireContext())) return@pedirNombreArchivo
                    CsvExportUtil.exportarNivelacion(requireContext(), nombre, resultados)
                    SnackbarUtil.show(requireView(), "CSV guardado")
                }
            }
            .setPositiveButton("OK", null)
            .show()
    }

    // ======================================================
    // PERFIL GRÁFICO
    // ======================================================

    private fun mostrarPerfilGrafico(perfil: List<PuntoPerfil>) {

        val view = layoutInflater.inflate(R.layout.dialog_perfil, null)
        val chart =
            view.findViewById<com.github.mikephil.charting.charts.LineChart>(R.id.chartPerfil)

        // ================= COLORES DESDE RESOURCES =================
        val colorTerreno =
            ContextCompat.getColor(requireContext(), R.color.perfil_terreno)
        val colorRasante =
            ContextCompat.getColor(requireContext(), R.color.perfil_rasante)
        val colorGrid =
            ContextCompat.getColor(requireContext(), R.color.perfil_grid)
        val colorTexto =
            ContextCompat.getColor(requireContext(), R.color.perfil_texto)

        // ================= RANGO X =================
        val minX = perfil.first().progresiva.toFloat()
        val maxX = perfil.last().progresiva.toFloat()
        val rangoX = maxX - minX

        // ================= TERRENO =================
        val terrenoEntries = perfil.map {
            Entry(it.progresiva.toFloat(), it.cotaSuperficie.toFloat())
        }

        val terrenoDataSet = LineDataSet(terrenoEntries, "Terreno").apply {
            setDrawCircles(false)
            setDrawValues(false)
            lineWidth = 2f
            color = colorTerreno
        }

        // ================= RASANTE =================
        val rasante = viewModel.obtenerRasante()

        val rasanteDataSet = rasante?.let {
            val entries = it.map { p ->
                Entry(p.progresiva.toFloat(), p.cota.toFloat())
            }

            LineDataSet(entries, "Rasante").apply {
                setDrawCircles(false)
                setDrawValues(false)
                lineWidth = 2.5f
                color = colorRasante
                enableDashedLine(12f, 6f, 0f)
            }
        }

        val dataSets =
            mutableListOf<com.github.mikephil.charting.interfaces.datasets.ILineDataSet>().apply {
                add(terrenoDataSet)
                rasanteDataSet?.let { add(it) }
            }

        chart.data = LineData(dataSets)

        // ================= EJE X (PROGRESIVAS) =================
        chart.setExtraOffsets(20f, 24f, 20f, 48f)

        chart.xAxis.apply {
            axisMinimum = minX
            axisMaximum = maxX

            granularity = rangoX / maxOf(1f, perfil.size - 1f)
            isGranularityEnabled = true

            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(true)
            gridColor = colorGrid

            labelRotationAngle = -90f
            textSize = 10f
            textColor = colorTexto

            setAvoidFirstLastClipping(false)

            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "0+%.2f".format(value)
                }
            }
        }

        // ================= EJE Y =================
        chart.axisRight.isEnabled = false
        chart.axisLeft.apply {
            setDrawGridLines(true)
            gridColor = colorGrid
            textColor = colorTexto
        }

        // ================= ESCALA VISUAL COHERENTE =================
        val minY = perfil.minOf { it.cotaSuperficie }.toFloat()
        val maxY = perfil.maxOf { it.cotaSuperficie }.toFloat()
        val centroY = (minY + maxY) / 2f

        chart.axisLeft.axisMinimum = centroY - rangoX / 2f
        chart.axisLeft.axisMaximum = centroY + rangoX / 2f

        // ================= PENDIENTE RASANTE =================
        rasante?.let {
            val dz = it.last().cota - it.first().cota
            val dx = it.last().progresiva - it.first().progresiva
            val pendientePct = if (dx != 0.0) (dz / dx) * 100 else 0.0

            chart.description.apply {
                isEnabled = true
                text = "Pendiente rasante: %.2f %%".format(pendientePct)
                textSize = 12f
                textColor = colorTexto
            }
        }

        // ================= LEYENDA =================
        chart.legend.apply {
            isEnabled = true
            textColor = colorTexto
            textSize = 12f
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
            yOffset = 12f
        }

        // ================= UX =================
        chart.setScaleEnabled(true)
        chart.isDragEnabled = true
        chart.setPinchZoom(true)
        chart.invalidate()

        // ================= DIALOG =================
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Perfil longitudinal")
            .setView(view)
            .setPositiveButton("Cerrar", null)
            .show()
    }

}
