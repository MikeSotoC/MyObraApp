package com.uchicn.myobra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uchicn.myobra.ui.topniv.TopNivScreen
import com.uchicn.myobra.ui.poligonal.PoligonalScreen
import com.uchicn.myobra.ui.radiacion.RadiacionScreen
import com.uchicn.myobra.ui.historial.HistorialScreen

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
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHistorial = { navController.navigate(Screen.Historial.route) }
            )
        }
        composable(Screen.Poligonal.route) {
            PoligonalScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Radiacion.route) {
            RadiacionScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Historial.route) {
            HistorialScreen(
                onProyectoNivelacionClick = { id ->
                    // Navegar a nivelación con datos cargados (pendiente)
                    navController.popBackStack()
                },
                onProyectoPoligonalClick = { id ->
                    // Navegar a poligonal con datos cargados (pendiente)
                    navController.popBackStack()
                }
            )
        }
    }
}
