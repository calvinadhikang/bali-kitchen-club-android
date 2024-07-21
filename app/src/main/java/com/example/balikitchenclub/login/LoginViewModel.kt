package com.example.balikitchenclub.login

import android.content.Context
import android.content.Intent
import android.util.Log
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
        try {
            val userPreferences = UserPreferences(context)
            val user = userPreferences.getUser()
            if (user != null){
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        } catch (ex: Exception) {
            Log.e("Err", ex.message!!)
        }
    }

    fun login(context: Context) {
        viewModelScope.launch {
            try {
                isLoading.value = true

                val api = ApiClient.apiService
                val response = withContext(Dispatchers.IO){
                    val authDto = AuthDto(username.value, password.value)
                    api.login(authDto)
                }

                if (response.isSuccessful){
                    val result = response.body()!!

                    if (result.error){
                        message.value = result.message
                        return@launch
                    }

                    val user = result.data
                    val userPref = UserPreferences(context)
                    if (user != null) {
                        userPref.saveUser(user.name, user.username, user.password, user.id, user.role)
                    }

                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }else{
                    message.value = "Login Gagal !"
                }
            } catch (ex: Exception) {
                Log.e("Err", ex.message!!)
            } finally {
                isLoading.value = false
            }
        }
    }
}