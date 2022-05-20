package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalexam.adapter.MainAdapter
import com.example.finalexam.databinding.ActivityMainBinding
import com.example.finalexam.model.Countries
import com.example.finalexam.view_model.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val mainAdapter = MainAdapter()
    private lateinit var dateWorld: String
    companion object{
        fun changeTheme(status: Boolean): Boolean {
            if (status){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        binding.mainRvCountries.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }
        showLoading(true)


        if(viewModel.getCountries().value == null){
            showLoading(true)
            showData(null)
        }

        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Countries) {
                selectedItem(data)
            }
        })
    }



    private fun showData(query: String?) = runBlocking{
        launch {
            val formatNumber = DecimalFormat("#,###")
            viewModel.setSummary(query)
            delay(600)
            viewModel.getWorld().observe(this@MainActivity, { items ->
                if (items != null) {
                }
            })
            viewModel.getCountries().observe(this@MainActivity, { items ->
                if (items != null) {
                    mainAdapter.updateCountries(items)
                }
                showLoading(false)
            })
        }
    }

    private fun selectedItem(data: Countries) {
        val intent = Intent(this, DetailCountry::class.java)
        val formatNumber = DecimalFormat("#,###")
        intent.putExtra(DetailCountry.EXTRA_SLUG, data.Slug)
        intent.putExtra(DetailCountry.EXTRA_COUNTRY_CODE, data.CountryCode)
        intent.putExtra(DetailCountry.EXTRA_COUNTRY, data.Country)
        intent.putExtra(DetailCountry.EXTRA_CONFIRMED, formatNumber.format(data.TotalConfirmed?.toFloat()))
        intent.putExtra(DetailCountry.EXTRA_NEW_CONFIRMED, formatNumber.format(data.NewConfirmed?.toFloat()))
        intent.putExtra(DetailCountry.EXTRA_RECOVERED, formatNumber.format(data.TotalRecovered?.toFloat()))
        intent.putExtra(DetailCountry.EXTRA_NEW_RECOVERED, formatNumber.format(data.NewRecovered?.toFloat()))
        intent.putExtra(DetailCountry.EXTRA_DEATHS, formatNumber.format(data.TotalDeaths?.toFloat()))
        intent.putExtra(DetailCountry.EXTRA_NEW_DEATHS, formatNumber.format(data.NewDeaths?.toFloat()))
        startActivity(intent)
    }

    private fun showLoading(status: Boolean) {
            if (status) {
                binding.mainProgress.visibility = View.VISIBLE
            } else {
                binding.mainProgress.visibility = View.GONE
            }
    }
}