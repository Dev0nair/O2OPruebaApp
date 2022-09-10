package com.igonris.o2opruebaapp.di

import com.igonris.o2opruebaapp.repository.factory.PunkAPIRepositoryFactory
import com.igonris.o2opruebaapp.repository.factory.api.IPunkAPI
import com.igonris.o2opruebaapp.repository.factory.api.PunkAPIRepositoryImp
import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePunkApiRepo(iPunkAPI: IPunkAPI): IPunkAPIRepository {
        return PunkAPIRepositoryFactory.getRepository(iPunkAPI = iPunkAPI)
    }
}