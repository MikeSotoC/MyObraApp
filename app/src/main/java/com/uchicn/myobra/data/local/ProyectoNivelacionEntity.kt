package com.uchicn.myobra.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos_nivelacion")
data class ProyectoNivelacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaModificacion: Long = System.currentTimeMillis(),
    val puntosJson: String, // JSON serializado de los puntos
    val bmInicial: Double,
    val distanciaTotal: Double,
    val errorCierre: Double,
    val errorAceptable: Boolean,
    val notas: String? = null
)
