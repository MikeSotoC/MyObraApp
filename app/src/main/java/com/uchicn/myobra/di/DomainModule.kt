package com.uchicn.myobra.di

import android.content.Context
import com.uchicn.myobra.core.domain.topografia.CalcularMaterialesZanja
import com.uchicn.myobra.core.domain.topografia.CalcularTopografia
import com.uchicn.myobra.core.domain.topografia.CalcularPoligonal
import com.uchicn.myobra.core.domain.topografia.CalcularRadiacion
import com.uchicn.myobra.data.local.MyObraDatabase
import com.uchicn.myobra.data.local.ProyectoNivelacionDao
import com.uchicn.myobra.data.local.ProyectoPoligonalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCalcularTopografia(): CalcularTopografia {
        return CalcularTopografia()
    }

    @Provides
    @Singleton
    fun provideCalcularMaterialesZanja(): CalcularMaterialesZanja {
        return CalcularMaterialesZanja()
    }

    @Provides
    @Singleton
    fun provideCalcularPoligonal(): CalcularPoligonal {
        return CalcularPoligonal()
    }

    @Provides
    @Singleton
    fun provideCalcularRadiacion(): CalcularRadiacion {
        return CalcularRadiacion()
    }
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyObraDatabase {
        return MyObraDatabase.getDatabase(context)
    }
    
    @Provides
    @Singleton
    fun provideProyectoNivelacionDao(database: MyObraDatabase): ProyectoNivelacionDao {
        return database.proyectoNivelacionDao()
    }
    
    @Provides
    @Singleton
    fun provideProyectoPoligonalDao(database: MyObraDatabase): ProyectoPoligonalDao {
        return database.proyectoPoligonalDao()
    }
}
