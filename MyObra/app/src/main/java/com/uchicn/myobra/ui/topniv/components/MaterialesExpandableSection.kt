package com.uchicn.myobra.ui.topniv.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uchicn.myobra.ui.theme.AccentOrange

@Composable
fun MaterialesExpandableSection(
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
    onCalcularClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
