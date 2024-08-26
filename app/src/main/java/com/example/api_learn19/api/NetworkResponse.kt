package com.example.api_learn19.api

sealed class NetworkResponse<out T> { //SearchPT sealed classes can only be inherited from inside their body, ie all subclasses inside only
    data class Success<out T>(val data : T):NetworkResponse<T>()  //SearchPT inheritance NetworkResponse<T>   (val data : T) is a primary constructor
    data class Error(val message: String): NetworkResponse<Nothing>()
    object Loading:NetworkResponse<Nothing>()
}