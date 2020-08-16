package com.harsha.accenture.assign.network

import com.harsha.accenture.assign.models.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("forecast?q=Singapore&APPID=d4f892fdfe4523770f6e6e1e1da1862c")
    fun getWeatherForecastAPI() : Call<WeatherForecastResponse>
}