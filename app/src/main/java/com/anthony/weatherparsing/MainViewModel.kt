package com.anthony.weatherparsing

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import java.io.IOException

interface MainViewModelInterface {
    fun completeReload()
}

class MainViewModel(private val mainViewModelInterface: MainViewModelInterface) {
    var weatherItems = ObservableField<List<WeatherItem>>()
    var isLoading = ObservableBoolean(false)

    init {
        LoadPage(this@MainViewModel, false).execute()
    }

    fun onRefresh() {
        weatherItems.set(mutableListOf())
        LoadPage(this@MainViewModel, true).execute()
    }

    class LoadPage(private val mainViewModel: MainViewModel, private val isReload: Boolean) : AsyncTask<Void, Void, List<WeatherItem>>() {
        companion object {
            private val TAG = LoadPage::class.java.canonicalName
        }

        override fun doInBackground(vararg params: Void?): List<WeatherItem> {
            // load html
            val url = "https://weather.naver.com/rgn/cityWetrMain.nhn"
            val weatherItemList = mutableListOf<WeatherItem>()
            try {
                val document = Jsoup.connect(url).get()

                // parse : 도시별 날씨
                val contentElement = document.selectFirst("div#content")

                // 도시별 날씨
                val thead = contentElement.select("thead")
                // 도시별 날씨 : Classification
                val classification = Classification(
                        thead.select("th")[0].text(),
                        thead.select("th")[1].text(),
                        thead.select("th")[2].text()
                )
                var weatherItem = WeatherItem(WeatherType.Classification, classification, null)
                weatherItemList.add(weatherItem)

                // 도시별 날씨 : Data
                val tbody = contentElement.selectFirst("tbody")
                for (weatherElement in tbody.select("tr")) {
                    val area = weatherElement.select("th").select("a").html()
                    var morningWeather: Weather
                    var afternoonWeather: Weather
                    // TD
                    weatherElement.select("td").run {
                        // TD : icon
                        first().run {
                            val imageSrc = selectFirst("img").absUrl("src")

                            select("li").run {
                                morningWeather = Weather(imageSrc, first().text(), next().html())
                            }
                        }
                        next().run {
                            val imageSrc = select("img").first().absUrl("src")

                            select("li").run {
                                afternoonWeather = Weather(imageSrc, first().text(), next().html())
                            }
                        }
                    }
                    val weatherData = WeatherData(area, morningWeather, afternoonWeather)
                    weatherItem = WeatherItem(WeatherType.WeatherData, null, weatherData)
                    weatherItemList.add(weatherItem)
                }
                Log.d(TAG, "weatherItemList size : ${weatherItemList.size}")
            } catch (exception: IOException) {
                exception.printStackTrace()
                return listOf()
            }
            return weatherItemList
        }

        override fun onPreExecute() {
            super.onPreExecute()
            if (!isReload) {
                mainViewModel.isLoading.set(true)
            }
        }

        override fun onPostExecute(result: List<WeatherItem>) {
            super.onPostExecute(result)
            if (isReload) {
                mainViewModel.mainViewModelInterface.completeReload()
            } else {
                mainViewModel.isLoading.set(false)
            }
            mainViewModel.weatherItems.set(result)
        }
    }
}