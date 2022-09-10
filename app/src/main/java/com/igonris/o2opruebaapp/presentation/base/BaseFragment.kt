package com.igonris.o2opruebaapp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.igonris.o2opruebaapp.presentation.main.MainActivity
import com.igonris.o2opruebaapp.utils.extensions.navigate
import com.igonris.o2opruebaapp.utils.extensions.showError

/**
 * Fragment usado para automatizar la navegación y controlar los errores no esperados de las corrutinas y el loading
 * */
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errors.observe(viewLifecycleOwner) { _error ->
            _error.doIfUnhandled { error ->
                showError(error.desc)
            }
        }

        viewModel.navigation.observe(viewLifecycleOwner) { _navigation ->
            _navigation.doIfUnhandled { navigation ->
                navigate(navigation.dest, navigation.args, navigation.sharedElements)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { _navigation ->
            _navigation.doIfUnhandled { loading ->
                (requireActivity() as MainActivity).showLoading(loading)
            }
        }
    }
}