package com.uchicn.myobra.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos_poligonal")
data class ProyectoPoligonalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val fechaCreacion: Long = System.currentTimeMillis(),
    val fechaModificacion: Long = System.currentTimeMillis(),
    val puntosJson: String, // JSON serializado de los puntos
    val coordenadaXInicial: Double,
    val coordenadaYInicial: Double,
    val errorCierreLinear: Double,
    val errorCierreAngular: Double,
    val precision: String,
    val esCerrada: Boolean,
    val notas: String? = null
)
