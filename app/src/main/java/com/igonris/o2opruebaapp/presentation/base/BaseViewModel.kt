package com.igonris.o2opruebaapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igonris.o2opruebaapp.utils.types.ErrorType
import com.igonris.o2opruebaapp.utils.types.ManagedItem
import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import com.igonris.o2opruebaapp.utils.types.Navigation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext

/**
 * ViewModel usado para automatizar la navegación y controlar los errores no esperados de las corrutinas
 * También facilita la parte del loading, para no repetir código en todas las pantallas.
 * Sería de más utilidad para almacenar los errores no esperados de las aplicaciones y subirlos a una base de datos
 * */
abstract class BaseViewModel(val myDispatchers: MyDispatchers) : ViewModel() {

    private val _errors = MutableLiveData<ManagedItem<ErrorType>>()
    val errors: LiveData<ManagedItem<ErrorType>> = _errors

    private val _loading = MutableLiveData<ManagedItem<Boolean>>()
    val loading: LiveData<ManagedItem<Boolean>> = _loading

    private val _navigation = MutableLiveData<ManagedItem<Navigation>>()
    val navigation: LiveData<ManagedItem<Navigation>> = _navigation

    val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            _errors.postValue(ManagedItem(ErrorType.Other(throwable.message ?: "")))
        }
    }

    suspend fun onLoading(loading: Boolean) {
        withContext(myDispatchers.main + coroutineExceptionHandler) {
            _loading.postValue(ManagedItem(loading))
        }
    }

    suspend fun onError(error: ErrorType) {
        onLoading(false)

        withContext(myDispatchers.main) {
            _errors.postValue(ManagedItem(error))
        }
    }

    fun navigate(navigation: Navigation) {
        _navigation.postValue(ManagedItem(navigation))
    }

}

