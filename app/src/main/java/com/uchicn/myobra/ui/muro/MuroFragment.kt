// MuroFragment.kt
package com.uchicn.myobra.ui.muro

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.domain.model.muro.TipoAsentado
import com.uchicn.myobra.core.domain.model.muro.TipoMortero
import com.uchicn.myobra.databinding.FragmentMuroBinding

class MuroFragment : Fragment(R.layout.fragment_muro) {

    private val viewModel: MuroViewModel by viewModels()

    private var _binding: FragmentMuroBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMuroBinding.bind(view)

        val adapterAsentado = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            TipoAsentado.values().map { it.nombre }
        )
        binding.spAsentado.setAdapter(adapterAsentado)
        binding.spAsentado.setOnItemClickListener { _, _, position, _ ->
            viewModel.setAsentado(TipoAsentado.values()[position])
        }

        val adapterMortero = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            TipoMortero.values().map { it.nombre }
        )
        binding.spMortero.setAdapter(adapterMortero)
        binding.spMortero.setOnItemClickListener { _, _, position, _ ->
            viewModel.setMortero(TipoMortero.values()[position])
        }

        binding.btnCalcularMuro.setOnClickListener {
            val resultados = viewModel.calcular(
                areaMuroM2 = binding.etAreaMuro.text.toString().toDoubleOrNull(),
                largoLadCm = binding.etLargoLad.text.toString().toDoubleOrNull(),
                anchoLadCm = binding.etAnchoLad.text.toString().toDoubleOrNull(),
                altoLadCm = binding.etAltoLad.text.toString().toDoubleOrNull(),
                juntaHorizontalCm = binding.etJuntaH.text.toString().toDoubleOrNull(),
                juntaVerticalCm = binding.etJuntaV.text.toString().toDoubleOrNull(),
                desperdicioLadrilloPct =
                    binding.etDesperdicioLadrillo.text.toString().toDoubleOrNull() ?: 0.0,
                desperdicioMorteroPct =
                    binding.etDesperdicioMortero.text.toString().toDoubleOrNull() ?: 0.0
            )

            AlertDialog.Builder(requireContext())
                .setTitle("Resultado – Muro de ladrillo")
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
