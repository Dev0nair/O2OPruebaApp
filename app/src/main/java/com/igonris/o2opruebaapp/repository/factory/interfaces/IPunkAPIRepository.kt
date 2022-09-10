package com.igonris.o2opruebaapp.repository.factory.interfaces

import com.igonris.o2opruebaapp.utils.types.ResultType
import com.igonris.o2opruebaapp.repository.model.Beer
import kotlinx.coroutines.flow.Flow

interface IPunkAPIRepository {

    fun getBeers(name: String, page: Int, perPage: Int): Flow<ResultType<List<Beer>>>
    fun getBeerInfo(id: String): Flow<ResultType<Beer>>

}