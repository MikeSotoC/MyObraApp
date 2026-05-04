package com.uchicn.myobra.ui.util

import android.text.InputFilter
import android.text.Spanned

class DecimalDigitsInputFilter(private val decimalDigits: Int) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {

        val newText =
            dest.substring(0, dstart) +
                    source.subSequence(start, end) +
                    dest.substring(dend)

        // permitir vacío (borrar todo)
        if (newText.isEmpty()) return null

        // solo números y un punto
        if (!newText.matches(Regex("^\\d*(\\.\\d*)?$"))) {
            return ""
        }

        // limitar decimales
        val index = newText.indexOf('.')
        if (index >= 0) {
            val decimalsCount = newText.length - index - 1
            if (decimalsCount > decimalDigits) {
                return ""
            }
        }

        return null
    }
}
