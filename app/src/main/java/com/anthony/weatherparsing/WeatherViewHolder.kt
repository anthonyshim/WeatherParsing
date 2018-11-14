package com.anthony.weatherparsing

import com.anthony.weatherparsing.databinding.ItemLocationWeatherBinding

class WeatherViewHolder(private val binding: ItemLocationWeatherBinding) : BaseViewHolder<WeatherItem>(binding.root) {
    override fun bind(item: WeatherItem) {
        binding.item = item.data
    }
}