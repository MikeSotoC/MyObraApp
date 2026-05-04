package com.uchicn.myobra.ui.poligonal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.uchicn.myobra.core.domain.model.topo.PuntoPoligonal
import com.uchicn.myobra.core.domain.model.topo.ResultadoPoligonal
import com.uchicn.myobra.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoligonalScreen(
    onNavigateBack: () -> Unit,
    viewModel: PoligonalViewModel = hiltViewModel()
) {
    var puntos by remember { mutableStateOf(listOf<PuntoPoligonal>()) }
    var azimutInicial by remember { mutableStateOf("") }
    var coordN by remember { mutableStateOf("0") }
    var coordE by remember { mutableStateOf("0") }
    var cotaInicio by remember { mutableStateOf("0") }
    
    // Diálogo para agregar punto
    var showDialog by remember { mutableStateOf(false) }
    var estacion by remember { mutableStateOf("") }
    var anguloH by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var anguloV by remember { mutableStateOf("90") }
    var ai by remember { mutableStateOf("1.50") }
    var ap by remember { mutableStateOf("1.50") }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Poligonal Rápida", fontWeight = FontWeight.Bold)
                        Text("Método Brújula (Bowditch)", style = MaterialTheme.typography.labelMedium)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorPrimary,
                    titleContentColor = ColorWhite,
                    navigationIconContentColor = ColorWhite
                )
            )
        },
        containerColor = ColorBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Configuración inicial
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Configuración Inicial", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = ColorPrimary)
                        
                        OutlinedTextField(
                            value = azimutInicial,
                            onValueChange = { azimutInicial = it },
                            label = { Text("Azimut Inicial (°)") },
                            placeholder = { Text("Opcional") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ColorPrimary,
                                unfocusedBorderColor = ColorGrayLight
                            )
                        )
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = coordN,
                                onValueChange = { coordN = it },
                                label = { Text("Norte (m)") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = coordE,
                                onValueChange = { coordE = it },
                                label = { Text("Este (m)") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                        
                        OutlinedTextField(
                            value = cotaInicio,
                            onValueChange = { cotaInicio = it },
                            label = { Text("Cota Inicial (m)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }
            
            // Lista de puntos
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Puntos Medidos (${puntos.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ColorSecondary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Agregar Punto")
                    }
                }
            }
            
            if (puntos.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(ColorSurface.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = ColorGray, modifier = Modifier.size(40.dp))
                            Spacer(Modifier.height(8.dp))
                            Text("No hay puntos agregados", color = ColorGray, style = MaterialTheme.typography.bodyMedium)
                            Text("Presiona \"Agregar Punto\" para comenzar", color = ColorGray, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                items(puntos) { punto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = ColorSurface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(punto.estacion, fontWeight = FontWeight.Bold, color = ColorPrimary)
                                Text("Ang: ${punto.anguloHorizontal}° | Dist: ${punto.distanciaInclinada}m", style = MaterialTheme.typography.bodySmall)
                                Text("AV: ${punto.anguloVertical}° | AI/AP: ${punto.alturaInstrumento}/${punto.alturaPrisma}m", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { puntos = puntos.filter { it != punto } }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = ColorError)
                            }
                        }
                    }
                }
            }
            
            // Botón calcular
            if (puntos.isNotEmpty()) {
                item {
                    Button(
                        onClick = {
                            val puntosValidos = puntos.map { p ->
                                p.copy(
                                    estacion = p.estacion.ifEmpty { "P${puntos.indexOf(p) + 1}" }
                                )
                            }
                            viewModel.calcularPoligonalCerrada(
                                puntosMedidos = puntosValidos,
                                azimutInicial = azimutInicial.toDoubleOrNull(),
                                coordenadaInicioN = coordN.toDoubleOrNull() ?: 0.0,
                                coordenadaInicioE = coordE.toDoubleOrNull() ?: 0.0,
                                cotaInicio = cotaInicio.toDoubleOrNull() ?: 0.0
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("CALCULAR POLIGONAL", fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            // Resultados
            when (val state = uiState) {
                is PoligonalUiState.MostrarResultado -> {
                    item { ResultadoPoligonalCard(resultado = state.resultado) }
                }
                is PoligonalUiState.Error -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = ColorError.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Error, contentDescription = null, tint = ColorError)
                                Spacer(Modifier.width(12.dp))
                                Text(state.mensaje, color = ColorError, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
    
    // Diálogo para agregar punto
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Agregar Punto de Poligonal", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = estacion,
                        onValueChange = { estacion = it },
                        label = { Text("Estación") },
                        placeholder = { Text("Ej: E1, P1") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = anguloH,
                            onValueChange = { anguloH = it },
                            label = { Text("Ángulo H (°)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = distancia,
                            onValueChange = { distancia = it },
                            label = { Text("Distancia (m)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                    OutlinedTextField(
                        value = anguloV,
                        onValueChange = { anguloV = it },
                        label = { Text("Ángulo V (°)") },
                        placeholder = { Text("90 = horizontal") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = ai,
                            onValueChange = { ai = it },
                            label = { Text("Alt. Inst. (m)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = ap,
                            onValueChange = { ap = it },
                            label = { Text("Alt. Prisma (m)") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (anguloH.toDoubleOrNull() != null && distancia.toDoubleOrNull() != null) {
                            puntos = puntos + PuntoPoligonal(
                                estacion = estacion.ifEmpty { "P${puntos.size + 1}" },
                                anguloHorizontal = anguloH.toDouble(),
                                distanciaInclinada = distancia.toDouble(),
                                anguloVertical = anguloV.toDoubleOrNull() ?: 90.0,
                                alturaInstrumento = ai.toDoubleOrNull() ?: 1.50,
                                alturaPrisma = ap.toDoubleOrNull() ?: 1.50
                            )
                            estacion = ""
                            anguloH = ""
                            distancia = ""
                            anguloV = "90"
                            ai = "1.50"
                            ap = "1.50"
                            showDialog = false
                        }
                    },
                    enabled = anguloH.toDoubleOrNull() != null && distancia.toDoubleOrNull() != null,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("AGREGAR")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("CANCELAR")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun ResultadoPoligonalCard(resultado: ResultadoPoligonal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ColorPrimary.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Resultados de Poligonal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = ColorPrimary)
            
            // Errores y precisión
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = ColorWhite),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error Angular", style = MaterialTheme.typography.labelMedium, color = ColorGray)
                        Text("${String.format("%.2f", resultado.errorAngular)}\"", fontWeight = FontWeight.Bold, color = ColorPrimary)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = ColorWhite),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error Lineal", style = MaterialTheme.typography.labelMedium, color = ColorGray)
                        Text("${String.format("%.4f", resultado.errorLineal)} m", fontWeight = FontWeight.Bold, color = ColorPrimary)
                    }
                }
            }
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ColorSecondary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Precisión:", fontWeight = FontWeight.SemiBold)
                    Text(resultado.precision, fontWeight = FontWeight.Bold, color = if (resultado.precision.contains("Perfecta") || resultado.precision.substringAfter("1:").toIntOrNull() ?: 0 > 2000) ColorSuccess else ColorWarning)
                }
            }
            
            // Tabla de coordenadas
            Text("Coordenadas Compensadas", fontWeight = FontWeight.SemiBold, color = ColorPrimaryDark)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(resultado.puntosCompensados) { punto ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(punto.estacion, fontWeight = FontWeight.Medium, modifier = Modifier.width(60.dp))
                        Text("N: ${String.format("%.3f", punto.coordenadaN)}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                        Text("E: ${String.format("%.3f", punto.coordenadaE)}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                        Text("Z: ${String.format("%.3f", punto.cota)}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
