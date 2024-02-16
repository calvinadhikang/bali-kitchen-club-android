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

    private val _menus = MutableLiveData<List<MenuResponseItem>>()
    val menus: LiveData<List<MenuResponseItem>> = _menus

    var detailName by mutableStateOf("")
    var detailPrice by mutableStateOf(0)
    var detailMenu: MutableStateFlow<MenuResponseItem?> = MutableStateFlow(null)

    var data: MutableStateFlow<List<MenuResponseItem>> = MutableStateFlow(mutableStateListOf())

    fun getAllMenus(){
        viewModelScope.launch {
            try {
                val api = ApiClient.apiService
                val response = withContext(Dispatchers.IO) {
                    api.getAllMenus()
                }

                if (response.isSuccessful) {
                    val result = response.body()

                    val list = mutableStateListOf<MenuResponseItem>()
                    result?.let {
                        list.addAll(it)
                    }
                    data.value = list
                } else {
                    Log.e("DATA_FAILED", "error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", e.message.toString())
            }
        }
    }

    fun createMenu(menuName: String, menuPrice: Int, context: Context){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.createMenu(CreateMenuDto(menuName, menuPrice, "makanan"))
            }

            if (response.isSuccessful){
                Toast.makeText(context, "Berhasil Tambah Menu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getDetailMenu(id: Int){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.getDetailMenu(id)
            }

            if (response.isSuccessful) {
                var result = response.body()
                detailMenu.value = result
                detailPrice = result!!.price
                detailName = result!!.name
            }
        }
    }

    fun updateMenu(id: Int, context: Context){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val updateDto = UpdateMenuDto(detailName, detailPrice)

            val response = withContext(Dispatchers.IO){
                api.updateMenu(id, updateDto)
            }

            if (response.isSuccessful){
                Toast.makeText(context, "Berhasil Update Menu", Toast.LENGTH_SHORT).show()
            }
        }

    }
}