package com.anthony.weatherparsing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anthony.weatherparsing.databinding.ItemLocationWeatherBinding
import com.anthony.weatherparsing.databinding.ItemLocationWeatherClassificationBinding

class WeatherAdapter : RecyclerView.Adapter<BaseViewHolder<WeatherItem>>() {
    var weatherItems: MutableList<WeatherItem> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return when (weatherItems[position].type) {
            WeatherType.Classification -> WeatherType.Classification.value
            WeatherType.WeatherData -> WeatherType.WeatherData.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WeatherItem> {
        return when (viewType) {
            WeatherType.Classification.value -> WeatherClassificationViewHolder(
                ItemLocationWeatherClassificationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> WeatherViewHolder(
                ItemLocationWeatherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return weatherItems.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherItem>, position: Int) {
        holder.bind(weatherItems[position])
    }

    fun setItems(items: MutableList<WeatherItem>) {
        weatherItems = items
        notifyDataSetChanged()
    }

    fun clearItems() {
        weatherItems.clear()
        notifyDataSetChanged()
    }
}