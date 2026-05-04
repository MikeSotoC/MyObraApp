package com.uchicn.myobra.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Nivelacion : Screen("nivelacion")
    object Poligonal : Screen("poligonal")
    object Radiacion : Screen("radiacion")
}
