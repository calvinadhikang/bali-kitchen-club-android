package com.example.balikitchenclub.main

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.login.LoginActivity
import com.example.balikitchenclub.utils.User
import com.example.balikitchenclub.utils.UserPreferences
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {

    var user = mutableStateOf("")

    fun getUser(context: Context){
        viewModelScope.launch {
            val userPreferences = UserPreferences(context)
            user.value = userPreferences.getUser()!!.name
        }
    }

    fun logout(context: Context){
        viewModelScope.launch {
            UserPreferences(context).clearUser()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}