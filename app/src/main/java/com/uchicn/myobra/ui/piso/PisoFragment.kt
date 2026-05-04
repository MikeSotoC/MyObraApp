package com.uchicn.myobra.ui.piso

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.domain.model.piso.DosificacionPiso
import com.uchicn.myobra.databinding.FragmentPisoBinding

class PisoFragment : Fragment(R.layout.fragment_piso) {

    private val viewModel: PisoViewModel by viewModels()

    private var _binding: FragmentPisoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPisoBinding.bind(view)

        val falsopisos = DosificacionPiso.values()
            .filter { it.name.startsWith("FALSOPISO") }

        val contrapisos = DosificacionPiso.values()
            .filter { it.name.startsWith("CONTRAPISO") }

        binding.spFalsopiso.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                falsopisos.map { it.descripcion }
            )
        )

        binding.spPiso.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                contrapisos.map { it.descripcion }
            )
        )

        binding.btnCalcularFalsopiso.setOnClickListener {
            viewModel.calcular(
                area = binding.etAreaFalsopiso.text.toString().toDoubleOrNull(),
                espesor = binding.etEspesorFalsopiso.text.toString().toDoubleOrNull(),
                dosificacion = binding.spFalsopiso.tag as? DosificacionPiso
            )
        }

        binding.btnCalcularPiso.setOnClickListener {
            viewModel.calcular(
                area = binding.etAreaPiso.text.toString().toDoubleOrNull(),
                espesor = binding.etEspesorPiso.text.toString().toDoubleOrNull(),
                dosificacion = binding.spPiso.tag as? DosificacionPiso
            )
        }

        binding.spFalsopiso.setOnItemClickListener { _, _, position, _ ->
            binding.spFalsopiso.tag = falsopisos[position]
        }

        binding.spPiso.setOnItemClickListener { _, _, position, _ ->
            binding.spPiso.tag = contrapisos[position]
        }

        viewModel.resultados.observe(viewLifecycleOwner) { resultados ->
            AlertDialog.Builder(requireContext())
                .setTitle("Resultado")
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
