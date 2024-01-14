package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = mutableListOf<WeatherModel>()
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = WeatherAdapter(list)
        binding.rvWeather.adapter = adapter

        initClickers()
        currentDate()
    }

    private fun initClickers() {
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivityForResult(intent, CREATE_ACTIVITY)
        }

        binding.btnMenu.setOnClickListener {
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun currentDate() {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.ENGLISH)
        val formattedDate = formatter.format(date)

        binding.tvCalendar.text = formattedDate
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_ACTIVITY && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra("city")?.let { city ->
                loadData(city)
            }
        }
    }

    private fun loadData(city: String) {
        RetrofitService().api.getWeather(city).enqueue(object : Callback<WeatherModel> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                response.body()?.let {
                    list.add(it)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val CREATE_ACTIVITY = 1
    }
}