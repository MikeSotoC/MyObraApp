package com.uchicn.myobra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ProyectoNivelacionEntity::class,
        ProyectoPoligonalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MyObraDatabase : RoomDatabase() {
    abstract fun proyectoNivelacionDao(): ProyectoNivelacionDao
    abstract fun proyectoPoligonalDao(): ProyectoPoligonalDao
    
    companion object {
        @Volatile
        private var INSTANCE: MyObraDatabase? = null
        
        fun getDatabase(context: Context): MyObraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyObraDatabase::class.java,
                    "myobra_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
