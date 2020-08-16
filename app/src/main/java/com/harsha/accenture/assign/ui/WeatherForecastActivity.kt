package com.harsha.accenture.assign.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrosid.svgloader.SvgLoader
import com.harsha.accenture.assign.R
import com.harsha.accenture.assign.adapters.WeatherForecastAdapter
import com.harsha.accenture.assign.databinding.ActivityWeatherForecastBinding
import com.harsha.accenture.assign.viewmodels.WeatherForecastViewModel

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var mAdapter: WeatherForecastAdapter
    private lateinit var mViewModel: WeatherForecastViewModel
    private lateinit var mActivityBinding: ActivityWeatherForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_weather_forecast)

        mViewModel = ViewModelProviders.of(this).get(WeatherForecastViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mActivityBinding.recyclerView.setHasFixedSize(true)
        mActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = WeatherForecastAdapter(mViewModel.getCountriesList().value)
        mActivityBinding.recyclerView.adapter = mAdapter
    }

    private fun initializeObservers() {
        mViewModel.fetchCountriesFromServer(this, false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
                mActivityBinding.floatingActionButton.hide()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
                mActivityBinding.floatingActionButton.show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SvgLoader.pluck().close();
    }
}