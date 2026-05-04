package com.uchicn.myobra.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProyectoNivelacionDao {
    @Query("SELECT * FROM proyectos_nivelacion ORDER BY fechaModificacion DESC")
    fun getAllProyectos(): Flow<List<ProyectoNivelacionEntity>>
    
    @Query("SELECT * FROM proyectos_nivelacion WHERE id = :id")
    suspend fun getProyectoById(id: Long): ProyectoNivelacionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProyecto(proyecto: ProyectoNivelacionEntity): Long
    
    @Update
    suspend fun updateProyecto(proyecto: ProyectoNivelacionEntity)
    
    @Delete
    suspend fun deleteProyecto(proyecto: ProyectoNivelacionEntity)
    
    @Query("DELETE FROM proyectos_nivelacion WHERE id = :id")
    suspend fun deleteProyectoById(id: Long)
}
