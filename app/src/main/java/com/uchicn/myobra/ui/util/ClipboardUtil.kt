package com.uchicn.myobra.ui.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View

object ClipboardUtil {

    fun copiar(context: Context, view: View, texto: String) {

        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        clipboard.setPrimaryClip(
            ClipData.newPlainText("Resultados", texto)
        )

        // Feedback Material
        SnackbarUtil.show(view, "Copiado al portapapeles")
    }
}
