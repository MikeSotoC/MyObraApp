// VigaFragment.kt (Material – Exposed Dropdown)
package com.uchicn.myobra.ui.viga

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.common.partida.PartidasConcreto
import com.uchicn.myobra.databinding.FragmentVigaBinding

class VigaFragment : Fragment(R.layout.fragment_viga) {

    private val viewModel: VigaViewModel by viewModels()

    private var _binding: FragmentVigaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVigaBinding.bind(view)

        val partidas = PartidasConcreto.TODAS

        val adapterConcreto = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            partidas.map { it.nombre }
        )

        binding.spPartida.setAdapter(adapterConcreto)

        binding.spPartida.setOnItemClickListener { _, _, position, _ ->
            viewModel.setPartida(partidas[position])
        }

        binding.btnCalcularViga.setOnClickListener {
            viewModel.calcular(
                largo = binding.etLargo.text.toString().toDoubleOrNull(),
                ancho = binding.etAncho.text.toString().toDoubleOrNull(),
                altura = binding.etAltura.text.toString().toDoubleOrNull(),
                cantidad = binding.etCantidad.text.toString().toIntOrNull()
            )
        }

        viewModel.resultados.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Vigas")
                .setMessage(it.joinToString("\n"))
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
