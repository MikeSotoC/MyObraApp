package com.uchicn.myobra.ui.columna

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.uchicn.myobra.R
import com.uchicn.myobra.core.domain.model.acero.DiametroAcero
import com.uchicn.myobra.core.common.partida.PartidasConcreto
import com.uchicn.myobra.databinding.FragmentColumnaBinding

class ColumnaFragment : Fragment(R.layout.fragment_columna) {

    private val viewModel: ColumnaViewModel by viewModels()

    private var _binding: FragmentColumnaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentColumnaBinding.bind(view)

        // ================= CONCRETO =================
        val adapterConcreto = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            PartidasConcreto.TODAS.map { it.nombre }
        )
        binding.spPartidaColumna.setAdapter(adapterConcreto)
        binding.spPartidaColumna.setOnItemClickListener { _, _, pos, _ ->
            viewModel.setPartidaConcreto(PartidasConcreto.TODAS[pos])
        }

        // ================= ACERO =================
        val adapterDiametros = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            DiametroAcero.values()
        )

        binding.spDiametroLongitudinal.setAdapter(adapterDiametros)
        binding.spDiametroEstribo.setAdapter(adapterDiametros)

        binding.spDiametroLongitudinal.setOnItemClickListener { _, _, pos, _ ->
            viewModel.setDiametroLongitudinal(DiametroAcero.values()[pos])
        }

        binding.spDiametroEstribo.setOnItemClickListener { _, _, pos, _ ->
            viewModel.setDiametroEstribo(DiametroAcero.values()[pos])
        }

        // ================= BOTÓN =================
        binding.btnCalcularColumna.setOnClickListener {
            viewModel.calcular(
                ancho = binding.etAncho.text.toString().toDoubleOrNull(),
                largo = binding.etLargo.text.toString().toDoubleOrNull(),
                altura = binding.etAltura.text.toString().toDoubleOrNull(),
                cantidad = binding.etCantidad.text.toString().toIntOrNull(),

                // ACERO
                cantidadBarras = binding.etCantidadBarras.text.toString().toIntOrNull(),
                perimetroEstribo = binding.etPerimetroEstribo.text.toString().toDoubleOrNull(),

                tramo1Cantidad = binding.etTramo1Cantidad.text.toString().toIntOrNull(),
                tramo1Esp = binding.etTramo1Esp.text.toString().toDoubleOrNull(),

                tramo2Cantidad = binding.etTramo2Cantidad.text.toString().toIntOrNull(),
                tramo2Esp = binding.etTramo2Esp.text.toString().toDoubleOrNull()
            )
        }

        // ================= RESULTADOS =================
        viewModel.resultados.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Columnas – Concreto + Acero")
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
