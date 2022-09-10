package com.igonris.o2opruebaapp.presentation.beerdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import coil.load
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.databinding.FragmentBeerDetailBinding
import com.igonris.o2opruebaapp.presentation.base.BaseFragment
import com.igonris.o2opruebaapp.presentation.base.BaseViewModel
import com.igonris.o2opruebaapp.repository.model.Beer
import com.igonris.o2opruebaapp.utils.extensions.prepareForTransition
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerDetailFragment: BaseFragment() {

    override val viewModel: BeerDetailViewModel by viewModels()
    private lateinit var viewBinding: FragmentBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareForTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        setupListener()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Postponemos la animación de transición para cuando carguen los datos
        postponeEnterTransition()
        viewModel.loadData(getIDFromArgument())
    }

    private fun setupListener() {
        viewModel.data.observe(viewLifecycleOwner) { managedItem ->
            managedItem.doIfUnhandled { item ->
                printItem(item)
            }
        }
    }

    private fun printItem(beer: Beer) {
        viewBinding.beerImage.load(beer.image_url)
        viewBinding.beerName.text = getString(R.string.name_grades_composition, beer.name, beer.abv)
        viewBinding.beerDetail.text = beer.description

        startPostponedEnterTransition()
    }

    private fun getIDFromArgument(): String {
        return arguments?.getString("id").orEmpty()
    }
}