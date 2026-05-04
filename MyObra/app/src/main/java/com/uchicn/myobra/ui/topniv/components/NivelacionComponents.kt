package com.uchicn.myobra.ui.topniv.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.uchicn.myobra.ui.theme.PrimaryBlue
import com.uchicn.myobra.ui.theme.PrimaryBlueLight
import com.uchicn.myobra.ui.theme.AccentOrange
import com.uchicn.myobra.ui.theme.SecondaryGray

@Composable
fun NivelacionCard(
    cotaBm: String,
    lecturaBm: String,
    bzInicial: String,
    bzFinal: String,
    distancia: String,
    intervalo: String,
    diametro: String,
    cama: String,
    onCotaBmChange: (String) -> Unit,
    onLecturaBmChange: (String) -> Unit,
    onBzInicialChange: (String) -> Unit,
    onBzFinalChange: (String) -> Unit,
    onDistanciaChange: (String) -> Unit,
    onIntervaloChange: (String) -> Unit,
    onDiametroChange: (String) -> Unit,
    onCamaChange: (String) -> Unit,
    onGenerarLecturasClick: () -> Unit,
    onLecturasClick: () -> Unit,
    onCotasClick: () -> Unit,
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
            // Header con icono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryBlue, PrimaryBlueLight)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Column {
                    Text(
                        text = "Nivelación Topográfica",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Ingresa los datos del proyecto",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

            // BM Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = cotaBm,
                    onValueChange = onCotaBmChange,
                    label = { Text("Cota BM") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.VerticalAlignTop, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = lecturaBm,
                    onValueChange = onLecturaBmChange,
                    label = { Text("Lectura BM") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.RemoveRedEye, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // BZ Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = bzInicial,
                    onValueChange = onBzInicialChange,
                    label = { Text("BZ inicial") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.Start, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = bzFinal,
                    onValueChange = onBzFinalChange,
                    label = { Text("BZ final") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.End, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // Distancia/Intervalo Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = distancia,
                    onValueChange = onDistanciaChange,
                    label = { Text("Distancia (m)") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.Straighten, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = intervalo,
                    onValueChange = onIntervaloChange,
                    label = { Text("Intervalo (m)") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.AddCircleOutline, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // Diámetro/Cama Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = diametro,
                    onValueChange = onDiametroChange,
                    label = { Text("Diámetro (mm)") },
                    placeholder = { Text("0") },
                    leadingIcon = {
                        Icon(Icons.Default.Circle, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
                OutlinedTextField(
                    value = cama,
                    onValueChange = onCamaChange,
                    label = { Text("Cama (cm)") },
                    placeholder = { Text("0") },
                    leadingIcon = {
                        Icon(Icons.Default.HorizontalRule, contentDescription = null)
                    },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onLecturasClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(PrimaryBlue, PrimaryBlueLight)
                        )
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryBlue
                    )
                ) {
                    Icon(
                        Icons.Default.List,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Lecturas")
                }
                OutlinedButton(
                    onClick = onCotasClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(PrimaryBlue, PrimaryBlueLight)
                        )
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PrimaryBlue
                    )
                ) {
                    Icon(
                        Icons.Default.Assessment,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                    Text("Cotas")
                }
            }

            val isEnabled = cotaBm.isNotEmpty() && lecturaBm.isNotEmpty() &&
                    bzInicial.isNotEmpty() && bzFinal.isNotEmpty() &&
                    distancia.isNotEmpty() && intervalo.isNotEmpty() &&
                    diametro.isNotEmpty() && cama.isNotEmpty()

            Button(
                onClick = onGenerarLecturasClick,
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnabled) AccentOrange else SecondaryGray,
                    disabledContainerColor = SecondaryGray.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Generar lecturas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
