package com.example.api_learn19

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.api_learn19.api.NetworkResponse
import com.example.api_learn19.api.WeatherModel

@Composable
fun WeatherPage(viewModel: WeatherViewModel){
    var city by remember{   //SearchPT
       mutableStateOf("")       //SearchPT
    }
    val keyboardController= LocalSoftwareKeyboardController.current
    val weatherResultt=viewModel.weatherResult.observeAsState() //SearchPT
    // Spacer(modifier = Modifier.height(80.dp)) wont work as it has to be in parent component like coulumn or row
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp), horizontalAlignment=Alignment.CenterHorizontally){
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                modifier=Modifier.weight(1f), //SearchPT
                value = city, onValueChange = {      //SearchPT
                city = it
            },
                label={
                    Text(text="Search for any location....")
                })
            IconButton(onClick = { viewModel.getData(city)
                keyboardController?.hide()
            })    //SearchPT viewmodel is variable instance of WeatherModel
                {
                    Icon(imageVector = Icons.Default.Search, contentDescription ="Search for any location")
                }
        }
        when(val result=weatherResultt.value){
            is NetworkResponse.Error -> {
                Text(text=result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                //Text(text=result.data.toString())
                WeatherDetails(data=result.data)
            }
            null ->{}  //Todo puts it into inf loop
        }
    }
}
@Composable
fun WeatherDetails(data: WeatherModel){
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom){
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location",modifier=Modifier.size(40.dp))
            Text(text=data.location.name, fontSize =36.sp)
            Spacer(modifier=Modifier.width(8.dp))
            Text(text=data.location.country, fontSize =16.sp, color= Color.DarkGray)

        }
        Spacer(modifier=Modifier.height(60.dp))
        Text(text="${data.current.temp_c}°c", fontSize = 76.sp, fontWeight = FontWeight.Bold, textAlign= TextAlign.Center)
        AsyncImage(model = "https:${data.current.condition.icon}".replace("64x64","128x128"), contentDescription ="Condition Image" ,modifier=Modifier.size(180.dp))
        Text(text="${data.current.condition.text}!", fontSize = 44.sp, fontWeight = FontWeight.Bold, textAlign= TextAlign.Center, style= TextStyle(lineHeight = 38.sp) )
        Spacer(modifier = Modifier.height(20.dp))
        Card(modifier = Modifier.fillMaxHeight()){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CardKeyValue(key = data.current.humidity, value = "Humidity")
                CardKeyValue(key = data.current.uv, value = "UV")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CardKeyValue(key = data.current.wind_kph, value = "Wind Speed")
                CardKeyValue(key = data.current.wind_dir, value = "Direction")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                CardKeyValue(key = data.current.precip_mm, value = "Rain(mm)")
                CardKeyValue(key = data.current.dewpoint_c, value = "Dew Point")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                verticalArrangement = Arrangement.Bottom
                    ){
                Spacer(modifier = Modifier.height(30.dp))
                Text(color = Color.DarkGray,text="Developed with \uD83E\uDDE1 by Sarthak")}
            }
        }
    }
}
@Composable
    fun CardKeyValue(key:String,value:String){
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Text(text=key, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text=value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Magenta)
    }
    }
