package com.igonris.o2opruebaapp.utils.extensions

import androidx.lifecycle.viewModelScope
import com.igonris.o2opruebaapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun BaseViewModel.launchOnIO(action: suspend () -> Unit) {
    viewModelScope.launch(myDispatchers.io + coroutineExceptionHandler) {
        action()
    }
}

suspend fun BaseViewModel.joinOnMain(action: suspend () -> Unit) {
    withContext(myDispatchers.main) {
        action()
    }
}