package com.igonris.o2opruebaapp.usecases

import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.utils.types.ResultType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ISearchBeersUseCase {
    suspend operator fun invoke(beerName: String, page: Int = 0, perPage: Int = Int.MAX_VALUE): Flow<ResultType<List<Beer>>>
}

class SearchBeersUseCase @Inject constructor(
    private val iPunkAPIRepository: IPunkAPIRepository
): ISearchBeersUseCase {

    override suspend fun invoke(beerName: String, page: Int, perPage: Int): Flow<ResultType<List<Beer>>> {
        val beerNameToUse = if (beerName.isEmpty()) "_" else beerName.replace(" ", "_")

        return iPunkAPIRepository.getBeers(beerNameToUse, page, perPage)
    }
}