package com.example.api_learn19

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_learn19.api.Constant
import com.example.api_learn19.api.NetworkResponse
import com.example.api_learn19.api.RetrofitInstance
import com.example.api_learn19.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {       //Inheritance
    private val weatherApi= RetrofitInstance.weatherApi
    private val _weatherResult= MutableLiveData<NetworkResponse<WeatherModel>>()  //SearchPT Imp
    public val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult
    fun getData(city: String){
       // Log.i("City name:",city)
        _weatherResult.value=NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response=weatherApi.getWeather(Constant.apiKey,city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value=NetworkResponse.Success(it)
                    }
                }
                else{
                        _weatherResult.value=NetworkResponse.Error("Error: Try again")
                }
            }
            catch (e: Exception){
                _weatherResult.value=NetworkResponse.Error("What? Its is an Exceptional Error")
            }
        }


    }
}