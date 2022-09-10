package com.igonris.o2opruebaapp.repository.factory.api

import com.igonris.o2opruebaapp.repository.model.Beer
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface IPunkAPI {

    @GET("beers")
    suspend fun getBeers(
        @Query("beer_name") beer_name: String,
        @Query("page") page: Int,
        @Query("per_page") offset: Int
    ): Response<List<Beer>>

    @GET("beers/{id}")
    suspend fun getBeerInfo(
        @Path("id") id: String
    ): Response<List<Beer>>
}