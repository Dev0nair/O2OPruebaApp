package com.igonris.o2opruebaapp.di

import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun getDispatchers(): MyDispatchers = MyDispatchers()

    @Singleton
    @Provides
    fun provideAutoLoad(): Boolean = true
}