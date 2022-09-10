package com.igonris.o2opruebaapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.databinding.FragmentHomeBinding
import com.igonris.o2opruebaapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment() {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewElements()
        setupListeners()
    }

    private fun setupViewElements() {
        beerAdapter = BeerAdapter { idBeer, beerImage, beerName ->
            viewModel.onBeerClick(idBeer, beerImage, beerName)
        }

        viewBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            adapter = beerAdapter
        }

        viewBinding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->

            // Solo recargamos cuando hemos visto todas las cervezas cargadas. Paginación.
            // Con la comprobación del adaptador, nos aseguramos que no llamamos sin querer
            // Al onScroll cuando aún no hemos cargado ni la primera pantalla
            if(!viewBinding.recyclerView.canScrollVertically(1) && beerAdapter.itemCount > 0) {
                viewModel.onScroll()
            }

        }

        viewBinding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Con esta comprobación evitamos que se re-busque el mismo texto.
                // Esto pasa porque cada vez que se carga la pantalla, este evento se dispara.
                if(beerAdapter.itemCount > 0) {
                    viewModel.searchBeer(query.orEmpty())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(beerAdapter.itemCount > 0) {
                    viewModel.searchBeer(newText.orEmpty())
                }
                return true
            }
        })
    }

    private fun setupListeners() {
        viewModel.data.observe(viewLifecycleOwner) { managedData ->
            beerAdapter.setData(managedData.item)
        }
    }
}