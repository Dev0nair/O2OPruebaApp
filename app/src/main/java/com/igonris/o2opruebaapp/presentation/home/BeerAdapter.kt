package com.igonris.o2opruebaapp.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.databinding.ItemListBeerBinding
import com.igonris.o2opruebaapp.repository.model.Beer

class BeerAdapter(
    val onBeerClick: (String, View, View) -> Unit
): RecyclerView.Adapter<BeerAdapter.BeerHolder>() {

    private var data: ArrayList<Beer> = ArrayList()

    inner class BeerHolder(val view: ItemListBeerBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerHolder {
        val view: ItemListBeerBinding = ItemListBeerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BeerHolder(view)
    }

    override fun onBindViewHolder(holder: BeerHolder, position: Int) {
        val item = data[position]

        holder.view.beerImage.load(item.image_url)
        holder.view.beerName.text = item.name

        holder.view.root.setOnClickListener {
            onBeerClick(item.id, holder.view.beerImage, holder.view.beerName)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Beer>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}