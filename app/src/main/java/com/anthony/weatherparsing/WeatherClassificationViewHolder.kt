package com.anthony.weatherparsing

import com.anthony.weatherparsing.databinding.ItemLocationWeatherClassificationBinding

class WeatherClassificationViewHolder(private val binding: ItemLocationWeatherClassificationBinding) : BaseViewHolder<WeatherItem>(binding.root) {
    override fun bind(item: WeatherItem) {
        binding.item = item.classification
    }
}