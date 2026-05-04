package com.uchicn.myobra.ui.cimientos

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.common.partida.PartidasCimiento
import com.uchicn.myobra.databinding.FragmentCimientoBinding

class CimientoFragment : Fragment(R.layout.fragment_cimiento) {

    private val viewModel: CimientoViewModel by viewModels()

    private var _binding: FragmentCimientoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCimientoBinding.bind(view)

        val adapterDosificacion = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            PartidasCimiento.TODAS.map { it.nombre }
        )

        binding.spDosificacion.setAdapter(adapterDosificacion)

        binding.spDosificacion.setOnItemClickListener { _, _, position, _ ->
            viewModel.setDosificacion(PartidasCimiento.TODAS[position])
        }

        viewModel.resultados.observe(viewLifecycleOwner) { resultados ->
            AlertDialog.Builder(requireContext())
                .setTitle("Materiales")
                .setMessage(resultados.joinToString("\n"))
                .setPositiveButton("OK", null)
                .show()
        }

        binding.btnCalcularCimiento.setOnClickListener {
            viewModel.calcularCimiento(
                binding.etLargo.text.toString().toDoubleOrNull(),
                binding.etAncho.text.toString().toDoubleOrNull(),
                binding.etAltura.text.toString().toDoubleOrNull()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}