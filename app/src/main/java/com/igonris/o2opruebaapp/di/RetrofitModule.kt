package com.igonris.o2opruebaapp.di

import com.igonris.o2opruebaapp.repository.factory.api.IPunkAPI
import com.igonris.o2opruebaapp.repository.factory.PunkAPIRepositoryFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return Retrofit.Builder()
            .baseUrl(PunkAPIRepositoryFactory.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(httpLoggingInterceptor.apply {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    @Provides
    fun getRetrofitPoke(retrofit: Retrofit): IPunkAPI =
        retrofit.create(IPunkAPI::class.java)
}