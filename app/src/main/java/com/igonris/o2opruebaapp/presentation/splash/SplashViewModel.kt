package com.igonris.o2opruebaapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.presentation.base.BaseViewModel
import com.igonris.o2opruebaapp.utils.extensions.joinOnMain
import com.igonris.o2opruebaapp.utils.extensions.launchOnIO
import com.igonris.o2opruebaapp.utils.types.ManagedItem
import com.igonris.o2opruebaapp.utils.types.MyDispatchers
import com.igonris.o2opruebaapp.utils.types.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    myDispatchers: MyDispatchers
): BaseViewModel(myDispatchers) {

    fun loadSplash() {
        launchOnIO {
            delay(1500)

            joinOnMain {
                navigate(Navigation(R.id.action_splashFragment_to_homeFragment))
            }
        }
    }

}