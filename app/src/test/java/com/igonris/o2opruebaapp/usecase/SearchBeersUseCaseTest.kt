package com.igonris.o2opruebaapp.usecase

import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.usecases.ISearchBeersUseCase
import com.igonris.o2opruebaapp.usecases.SearchBeersUseCase
import com.igonris.o2opruebaapp.utils.types.ErrorType
import com.igonris.o2opruebaapp.utils.types.ResultType
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.After
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchBeersUseCaseTest {

    //region Features
    private val iPunkAPIRepository: IPunkAPIRepository = mock<IPunkAPIRepository>()
    private val searchBeersUseCase: ISearchBeersUseCase = SearchBeersUseCase(iPunkAPIRepository)
    //endregion

    @Test
    fun `check we get a success result from use case`() = runTest {
        val expected: Flow<ResultType.Success<List<Beer>>> = flowOf(ResultType.Success(emptyList()))

        whenever(iPunkAPIRepository.getBeers("_", 1, 3 * 4)) doReturn expected

        val result: List<ResultType<List<Beer>>> = searchBeersUseCase("_", 1, 3 * 4).toList()

        verify(iPunkAPIRepository).getBeers("_", 1, 3 * 4)

        result.first() shouldBe expected.first()
    }

    @Test
    fun `check we get an error result from use case`() = runTest {
        val expected: Flow<ResultType.Error<List<Beer>>> = flowOf(ResultType.Error(ErrorType.APIError("")))

        whenever(iPunkAPIRepository.getBeers("_", 1, 3 * 4)) doReturn expected

        val result: List<ResultType<List<Beer>>> = searchBeersUseCase("_", 1, 3 * 4).toList()

        verify(iPunkAPIRepository).getBeers("_", 1, 3 * 4)

        result.first() shouldBe expected.first()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(iPunkAPIRepository)
    }

}