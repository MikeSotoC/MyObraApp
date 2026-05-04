package com.uchicn.myobra.ui.topniv.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Nivelación",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // BM Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = cotaBm,
                    onValueChange = onCotaBmChange,
                    label = { Text("Cota BM") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lecturaBm,
                    onValueChange = onLecturaBmChange,
                    label = { Text("Lectura BM") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }

            // BZ Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bzInicial,
                    onValueChange = onBzInicialChange,
                    label = { Text("BZ inicial") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = bzFinal,
                    onValueChange = onBzFinalChange,
                    label = { Text("BZ final") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }

            // Distancia/Intervalo Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = distancia,
                    onValueChange = onDistanciaChange,
                    label = { Text("Distancia total (m)") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = intervalo,
                    onValueChange = onIntervaloChange,
                    label = { Text("Intervalo (m)") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }

            // Diámetro/Cama Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = diametro,
                    onValueChange = onDiametroChange,
                    label = { Text("Diámetro tubería (mm)") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = cama,
                    onValueChange = onCamaChange,
                    label = { Text("Cama (cm)") },
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }

            // Botones de nivelación
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onLecturasClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lecturas")
                }
                OutlinedButton(
                    onClick = onCotasClick,
                    modifier = Modifier.weight(1f)
                ) {
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generar lecturas")
            }
        }
    }
}
