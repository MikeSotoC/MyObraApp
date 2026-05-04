package com.uchicn.myobra.ui.topniv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.uchicn.myobra.ui.theme.AccentOrange
import com.uchicn.myobra.ui.theme.PrimaryBlue
import com.uchicn.myobra.core.domain.model.topo.ResultadoMateriales
import com.uchicn.myobra.ui.topniv.components.ErrorCard
import com.uchicn.myobra.ui.topniv.components.MaterialesExpandableSection
import com.uchicn.myobra.ui.topniv.components.NivelacionCard
import com.uchicn.myobra.ui.topniv.components.ResultadosCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNivScreen(
    viewModel: TopNivViewModel = hiltViewModel(),
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
                title = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                            color = PrimaryBlue
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Engineering,
                                    contentDescription = null,
                                    tint = androidx.compose.ui.graphics.Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "MyObra",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Nivelación Topográfica",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
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
            
            // Sección expandible de materiales
            MaterialesExpandableSection(
                expanded = calcularMateriales,
                onExpandedChange = { calcularMateriales = it },
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
                    viewModel.calcularMateriales(
                        perfil = emptyList(), // Usa el perfil cacheado
                        ancho = anchoZanja.toDoubleOrNull(),
                        diametroMm = diametro.toDoubleOrNull(),
                        camaCm = cama.toDoubleOrNull(),
                        sobreCm = sobrecama.toDoubleOrNull(),
                        rellPropioCm = rellenoPropio.toDoubleOrNull(),
                        rellPrestCm = rellenoPrestamo.toDoubleOrNull(),
                        desperdicioPct = desperdicio.toDoubleOrNull(),
                        espAsfaltoCm = if (conAsfalto) espesorAsfalto.toDoubleOrNull() else null
                    )
                }
            )
            
            // Mostrar resultados del UI state
            when (val state = uiState) {
                is TopNivUiState.MostrarResultados -> {
                    ResultadosCard(
                        titulo = state.titulo,
                        resultados = state.resultados,
                        pendientePorcentaje = state.pendientePorcentaje
                    )
                }
                is TopNivUiState.MostrarMateriales -> {
                    MaterialesResultadoCard(
                        materiales = state.materiales
                    )
                }
                is TopNivUiState.Error -> {
                    ErrorCard(mensaje = state.mensaje)
                }
                else -> {}
            }
            
            // Espacio adicional al final
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun MaterialesExpandableSection(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header clickable
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(AccentOrange, AccentOrange.copy(alpha = 0.7f))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Materiales",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Calcula materiales de zanja",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = { onExpandedChange(!expanded) }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Contraer" else "Expandir",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    
                    OutlinedTextField(
                        value = anchoZanja,
                        onValueChange = onAnchoZanjaChange,
                        label = { Text("Ancho de zanja (m)") },
                        placeholder = { Text("0.00") },
                        leadingIcon = {
                            Icon(Icons.Default.SquareFoot, contentDescription = null)
                        },
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    OutlinedTextField(
                        value = sobrecama,
                        onValueChange = onSobrecamaChange,
                        label = { Text("Sobrecama (cm)") },
                        placeholder = { Text("0") },
                        leadingIcon = {
                            Icon(Icons.Default.HorizontalRule, contentDescription = null)
                        },
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    OutlinedTextField(
                        value = rellenoPropio,
                        onValueChange = onRellenoPropioChange,
                        label = { Text("Relleno propio (cm)") },
                        placeholder = { Text("0") },
                        leadingIcon = {
                            Icon(Icons.Default.Grading, contentDescription = null)
                        },
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    OutlinedTextField(
                        value = rellenoPrestamo,
                        onValueChange = onRellenoPrestamoChange,
                        label = { Text("Relleno préstamo (cm)") },
                        placeholder = { Text("0") },
                        leadingIcon = {
                            Icon(Icons.Default.AddBox, contentDescription = null)
                        },
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    OutlinedTextField(
                        value = desperdicio,
                        onValueChange = onDesperdicioChange,
                        label = { Text("Desperdicio (%)") },
                        placeholder = { Text("0") },
                        leadingIcon = {
                            Icon(Icons.Default.PieChart, contentDescription = null)
                        },
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentOrange,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Checkbox(
                                checked = conAsfalto,
                                onCheckedChange = onConAsfaltoChange,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AccentOrange,
                                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Icon(
                                Icons.Default.Road,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Incluir capa de asfalto",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    if (conAsfalto) {
                        OutlinedTextField(
                            value = espesorAsfalto,
                            onValueChange = onEspesorAsfaltoChange,
                            label = { Text("Espesor asfalto (cm)") },
                            placeholder = { Text("0") },
                            leadingIcon = {
                                Icon(Icons.Default.Layer, contentDescription = null)
                            },
                            keyboardType = KeyboardType.Decimal,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AccentOrange,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                    
                    Button(
                        onClick = onCalcularClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentOrange
                        )
                    ) {
                        Icon(
                            Icons.Default.Calculate,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = "Calcular materiales",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ResultadosCard(
    titulo: String,
    resultados: List<String>,
    pendientePorcentaje: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SuccessGreen, SuccessGreen.copy(alpha = 0.7f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Column {
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Resultados del cálculo",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            
            // Pendiente destacada
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Pendiente",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = String.format("%.2f%%", pendientePorcentaje),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            
            // Lista de resultados
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                resultados.forEach { resultado ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Circle,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(8.dp)
                        )
                        Text(
                            text = resultado,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorCard(mensaje: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun MaterialesResultadoCard(
    materiales: ResultadoMateriales,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(AccentOrange, AccentOrange.copy(alpha = 0.7f))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Column {
                    Text(
                        text = "Materiales Calculados",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Cantidad de materiales para zanja",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            
            // Grid de resultados
            val items = mutableListOf<Pair<String, String>>()
            
            items.add("Excavación" to "${String.format("%.2f", materiales.excavacion)} m³")
            materiales.cama.takeIf { it > 0 }?.let { 
                items.add("Cama de arena" to "${String.format("%.2f", it)} m³") 
            }
            materiales.sobrecama.takeIf { it > 0 }?.let { 
                items.add("Sobrecama" to "${String.format("%.2f", it)} m³") 
            }
            materiales.rellenoPropio.takeIf { it > 0 }?.let { 
                items.add("Relleno propio" to "${String.format("%.2f", it)} m³") 
            }
            materiales.rellenoPrestamo.takeIf { it > 0 }?.let { 
                items.add("Relleno préstamo" to "${String.format("%.2f", it)} m³") 
            }
            materiales.asfalto.takeIf { it != null && it > 0 }?.let { 
                items.add("Asfalto" to "${String.format("%.2f", it)} m³") 
            }
            
            items.forEach { (titulo, valor) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = titulo,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = valor,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Icon(
                            Icons.Default.Calculate,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
