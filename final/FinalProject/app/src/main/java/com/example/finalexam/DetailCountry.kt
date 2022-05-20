package com.example.finalexam

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.LineDataSet
import com.example.finalexam.databinding.ActivityDetailCountryBinding
import com.example.finalexam.view_model.DetailViewModel

class DetailCountry : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCountryBinding
    private lateinit var viewModel: DetailViewModel
    companion object{
        const val EXTRA_SLUG = "slug"
        const val EXTRA_COUNTRY_CODE = "country_code"
        const val EXTRA_COUNTRY = "country"
        const val EXTRA_NEW_CONFIRMED = "new_confirmed"
        const val EXTRA_NEW_DEATHS = "new_deaths"
        const val EXTRA_NEW_RECOVERED = "new_recovered"
        const val EXTRA_CONFIRMED = "confirmed"
        const val EXTRA_DEATHS = "deaths"
        const val EXTRA_RECOVERED = "recovered"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slug = intent.getStringExtra(EXTRA_SLUG)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        showLoading(1,true)
        showLoading(2, true)

        detailShow()
    }


    private fun detailShow() {
        val country = intent.getStringExtra(EXTRA_COUNTRY)
        val countryCode = intent.getStringExtra(EXTRA_COUNTRY_CODE)
        val totalConfirmed = intent.getStringExtra(EXTRA_CONFIRMED)
        val totalRecovered = intent.getStringExtra(EXTRA_RECOVERED)
        val totalDeaths = intent.getStringExtra(EXTRA_DEATHS)
        val newConfirmed = intent.getStringExtra(EXTRA_NEW_CONFIRMED)
        val newRecovered = intent.getStringExtra(EXTRA_NEW_RECOVERED)
        val newDeaths = intent.getStringExtra(EXTRA_NEW_DEATHS)

        with(binding){
            detailTvCountry.text = country
            detailTvTotalConfirmed.text = totalConfirmed
            detailTvTotalRecovered.text = totalRecovered
            detailTvTotalDeaths.text = totalDeaths
            detailTvConfirmed.text = newConfirmed
            detailTvRecovered.text = newRecovered
            detailTvDeaths.text = newDeaths
        }
        showLoading(1,false)
    }

    private fun showLoading(select: Int,status: Boolean) {
            when (select) {
                1 -> if (status) {
                    binding.detailProgress1.visibility = View.VISIBLE
                } else {
                    binding.detailProgress1.visibility = View.GONE
                }
            }
    }
}