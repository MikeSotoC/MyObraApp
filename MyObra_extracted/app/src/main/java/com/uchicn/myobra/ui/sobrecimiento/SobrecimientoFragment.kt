// SobrecimientoFragment.kt
package com.uchicn.myobra.ui.sobrecimiento

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.common.partida.PartidasSobrecimiento
import com.uchicn.myobra.databinding.FragmentSobrecimientoBinding

class SobrecimientoFragment : Fragment(R.layout.fragment_sobrecimiento) {

    private val viewModel: SobrecimientoViewModel by viewModels()

    private var _binding: FragmentSobrecimientoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSobrecimientoBinding.bind(view)

        val adapterSobrecimiento = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            PartidasSobrecimiento.TODAS.map { it.nombre }
        )

        binding.spPartidaSobrecimiento.setAdapter(adapterSobrecimiento)

        binding.spPartidaSobrecimiento.setOnItemClickListener { _, _, position, _ ->
            viewModel.setPartida(PartidasSobrecimiento.TODAS[position])
        }

        binding.btnCalcularSobrecimiento.setOnClickListener {
            viewModel.calcular(
                largo = binding.etLargo.text.toString().toDoubleOrNull(),
                ancho = binding.etAncho.text.toString().toDoubleOrNull(),
                altura = binding.etAltura.text.toString().toDoubleOrNull()
            )
        }

        viewModel.resultados.observe(viewLifecycleOwner) { resultados ->
            AlertDialog.Builder(requireContext())
                .setTitle("Sobrecimiento")
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
