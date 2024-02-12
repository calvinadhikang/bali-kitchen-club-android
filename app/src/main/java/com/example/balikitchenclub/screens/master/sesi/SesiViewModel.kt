package com.example.balikitchenclub.screens.master.sesi

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dto.CreateSesiDto
import com.example.balikitchenclub.utils.return24HourTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SesiViewModel : ViewModel(){

    private val _sesis = MutableLiveData<List<SesiResponseItem>>()
    val sesis: LiveData<List<SesiResponseItem>> = _sesis

    private val _sesiNow = MutableLiveData<SesiResponseItem>()
    val sesiNow: LiveData<SesiResponseItem> = _sesiNow

    var detailSesiName by mutableStateOf("")
    var detailStartHour by mutableStateOf(0)
    var detailStartMinute by mutableStateOf(0)
    var detailEndHour by mutableStateOf(0)
    var detailEndMinute by mutableStateOf(0)

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

    fun getSesiNow(){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val response = withContext(Dispatchers.IO){
                    api.getSesiNow()
                }

                if (response.isSuccessful){
                    val result = response.body()
                    _sesiNow.value = result!!
                }

            } catch (e: Exception){

            }
        }
    }

    fun createSesi(name: String, startHour: String, startMinute: String, endHour: String, endMinute: String, context: Context){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val createSesiDto = CreateSesiDto(name, return24HourTime(startHour, startMinute), return24HourTime(endHour, endMinute))
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

    fun getDetailSesi(id: String){

    }
}