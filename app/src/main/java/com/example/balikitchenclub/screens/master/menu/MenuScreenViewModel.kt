package com.example.balikitchenclub.screens.master.menu

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dto.CreateMenuDto
import com.example.balikitchenclub.network.dto.UpdateMenuDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuScreenViewModel : ViewModel(){

    var detailName by mutableStateOf("")
    var detailPrice by mutableStateOf(0)
    var detailStock by mutableStateOf(0)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailLoading = MutableLiveData<Boolean>()
    val detailLoading: LiveData<Boolean> = _detailLoading

    private val _menus = MutableLiveData<List<MenuResponseItem>>()
    val menus: LiveData<List<MenuResponseItem>> = _menus

    fun getAllMenus() {
        viewModelScope.launch {
            try {
                _isLoading.value = true  // Set loading state to true
                val api = ApiClient.apiService
                val response = api.getAllMenus()

                if (response.isSuccessful) {
                    val res = response.body()
                    if (res?.data != null) {
                        _menus.value = res.data
                    }
                } else {
                    Log.e("DATA_FAILED", "error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", e.message.toString())
            } finally {
                _isLoading.value = false  // Set loading state to false
            }
        }
    }

    fun createMenu(menuName: String, menuPrice: Int, context: Context){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val api = ApiClient.apiService
                val response = api.createMenu(CreateMenuDto(menuName, menuPrice, 2))

                if (response.isSuccessful){
                    withContext(Dispatchers.Main){
                        Toast.makeText(context, "Berhasil Tambah Menu", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getDetailMenu(id: Int){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val api = ApiClient.apiService
                val response = api.getDetailMenu(id)

                if (response.isSuccessful) {
                    val result = response.body()!!.data
                    detailPrice = result.price
                    detailName = result.name
                    detailStock = result.stock
                }
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateMenu(id: Int, context: Context){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val api = ApiClient.apiService
                val updateDto = UpdateMenuDto(detailName, detailPrice)
                val response = api.updateMenu(id, updateDto)

                if (response.isSuccessful){
                    Toast.makeText(context, "Berhasil Update Menu", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                Log.e("ERROR", ex.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }
}