package com.anthony.weatherparsing

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.anthony.weatherparsing.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainViewModelInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity, R.layout.activity_main).apply {
            val mainViewModel = MainViewModel(this@MainActivity)
            viewModel = mainViewModel
            swipeRefresh.setOnRefreshListener {
                mainViewModel.onRefresh()
            }
            recycler.adapter = WeatherAdapter()
            recycler.addItemDecoration(PaddingDividerItemDecoration(this@MainActivity, R.drawable.shape_solid_gray_size_1dp))
            recycler.layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    override fun completeReload() {
        swipeRefresh.isRefreshing = false
    }
}
