// HomeViewModel.kt
package com.uchicn.myobra.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Seleccioná un módulo desde el menú para comenzar a calcular los materiales de tu obra."
    }
    val text: LiveData<String> = _text
}
