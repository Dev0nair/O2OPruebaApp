package com.igonris.o2opruebaapp.usecase

import com.igonris.o2opruebaapp.repository.factory.api.PunkAPIRepositoryImp
import com.igonris.o2opruebaapp.repository.factory.interfaces.IPunkAPIRepository
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.usecases.GetBeerDetailUseCase
import com.igonris.o2opruebaapp.usecases.IGetBeerDetailUseCase
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
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetBearDetailUseCaseTest {

    //region Features
    private val iPunkAPIRepository: IPunkAPIRepository = mock<IPunkAPIRepository>()
    private val getBearDetailUseCaseTest: IGetBeerDetailUseCase = GetBeerDetailUseCase(iPunkAPIRepository)
    //endregion

    @Test
    fun `check we get the expected beer`() = runTest {
        val beer = Beer(id = "1", "", "", "", "", 0.0)
        val expected: Flow<ResultType.Success<Beer>> = flowOf(ResultType.Success(beer))

        whenever(iPunkAPIRepository.getBeerInfo("1")) doReturn expected

        val result: List<ResultType<Beer>> = getBearDetailUseCaseTest("1").toList()

        verify(iPunkAPIRepository).getBeerInfo("1")

        result.first() shouldBe expected.first()
    }

    @Test
    fun `check we do not get the expected beer`() = runTest {
        val beer = Beer(id = "1", "", "", "", "", 0.0)
        val beer2 = Beer(id = "2", "", "", "", "", 0.0)

        val expected: Flow<ResultType.Success<Beer>> = flowOf(ResultType.Success(beer))
        val notExpected: Flow<ResultType.Success<Beer>> = flowOf(ResultType.Success(beer2))

        whenever(iPunkAPIRepository.getBeerInfo("1")) doReturn expected
        whenever(iPunkAPIRepository.getBeerInfo("2")) doReturn notExpected

        val result: List<ResultType<Beer>> = getBearDetailUseCaseTest("1").toList()

        verify(iPunkAPIRepository).getBeerInfo("1")

        result.first() shouldNotBe notExpected.first()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(iPunkAPIRepository)
    }

}