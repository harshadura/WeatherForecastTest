package com.harsha.accenture.assign.repositories

import androidx.lifecycle.MutableLiveData
import com.harsha.accenture.assign.interfaces.NetworkResponseCallback
import com.harsha.accenture.assign.models.WeatherForecastResponse
import com.harsha.accenture.assign.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherForecastRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mForecastMTL = MutableLiveData<WeatherForecastResponse>()

    companion object {
        private var mInstance: WeatherForecastRepository? = null
        fun getInstance(): WeatherForecastRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = WeatherForecastRepository()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mWeatherForecastCall: Call<WeatherForecastResponse>

    fun getCountries(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<WeatherForecastResponse> {
        mCallback = callback
        if (mForecastMTL.value?.cod?.isNotEmpty() == true && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mForecastMTL
        }
        mWeatherForecastCall = RestClient.getInstance().getApiService().getWeatherForecastAPI()
        mWeatherForecastCall.enqueue(object : Callback<WeatherForecastResponse> {

            override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                mForecastMTL.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                mForecastMTL.value = null
                mCallback.onNetworkFailure(t)
            }

        })
        return mForecastMTL
    }
}