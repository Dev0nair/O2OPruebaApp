package com.igonris.o2opruebaapp.di

import com.igonris.o2opruebaapp.usecases.ISearchBeersUseCase
import com.igonris.o2opruebaapp.usecases.SearchBeersUseCase
import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.usecases.GetBeerDetailUseCase
import com.igonris.o2opruebaapp.usecases.IGetBeerDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RepositoryModule::class])
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSearchBeersUseCase(iPunkAPIRepository: IPunkAPIRepository): ISearchBeersUseCase = SearchBeersUseCase(iPunkAPIRepository)

    @Provides
    fun provideGetBeerDetailUseCase(iPunkAPIRepository: IPunkAPIRepository): IGetBeerDetailUseCase = GetBeerDetailUseCase(iPunkAPIRepository)
}