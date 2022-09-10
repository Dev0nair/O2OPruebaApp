package com.igonris.o2opruebaapp.usecases

import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.utils.types.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IGetBeerDetailUseCase {
    suspend operator fun invoke(id: String): Flow<ResultType<Beer>>
}

class GetBeerDetailUseCase @Inject constructor(
    val iPunkAPIRepository: IPunkAPIRepository
)  : IGetBeerDetailUseCase {

    override suspend fun invoke(id: String): Flow<ResultType<Beer>> {
        return iPunkAPIRepository.getBeerInfo(id)
    }
}