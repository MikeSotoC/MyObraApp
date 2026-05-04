package com.uchicn.myobra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uchicn.myobra.ui.topniv.TopNivScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            // Pantalla Home - se implementará después
            TopNivScreen(onNavigateBack = {})
        }
        composable(Screen.Nivelacion.route) {
            TopNivScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Poligonal.route) {
            // Placeholder para pantalla de poligonal
            // Se implementará la UI completa más adelante
        }
        composable(Screen.Radiacion.route) {
            // Placeholder para pantalla de radiación
            // Se implementará la UI completa más adelante
        }
    }
}
