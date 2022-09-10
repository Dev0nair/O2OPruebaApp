package com.igonris.o2opruebaapp.presentation.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.presentation.base.BaseViewModel
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.usecases.ISearchBeersUseCase
import com.igonris.o2opruebaapp.usecases.SearchBeersUseCase
import com.igonris.o2opruebaapp.utils.extensions.joinOnMain
import com.igonris.o2opruebaapp.utils.extensions.launchOnIO
import com.igonris.o2opruebaapp.utils.types.ManagedItem
import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import com.igonris.o2opruebaapp.utils.types.Navigation
import com.igonris.o2opruebaapp.utils.types.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    myDispatchers: MyDispatchers,
    private val searchBeersUseCase: ISearchBeersUseCase,
    private val autoLoad: Boolean
) : BaseViewModel(myDispatchers) {

    private val _data: MutableLiveData<ManagedItem<List<Beer>>> =
        MutableLiveData(ManagedItem(emptyList()))
    val data: LiveData<ManagedItem<List<Beer>>> = _data

    private var currentPage: Int = 1
    private var perPage: Int = 3 * 6 // 3 elementos por fila
    private var lastNameSearched: String = ""

    private var canLoad: Boolean = true

    init {
        if(autoLoad) {
            loadData(true)
        }
    }

    fun searchBeer(name: String = "") {
        // Esto evita la recarga que ejecuta los onTextChange del buscador
        if(name.isNotEmpty() && name == lastNameSearched) return

        canLoad = true
        lastNameSearched = name
        currentPage = 1

        loadData(true)
    }

    fun onScroll() {
        currentPage += 1
        loadData(false)
    }

    private fun loadData(reset: Boolean) {
        launchOnIO {
            onLoading(true)

            searchBeersUseCase(lastNameSearched, currentPage, perPage)
                .flowOn(myDispatchers.io)
                .collect { resultType ->
                    when (resultType) {
                        is ResultType.Success -> {
                            processNewData(resultType.data, reset)
                        }
                        is ResultType.Error -> {
                            onError(resultType.error)
                        }
                    }
                }
        }
    }

    private suspend fun processNewData(data: List<Beer>, reset: Boolean) {
        joinOnMain {
            val actualData: List<Beer> = (_data.value ?: ManagedItem(emptyList())).item

            if (reset) {
                _data.postValue(ManagedItem(data))
            } else {
                _data.postValue(ManagedItem(actualData + data))
            }
        }

        onLoading(false)
    }

    fun onBeerClick(idBeer: String, beerImage: View?, beerName: View?) {
        canLoad = false
        navigate(
            if(beerImage != null && beerName != null) {
                Navigation(
                    dest = R.id.action_homeFragment_to_beerDetailFragment,
                    args = listOf("id" to idBeer),
                    sharedElements = listOf(beerImage, beerName)
                )
            } else {
                Navigation(
                    dest = R.id.action_homeFragment_to_beerDetailFragment,
                    args = listOf("id" to idBeer)
                )
            }
        )
    }

}