package com.anthony.weatherparsing

enum class WeatherType(val value: Int) {
    Classification(0),
    WeatherData(1)
}

data class WeatherItem(
    var type: WeatherType,
    var classification: Classification?,
    var data: WeatherData?
)

data class Classification(
    var area: String,
    var morning: String,
    var afternoon: String
)

data class WeatherData(
    var area: String,
    var morningWeather: Weather,
    var afternoonWeather: Weather
)

data class Weather(
    var icon: String,
    var description: String,
    var detail: String
)