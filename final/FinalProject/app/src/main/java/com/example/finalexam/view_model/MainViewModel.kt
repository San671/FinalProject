package com.example.finalexam.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalexam.api.ApiService
import com.example.finalexam.model.AllCountries
import com.example.finalexam.model.Countries
import com.example.finalexam.model.World
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel: ViewModel() {
    private val apiService = ApiService
    private val countryList = MutableLiveData<ArrayList<Countries>>()
    private val worldList = MutableLiveData<World>()
    private var countryFilterList = MutableLiveData<ArrayList<Countries>>()
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")
    init {
        countryFilterList = countryList
    }

    fun setSummary(query: String?){
        apiService.getSummary().enqueue(object : Callback<AllCountries>{
            override fun onResponse(call: Call<AllCountries>, response: Response<AllCountries>) {
                if (response.isSuccessful) {
                    val responseCountries: ArrayList<Countries>? = response.body()?.Countries
                    val responseWorld = response.body()?.Global

                    if (query != null) {
                        // jika sedang mode pencarian
                        val resultList = ArrayList<Countries>()
                        if (responseCountries != null) {
                            for (row in responseCountries) {
                                if (row.Country!!.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                                    resultList.add(row)
                                }
                            }
                            countryList.postValue(resultList)
                        }
                    } else {
                        countryList.postValue(responseCountries)
                    }


                    val date: Date? = inputFormat.parse(responseWorld?.Date.toString())
                    val formattedDate: String = outputFormat.format(date!!)
                    val resultWorld = World(responseWorld?.TotalConfirmed, responseWorld?.TotalRecovered, responseWorld?.TotalDeaths, formattedDate)

                    worldList.postValue(resultWorld)
                }else{
                    Log.e("ON_SUMMARY_RESPONSE", response.message())
                }
            }

            override fun onFailure(call: Call<AllCountries>, t: Throwable) {
                Log.e("ON_SUMMARY_FAILURE", t.message.toString())
            }
        })
    }

    fun getCountries(): LiveData<ArrayList<Countries>>{
        return countryList
    }
    fun getWorld(): LiveData<World>{
        return worldList
    }
}