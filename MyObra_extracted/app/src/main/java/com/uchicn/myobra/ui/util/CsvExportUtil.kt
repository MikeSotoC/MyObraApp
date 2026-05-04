package com.uchicn.myobra.ui.util

import android.content.Context
import android.os.Environment
import com.uchicn.myobra.core.common.export.CsvFormatter
import java.io.File
import java.io.FileOutputStream

object CsvExportUtil {
    fun exportarNivelacion(
        context: Context,
        nombre: String,
        resultados: List<String>
    ) {
        val carpeta = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "MyObra"
        )

        if (!carpeta.exists()) carpeta.mkdirs()

        val archivo = File(carpeta, "$nombre.csv")

        FileOutputStream(archivo).use {
            it.write(CsvFormatter.nivelacion(resultados).toByteArray())
        }
    }
}