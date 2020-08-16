package com.harsha.accenture.assign.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harsha.accenture.assign.interfaces.NetworkResponseCallback
import com.harsha.accenture.assign.models.WeatherForecastResponse
import com.harsha.accenture.assign.repositories.WeatherForecastRepository
import com.harsha.accenture.assign.utils.NetworkHelper

class WeatherForecastViewModel : ViewModel() {
    private var mWeatherForecast = MutableLiveData<WeatherForecastResponse>()
    var mShowProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowApiError: MutableLiveData<Boolean> = MutableLiveData()
    private var mRepository = WeatherForecastRepository.getInstance()

    fun getCountriesList() = mWeatherForecast

    fun fetchCountriesFromServer(context: Context, forceFetch : Boolean): MutableLiveData<WeatherForecastResponse> {
        if (NetworkHelper.isOnline(context)) {
            mShowProgressBar.value = true
            mWeatherForecast = mRepository.getCountries(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = true
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mWeatherForecast
    }

    fun onRefreshClicked(view : View){
        fetchCountriesFromServer(view.context, true)
    }
}