package com.uchicn.myobra.ui.radiacion

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
import com.uchicn.myobra.core.domain.model.topo.PuntoRadiacion
import com.uchicn.myobra.core.domain.model.topo.ResultadoRadiacion
import com.uchicn.myobra.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadiacionScreen(
    onNavigateBack: () -> Unit,
    viewModel: RadiacionViewModel = hiltViewModel()
) {
    var estacionN by remember { mutableStateOf("0") }
    var estacionE by remember { mutableStateOf("0") }
    var estacionCota by remember { mutableStateOf("0") }
    var ai by remember { mutableStateOf("1.50") }
    
    var puntos by remember { mutableStateOf(listOf<PuntoRadiacion>()) }
    var showDialog by remember { mutableStateOf(false) }
    var puntoId by remember { mutableStateOf("") }
    var anguloH by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("") }
    var anguloV by remember { mutableStateOf("90") }
    var ap by remember { mutableStateOf("1.50") }
    var descripcion by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Radiación Básica", fontWeight = FontWeight.Bold)
                        Text("Estación Total", style = MaterialTheme.typography.labelMedium)
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
            // Configuración de estación
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Datos de Estación", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = ColorPrimary)
                        
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = estacionN,
                                onValueChange = { estacionN = it },
                                label = { Text("Norte (m)") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = estacionE,
                                onValueChange = { estacionE = it },
                                label = { Text("Este (m)") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                        
                        OutlinedTextField(
                            value = estacionCota,
                            onValueChange = { estacionCota = it },
                            label = { Text("Cota Estación (m)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        
                        OutlinedTextField(
                            value = ai,
                            onValueChange = { ai = it },
                            label = { Text("Altura Instrumento (m)") },
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
                    Text("Puntos Radiados (${puntos.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
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
                            Text("No hay puntos radiados", color = ColorGray, style = MaterialTheme.typography.bodyMedium)
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
                                Text(punto.puntoId, fontWeight = FontWeight.Bold, color = ColorPrimary)
                                Text("Ang: ${punto.anguloHorizontal}° | Dist: ${punto.distanciaInclinada}m", style = MaterialTheme.typography.bodySmall)
                                if (punto.descripcion.isNotEmpty()) {
                                    Text(punto.descripcion, style = MaterialTheme.typography.bodySmall, color = ColorGray)
                                }
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
                            val alturaInst = ai.toDoubleOrNull() ?: 1.50
                            val puntosValidos = puntos.map { p ->
                                p.copy(
                                    puntoId = p.puntoId.ifEmpty { "P${puntos.indexOf(p) + 1}" },
                                    alturaInstrumento = alturaInst
                                )
                            }
                            viewModel.calcularRadiacion(
                                estacionN = estacionN.toDoubleOrNull() ?: 0.0,
                                estacionE = estacionE.toDoubleOrNull() ?: 0.0,
                                estacionCota = estacionCota.toDoubleOrNull() ?: 0.0,
                                puntosMedidos = puntosValidos
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
                        Text("CALCULAR RADIACIÓN", fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            // Resultados
            when (val state = uiState) {
                is RadiacionUiState.MostrarResultado -> {
                    item { ResultadoRadiacionCard(resultado = state.resultado) }
                }
                is RadiacionUiState.Error -> {
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
            title = { Text("Agregar Punto Radiado", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = puntoId,
                            onValueChange = { puntoId = it },
                            label = { Text("ID Punto") },
                            placeholder = { Text("Ej: P1, A1") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            label = { Text("Descripción") },
                            placeholder = { Text("Opcional") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
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
                    OutlinedTextField(
                        value = ap,
                        onValueChange = { ap = it },
                        label = { Text("Alt. Prisma (m)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (anguloH.toDoubleOrNull() != null && distancia.toDoubleOrNull() != null) {
                            puntos = puntos + PuntoRadiacion(
                                puntoId = puntoId.ifEmpty { "P${puntos.size + 1}" },
                                anguloHorizontal = anguloH.toDouble(),
                                distanciaInclinada = distancia.toDouble(),
                                anguloVertical = anguloV.toDoubleOrNull() ?: 90.0,
                                alturaInstrumento = ai.toDoubleOrNull() ?: 1.50,
                                alturaPrisma = ap.toDoubleOrNull() ?: 1.50,
                                descripcion = descripcion
                            )
                            puntoId = ""
                            descripcion = ""
                            anguloH = ""
                            distancia = ""
                            anguloV = "90"
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
fun ResultadoRadiacionCard(resultado: ResultadoRadiacion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ColorPrimary.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Resultados de Radiación", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = ColorPrimary)
            
            // Información de estación
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ColorSecondary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estación N", style = MaterialTheme.typography.labelMedium, color = ColorGray)
                        Text("${String.format("%.3f", resultado.estacionN)} m", fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Estación E", style = MaterialTheme.typography.labelMedium, color = ColorGray)
                        Text("${String.format("%.3f", resultado.estacionE)} m", fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Cota", style = MaterialTheme.typography.labelMedium, color = ColorGray)
                        Text("${String.format("%.3f", resultado.estacionCota)} m", fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            // Lista de puntos calculados
            Text("Puntos Calculados (${resultado.cantidadPuntos})", fontWeight = FontWeight.SemiBold, color = ColorPrimaryDark)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(resultado.puntosCalculados) { punto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ColorWhite),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(punto.puntoId, fontWeight = FontWeight.Bold, color = ColorPrimary, modifier = Modifier.width(60.dp))
                                if (punto.descripcion.isNotEmpty()) {
                                    Text(punto.descripcion, style = MaterialTheme.typography.bodySmall, color = ColorGray, modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Norte", style = MaterialTheme.typography.labelSmall, color = ColorGray)
                                    Text("${String.format("%.3f", punto.coordenadaN)} m", fontWeight = FontWeight.Medium)
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Este", style = MaterialTheme.typography.labelSmall, color = ColorGray)
                                    Text("${String.format("%.3f", punto.coordenadaE)} m", fontWeight = FontWeight.Medium)
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Cota", style = MaterialTheme.typography.labelSmall, color = ColorGray)
                                    Text("${String.format("%.3f", punto.cota)} m", fontWeight = FontWeight.Medium, color = ColorSuccess)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
