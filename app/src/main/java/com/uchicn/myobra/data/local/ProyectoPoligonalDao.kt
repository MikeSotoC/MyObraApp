package com.uchicn.myobra.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProyectoPoligonalDao {
    @Query("SELECT * FROM proyectos_poligonal ORDER BY fechaModificacion DESC")
    fun getAllProyectos(): Flow<List<ProyectoPoligonalEntity>>
    
    @Query("SELECT * FROM proyectos_poligonal WHERE id = :id")
    suspend fun getProyectoById(id: Long): ProyectoPoligonalEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProyecto(proyecto: ProyectoPoligonalEntity): Long
    
    @Update
    suspend fun updateProyecto(proyecto: ProyectoPoligonalEntity)
    
    @Delete
    suspend fun deleteProyecto(proyecto: ProyectoPoligonalEntity)
    
    @Query("DELETE FROM proyectos_poligonal WHERE id = :id")
    suspend fun deleteProyectoById(id: Long)
}
