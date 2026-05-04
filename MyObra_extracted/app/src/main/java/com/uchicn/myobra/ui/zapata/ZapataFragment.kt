// ZapataFragment.kt
package com.uchicn.myobra.ui.zapata

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.common.partida.PartidasConcreto
import com.uchicn.myobra.databinding.FragmentZapataBinding

class ZapataFragment : Fragment(R.layout.fragment_zapata) {

    private val viewModel: ZapataViewModel by viewModels()
    private var _binding: FragmentZapataBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentZapataBinding.bind(view)

        val partidas = PartidasConcreto.TODAS

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            partidas.map { it.nombre }
        )

        binding.spPartidaZapata.setAdapter(adapter)

        binding.spPartidaZapata.setOnItemClickListener { _, _, position, _ ->
            viewModel.setPartida(partidas[position])
        }

        binding.btnCalcularZapata.setOnClickListener {
            viewModel.calcular(
                largo = binding.etLargo.text.toString().toDoubleOrNull(),
                ancho = binding.etAncho.text.toString().toDoubleOrNull(),
                altura = binding.etAltura.text.toString().toDoubleOrNull()
            )
        }

        viewModel.resultados.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Zapata")
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
