package com.uchicn.myobra.ui.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {
    fun show(view: View, msg: String) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .show()
    }

    fun showError(view: View, msg: String) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .show()
    }
}