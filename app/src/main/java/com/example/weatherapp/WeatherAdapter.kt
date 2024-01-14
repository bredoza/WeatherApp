package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemCityBinding

class WeatherAdapter(private val list: List<WeatherModel>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = list[position]
        holder.bind(city)
    }

    class ViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(weatherModel: WeatherModel) {
            with(binding) {
                tvCity.text = weatherModel.name
                tvTemperature.text = "${weatherModel.main.temp.toInt()}°"
                tvFeelsLike.text = "Feels like: ${weatherModel.main.feels_like.toInt()}°"
                tvTempMin.text = "Min temp: ${weatherModel.main.temp_min.toInt()}°"
                tvTempMax.text = "Max temp: ${weatherModel.main.temp_max.toInt()}°"
                tvCountry.text = "Country: ${weatherModel.sys.country}"
                tvClouds.text = "Clouds: ${weatherModel.clouds.all}%"
                tvWind.text = "Wind speed: ${weatherModel.wind.speed} m/s"
            }
        }
    }
}