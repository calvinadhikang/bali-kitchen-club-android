package com.example.balikitchenclub.screens.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseTransaction
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dro.TransactionResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel: ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionResponseItem>>()
    val transactions: LiveData<List<TransactionResponseItem>> = _transactions

    private val _menus = MutableStateFlow<List<MenuResponseTransaction>>(emptyList())
    val menus: StateFlow<List<MenuResponseTransaction>> = _menus

    fun getAllTransactions(){

    }

    fun getAllMenuTransaction(){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.getAllMenuTransaction()
            }

            if (response.isSuccessful){
                val result = response.body()!!
                _menus.value = result
            }
        }
    }

    fun mutateQty(newQty: Int, menuId: Int){
        viewModelScope.launch {
            _menus.value = _menus.value.map { menu ->
                if (menu.id == menuId) {
                    var finalQty = menu.qty + newQty
                    if (finalQty >= 0 && finalQty <= menu.stock){
                        menu.copy(qty = finalQty)
                    }else{
                        menu
                    }
                } else {
                    menu
                }
            }
        }
    }
}