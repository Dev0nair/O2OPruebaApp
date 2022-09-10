package com.igonris.o2opruebaapp.presentation.beerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igonris.o2opruebaapp.presentation.base.BaseViewModel
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.usecases.GetBeerDetailUseCase
import com.igonris.o2opruebaapp.utils.extensions.launchOnIO
import com.igonris.o2opruebaapp.utils.types.ManagedItem
import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import com.igonris.o2opruebaapp.utils.types.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    myDispatchers: MyDispatchers,
    private val getBeerDetailUseCase: GetBeerDetailUseCase
): BaseViewModel(myDispatchers) {

    private val _data: MutableLiveData<ManagedItem<Beer>> = MutableLiveData()
    val data: LiveData<ManagedItem<Beer>> = _data

    fun loadData(id: String) {
        launchOnIO {
            onLoading(true)
            getBeerDetailUseCase(id = id)
                .flowOn(myDispatchers.io)
                .collect { resultType ->
                    when (resultType) {
                        is ResultType.Success -> {
                            _data.postValue(ManagedItem(resultType.data))
                            onLoading(false)
                        }
                        is ResultType.Error -> {
                            onError(resultType.error)
                        }
                    }
                }
        }
    }

}