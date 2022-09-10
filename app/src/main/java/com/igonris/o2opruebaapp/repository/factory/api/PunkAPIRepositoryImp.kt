package com.igonris.o2opruebaapp.repository.factory.api

import com.igonris.o2opruebaapp.utils.types.ResultType
import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.utils.Utils
import com.igonris.o2opruebaapp.utils.types.ErrorType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PunkAPIRepositoryImp(private val iPunkAPI: IPunkAPI): IPunkAPIRepository {

    override fun getBeers(name: String, page: Int, perPage: Int): Flow<ResultType<List<Beer>>> {
        return flow {
            coroutineScope {
                emit(
                    Utils.responseToResultType {
                        iPunkAPI.getBeers(name, page, perPage)
                    }
                )
            }
        }
    }

    override fun getBeerInfo(id: String): Flow<ResultType<Beer>> {
        return flow {
            coroutineScope {
                emit(
                    Utils.responseToResultType {
                        iPunkAPI.getBeerInfo(id = id)
                    }.let { resultType ->
                        if (resultType is ResultType.Success && resultType.data.isNotEmpty()) {
                            ResultType.Success(resultType.data[0])
                        } else {
                            ResultType.Error(ErrorType.Other("0 Result"))
                        }
                    }
                )
            }
        }
    }
}