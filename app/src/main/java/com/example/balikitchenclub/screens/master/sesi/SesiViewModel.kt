package com.example.balikitchenclub.screens.master.sesi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dto.CreateSesiDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SesiViewModel : ViewModel(){

    private val _sesis = MutableLiveData<List<SesiResponseItem>>()
    val sesis: LiveData<List<SesiResponseItem>> = _sesis

    fun getAllSesi(){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val response = withContext(Dispatchers.IO){
                    api.getAllSesi()
                }

                if (response.isSuccessful){
                    val result = response.body()
                    _sesis.value = result!!
                }

            } catch (e: Exception){

            }
        }
    }

    fun createSesi(name: String, start: String, end: String, context: Context){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val createSesiDto = CreateSesiDto(name, start, end)
                val response = withContext(Dispatchers.IO){
                    api.createSesi(createSesiDto)
                }

                if (response.isSuccessful){
                    Toast.makeText(context, "Berhasil Tambah Sesi ${name}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

            }
        }

    }
}