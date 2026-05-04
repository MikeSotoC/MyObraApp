package com.uchicn.myobra.ui.topniv

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uchicn.myobra.ui.topniv.components.NivelacionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNivScreen(
    viewModel: TopNivViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var cotaBm by remember { mutableStateOf("") }
    var lecturaBm by remember { mutableStateOf("") }
    var bzInicial by remember { mutableStateOf("") }
    var bzFinal by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var intervalo by remember { mutableStateOf("") }
    var diametro by remember { mutableStateOf("") }
    var cama by remember { mutableStateOf("") }
    
    // Estado para materiales
    var calcularMateriales by remember { mutableStateOf(false) }
    var anchoZanja by remember { mutableStateOf("") }
    var sobrecama by remember { mutableStateOf("") }
    var rellenoPropio by remember { mutableStateOf("") }
    var rellenoPrestamo by remember { mutableStateOf("") }
    var desperdicio by remember { mutableStateOf("") }
    var conAsfalto by remember { mutableStateOf(false) }
    var espesorAsfalto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nivelación Topográfica") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card de Nivelación
            NivelacionCard(
                cotaBm = cotaBm,
                lecturaBm = lecturaBm,
                bzInicial = bzInicial,
                bzFinal = bzFinal,
                distancia = distancia,
                intervalo = intervalo,
                diametro = diametro,
                cama = cama,
                onCotaBmChange = { cotaBm = it },
                onLecturaBmChange = { lecturaBm = it },
                onBzInicialChange = { bzInicial = it },
                onBzFinalChange = { bzFinal = it },
                onDistanciaChange = { distancia = it },
                onIntervaloChange = { intervalo = it },
                onDiametroChange = { diametro = it },
                onCamaChange = { cama = it },
                onGenerarLecturasClick = {
                    viewModel.generarPerfilYMostrarDialogo(
                        cotaBm.toDoubleOrNull(),
                        lecturaBm.toDoubleOrNull(),
                        intervalo.toDoubleOrNull(),
                        distancia.toDoubleOrNull()
                    )
                },
                onLecturasClick = {
                    viewModel.calcularNivelacion(
                        cotaBm = cotaBm.toDoubleOrNull(),
                        lecturaBm = lecturaBm.toDoubleOrNull(),
                        bzIni = bzInicial.toDoubleOrNull(),
                        bzFin = bzFinal.toDoubleOrNull(),
                        distancia = distancia.toDoubleOrNull(),
                        intervalo = intervalo.toDoubleOrNull(),
                        diametroMm = diametro.toDoubleOrNull(),
                        camaCm = cama.toDoubleOrNull(),
                        soloLecturas = true
                    )
                },
                onCotasClick = {
                    viewModel.calcularNivelacion(
                        cotaBm = cotaBm.toDoubleOrNull(),
                        lecturaBm = lecturaBm.toDoubleOrNull(),
                        bzIni = bzInicial.toDoubleOrNull(),
                        bzFin = bzFinal.toDoubleOrNull(),
                        distancia = distancia.toDoubleOrNull(),
                        intervalo = intervalo.toDoubleOrNull(),
                        diametroMm = diametro.toDoubleOrNull(),
                        camaCm = cama.toDoubleOrNull(),
                        soloLecturas = false
                    )
                }
            )
            
            // Checkbox para materiales
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = calcularMateriales,
                    onCheckedChange = { calcularMateriales = it }
                )
                Text("Calcular materiales")
            }
            
            // Sección de materiales (condicional)
            if (calcularMateriales) {
                MaterialesSection(
                    anchoZanja = anchoZanja,
                    sobrecama = sobrecama,
                    rellenoPropio = rellenoPropio,
                    rellenoPrestamo = rellenoPrestamo,
                    desperdicio = desperdicio,
                    conAsfalto = conAsfalto,
                    espesorAsfalto = espesorAsfalto,
                    onAnchoZanjaChange = { anchoZanja = it },
                    onSobrecamaChange = { sobrecama = it },
                    onRellenoPropioChange = { rellenoPropio = it },
                    onRellenoPrestamoChange = { rellenoPrestamo = it },
                    onDesperdicioChange = { desperdicio = it },
                    onConAsfaltoChange = { conAsfalto = it },
                    onEspesorAsfaltoChange = { espesorAsfalto = it },
                    onCalcularClick = {
                        // TODO: Implementar cálculo de materiales
                    }
                )
            }
            
            // Mostrar resultados del UI state
            when (val state = uiState) {
                is TopNivUiState.Error -> {
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(state.mensaje)
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun MaterialesSection(
    anchoZanja: String,
    sobrecama: String,
    rellenoPropio: String,
    rellenoPrestamo: String,
    desperdicio: String,
    conAsfalto: Boolean,
    espesorAsfalto: String,
    onAnchoZanjaChange: (String) -> Unit,
    onSobrecamaChange: (String) -> Unit,
    onRellenoPropioChange: (String) -> Unit,
    onRellenoPrestamoChange: (String) -> Unit,
    onDesperdicioChange: (String) -> Unit,
    onConAsfaltoChange: (Boolean) -> Unit,
    onEspesorAsfaltoChange: (String) -> Unit,
    onCalcularClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Materiales",
                style = MaterialTheme.typography.titleMedium
            )
            
            OutlinedTextField(
                value = anchoZanja,
                onValueChange = onAnchoZanjaChange,
                label = { Text("Ancho de zanja (m)") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = sobrecama,
                onValueChange = onSobrecamaChange,
                label = { Text("Sobrecama (cm)") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = rellenoPropio,
                onValueChange = onRellenoPropioChange,
                label = { Text("Relleno propio (cm)") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = rellenoPrestamo,
                onValueChange = onRellenoPrestamoChange,
                label = { Text("Relleno préstamo (cm)") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = desperdicio,
                onValueChange = onDesperdicioChange,
                label = { Text("Desperdicio (%)") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                modifier = Modifier.fillMaxWidth()
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = conAsfalto,
                    onCheckedChange = onConAsfaltoChange
                )
                Text("Con asfalto")
            }
            
            if (conAsfalto) {
                OutlinedTextField(
                    value = espesorAsfalto,
                    onValueChange = onEspesorAsfaltoChange,
                    label = { Text("Espesor asfalto (cm)") },
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            Button(
                onClick = onCalcularClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular materiales")
            }
        }
    }
}
