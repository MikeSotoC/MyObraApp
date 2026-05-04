package com.uchicn.myobra.ui.losa

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.databinding.FragmentLosaBinding

class LosaFragment : Fragment(R.layout.fragment_losa) {

    private val viewModel: LosaViewModel by viewModels()
    private var _binding: FragmentLosaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLosaBinding.bind(view)

        // ===============================
        // SPINNER CONCRETO (f'c)
        // ===============================
        val adapterConcreto = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.concretosDisponibles.map { it.nombre }
        )

        binding.spinnerConcreto.setAdapter(adapterConcreto)

        binding.spinnerConcreto.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val concretoSeleccionado =
                        viewModel.concretosDisponibles[position]

                    viewModel.setConcreto(concretoSeleccionado)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // No hacer nada
                }
            }

        // ===============================
        // BOTÓN CALCULAR
        // ===============================
        binding.btnCalcular.setOnClickListener {
            viewModel.calcular(
                // Losa
                binding.etLargoLosa.text.toString().toDoubleOrNull(),
                binding.etAnchoLosa.text.toString().toDoubleOrNull(),
                binding.etCapaCompresion.text.toString().toDoubleOrNull(),

                // Vigueta
                binding.etAnchoVigueta.text.toString().toDoubleOrNull(),
                binding.etAlturaVigueta.text.toString().toDoubleOrNull(),
                binding.etSeparacionVigueta.text.toString().toDoubleOrNull(),

                // Ladrillo
                binding.etLargoLadrillo.text.toString().toDoubleOrNull(),
                binding.etAnchoLadrillo.text.toString().toDoubleOrNull(),
                binding.etAltoLadrillo.text.toString().toDoubleOrNull(),

                // Desperdicios
                binding.etDesperdicioLadrillo.text.toString().toDoubleOrNull(),
                binding.etDesperdicioConcreto.text.toString().toDoubleOrNull()
            )
        }

        // ===============================
        // RESULTADOS
        // ===============================
        viewModel.resultados.observe(viewLifecycleOwner) { resultados ->
            AlertDialog.Builder(requireContext())
                .setTitle("Losa aligerada – resultados")
                .setMessage(resultados.joinToString("\n"))
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
