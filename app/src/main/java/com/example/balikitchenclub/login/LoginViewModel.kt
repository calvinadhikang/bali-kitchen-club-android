package com.example.balikitchenclub.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.main.MainActivity
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dto.AuthDto
import com.example.balikitchenclub.utils.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {

    var isLoading = mutableStateOf(false)
    var username = mutableStateOf("")
    var password = mutableStateOf("")
    var message = mutableStateOf("")

    fun checkUser(context: Context){
        viewModelScope.launch {
            val userPreferences = UserPreferences(context)
            val user = userPreferences.getUser()
            if (user != null){
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }
    }

    fun login(context: Context){
        viewModelScope.launch {
            isLoading.value = true
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                val authDto = AuthDto(username.value, password.value)
                api.login(authDto)
            }

            if (response.isSuccessful){
                val data = response.body()!!
                val userPref = UserPreferences(context)
                userPref.saveUser(data.name, data.username, data.password, data.id, data.role)

                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }else{
                message.value = "Login Gagal !"
            }
            isLoading.value = false
        }
    }
}