package com.uchicn.myobra.ui.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText

object DialogUtil {
    fun pedirNombreArchivo(
        context: Context,
        onAceptar: (String) -> Unit
    ) {
        val et = EditText(context).apply {
            hint = "Nombre del archivo"
        }

        AlertDialog.Builder(context)
            .setTitle("Exportar CSV")
            .setView(et)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = et.text.toString().trim()
                if (nombre.isNotEmpty()) {
                    onAceptar(nombre)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}