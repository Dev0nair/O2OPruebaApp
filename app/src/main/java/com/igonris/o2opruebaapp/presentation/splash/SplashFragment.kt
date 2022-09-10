package com.igonris.o2opruebaapp.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.igonris.o2opruebaapp.databinding.FragmentSplashBinding
import com.igonris.o2opruebaapp.presentation.base.BaseFragment
import com.igonris.o2opruebaapp.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment() : BaseFragment() {

    override val viewModel by viewModels<SplashViewModel>()
    private lateinit var viewBinding: FragmentSplashBinding

    override fun onDestroyView() {
        super.onDestroyView()

        (requireActivity() as MainActivity).setFullScreen(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadSplash()
    }
}