package com.example.balikitchenclub.screens.master.sesi

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.Employee
import com.example.balikitchenclub.network.dto.CreateEmployeeDto
import kotlinx.coroutines.launch

class EmployeeViewModel : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _staffs = MutableLiveData<List<Employee>>()
    val staffs: LiveData<List<Employee>> = _staffs

    fun getAllStaffs(){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val api = ApiClient.apiService
                val response = api.getAllStaffs()

                if (response.isSuccessful){
                    val result = response.body()!!.data
                    _staffs.value = result
                }

            } catch (e: Exception){
                Log.e("ERROR", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createUser(name: String, username: String, password: String, role: String, context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val api = ApiClient.apiService
                val createEmployeeDto = CreateEmployeeDto(name, username, password, role)
                val response = api.addStaff(createEmployeeDto)

                if (response.isSuccessful){
                    if (!response.body()!!.error) {
                        Toast.makeText(context, "Berhasil menambah karyawan", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            } finally {
                _isLoading.value = false
            }

        }
    }

}