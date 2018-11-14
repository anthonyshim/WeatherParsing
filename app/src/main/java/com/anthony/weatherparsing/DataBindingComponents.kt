package com.anthony.weatherparsing

import android.databinding.BindingAdapter
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

object DataBindingComponents {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, imageUrl: String?) {
        when (imageUrl.isNullOrEmpty()) {
            true -> imageView.setImageDrawable(null)
            else -> {
                Glide.with(imageView.context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("weatherItems")
    fun setWeatherItems(recyclerView: RecyclerView, weatherItems: MutableList<WeatherItem>?) {
        recyclerView.adapter?.let {
            if (it is WeatherAdapter) {
                if (weatherItems == null) it.clearItems() else it.setItems(weatherItems)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlText(textView: TextView, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            textView.text = Html.fromHtml(text)
        }
    }
}