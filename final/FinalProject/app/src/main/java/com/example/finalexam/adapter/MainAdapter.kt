package com.example.finalexam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexam.databinding.ItemListBinding
import com.example.finalexam.model.Countries
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class MainAdapter: RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private val countriesList = ArrayList<Countries>()
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: Countries)
    }

    inner class ListViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(countries: Countries){
            val formatNumber = DecimalFormat("#,###")
            with(binding){

                itemTvCountry.text = countries.Country
                itemTvRecovered.text = formatNumber.format(countries.TotalRecovered?.toFloat())
                itemTvPositive.text = formatNumber.format(countries.TotalConfirmed?.toFloat())
                itemTvDeath.text = formatNumber.format(countries.TotalDeaths?.toFloat())

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(countries) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateCountries(newCountries:ArrayList<Countries>){
        countriesList.clear()
        countriesList.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }
    override fun getItemCount(): Int = countriesList.size
}