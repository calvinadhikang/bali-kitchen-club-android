package com.example.balikitchenclub.screens.master.sesi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.EmployeeResponseItem
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dto.CreateEmployeeDto
import com.example.balikitchenclub.network.dto.CreateSesiDto
import com.example.balikitchenclub.utils.return24HourTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel : ViewModel(){

    private val _staffs = MutableLiveData<List<EmployeeResponseItem>>()
    val staffs: LiveData<List<EmployeeResponseItem>> = _staffs

    fun getAllStaffs(){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val response = withContext(Dispatchers.IO){
                    api.getAllStaffs()
                }

                if (response.isSuccessful){
                    val result = response.body()
                    _staffs.value = result!!
                }

            } catch (e: Exception){

            }
        }
    }

    fun createUser(name: String, username: String, password: String, role: String) {
        viewModelScope.launch {
            val api = ApiClient.apiService
            val createEmployeeDto = CreateEmployeeDto(name, username, password, role)
            val response = withContext(Dispatchers.IO){
                api.registerUser(createEmployeeDto)
            }
        }
    }

}