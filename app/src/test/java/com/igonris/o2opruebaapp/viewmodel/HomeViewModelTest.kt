package com.igonris.o2opruebaapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.navArgument
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.presentation.home.HomeViewModel
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.usecases.ISearchBeersUseCase
import com.igonris.o2opruebaapp.utils.types.ManagedItem
import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import com.igonris.o2opruebaapp.utils.types.Navigation
import com.igonris.o2opruebaapp.utils.types.ResultType
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val dispatchers = MyDispatchers(
        io = testDispatcher,
        main = testDispatcher
    )

    private val searchBeersUseCase: ISearchBeersUseCase = mock<ISearchBeersUseCase>()
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(
            myDispatchers = dispatchers,
            searchBeersUseCase = searchBeersUseCase,
            autoLoad = false
        )
    }

    @Test
    fun `check we get the first page`() = runTest {
        val perPage = 3 * 6

        val listFirstPage: Flow<ResultType.Success<List<Beer>>> =
            flowOf(ResultType.Success((1..perPage).map { i ->
                Beer("$i", "", "", "", "", 0.0)
            }.toList()))

        whenever(searchBeersUseCase("_", 1, perPage)) doReturn listFirstPage

        homeViewModel.searchBeer("_")

        verify(searchBeersUseCase)("_", 1, perPage)

        // Comprobamos que el valor del LiveData es el de la carga del UseCase
        homeViewModel.data.value?.item shouldBe listFirstPage.first().data
    }

    /**
     * Teniendo en cuenta que cogemos 18 elementos por carga,
     * con la carga inicial + scroll, deberían de hacer 36 (page1 + page2)
     * */
    @Test
    fun `check we get the next page on scroll`() = runTest {
        val perPage = 3 * 6

        val listFirstPage: Flow<ResultType.Success<List<Beer>>> =
            flowOf(ResultType.Success((1..perPage).map { i ->
                Beer("$i", "", "", "", "", 0.0)
            }.toList()))


        // Decimos que cuando se llamen con los parametros page1 y page2, devuelvan el mismo listado como ejemplo
        whenever(searchBeersUseCase("_", 1, perPage)) doReturn listFirstPage
        whenever(searchBeersUseCase("_", 2, perPage)) doReturn listFirstPage

        homeViewModel.searchBeer("_")

        // Verificamos la iteracción de la devolución de la page1
        verify(searchBeersUseCase)("_", 1, perPage)

        homeViewModel.onScroll()

        // Verificamos la iteracción de la devolución de la page2
        verify(searchBeersUseCase)("_", 2, perPage)

        val dataExpected = perPage * 2

        // Comprobamos que el valor del LiveData es el de la carga del UseCase
        homeViewModel.data.value?.item?.size shouldBeEqualTo dataExpected
    }

    @Test
    fun `check we navigate to detail when beer get clicked`() = runTest {
        val expected: Navigation = Navigation(
            dest = R.id.action_homeFragment_to_beerDetailFragment,
            args = listOf("id" to "1")
        )

        homeViewModel.onBeerClick("1", null, null)

        homeViewModel.navigation.value?.item shouldBeEqualTo expected
    }

    /**
     * Nos aseguramos que no hay mas iteracciones
     * */
    @After
    fun tearDown() {
        verifyNoMoreInteractions(searchBeersUseCase)
    }
}