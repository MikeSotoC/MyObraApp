package com.uchicn.myobra.di

import com.uchicn.myobra.core.domain.topografia.CalcularMaterialesZanja
import com.uchicn.myobra.core.domain.topografia.CalcularTopografia
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
