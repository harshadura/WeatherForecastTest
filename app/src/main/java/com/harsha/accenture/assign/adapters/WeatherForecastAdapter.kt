package com.harsha.accenture.assign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.harsha.accenture.assign.R
import com.harsha.accenture.assign.databinding.WeatherForecastListItemBinding
import com.harsha.accenture.assign.models.WeatherForecastData
import com.harsha.accenture.assign.models.WeatherForecastResponse

class WeatherForecastAdapter(var mList: WeatherForecastResponse?) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    fun setData(list: WeatherForecastResponse) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: WeatherForecastListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.weather_forecast_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList?.list?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.weatherForecastData = WeatherForecastData(mList?.list?.get(position)?.dtTxt?:"", mList?.list?.get(position)?.weather?.get(0)?.description?:"", "")
    }

    class ViewHolder(var itemBinding: WeatherForecastListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}