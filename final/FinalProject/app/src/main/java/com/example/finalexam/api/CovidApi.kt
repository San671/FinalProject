package com.example.finalexam.api

import com.example.finalexam.model.AllCountries
import com.example.finalexam.model.InfoCountry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {
    @GET("summary")
    fun getAllCountries(): Call<AllCountries>

    @GET("dayone/country/{country}")
    fun getInfoCountry(@Path("country") country:String?): Call<ArrayList<InfoCountry>>
}