package com.example.balikitchenclub.screens.transaction

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.ApiService
import com.example.balikitchenclub.network.dro.DetailTransactionResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.MenuResponseTransaction
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dro.TransactionResponseItem
import com.example.balikitchenclub.network.dto.CreateTransactionDetailDto
import com.example.balikitchenclub.network.dto.CreateTransactionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(): ViewModel() {

    companion object {
        var confirmMenus: MutableStateFlow<List<MenuResponseTransaction>> = MutableStateFlow(mutableStateListOf())
    }

    private val _transactions = MutableLiveData<List<TransactionResponseItem>>()
    val transactions: LiveData<List<TransactionResponseItem>> = _transactions

    private val _menus = MutableStateFlow<List<MenuResponseTransaction>>(emptyList())
    val menus: StateFlow<List<MenuResponseTransaction>> = _menus

    var totalItem by mutableStateOf(0)
    var totalPrice by mutableStateOf(0)

    fun setConfirmedMenu(){
        viewModelScope.launch {
            var list = mutableStateListOf<MenuResponseTransaction>()
            _menus.value.forEachIndexed { index, menu ->
                if (menu.qty > 0){
                    list.add(menu)
                }
            }

            confirmMenus = MutableStateFlow(list)
            Log.e("CHECK", confirmMenus.value.toString())
        }
    }

    fun getAllTransactions(){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.getTransactions()
            }

            if (response.isSuccessful){
                val result = response.body()
                _transactions.value = result!!
            }
        }
    }

    private fun countTotalItem(){
        var total = 0
        _menus.value.forEachIndexed { index, menu ->
            if (menu.qty > 0){
                total++
            }
        }

        totalItem = total
    }

    private fun countTotalPrice(){
        var total = 0
        _menus.value.forEachIndexed { index, menu ->
            if (menu.qty > 0){
                total += menu.price * menu.qty
            }
        }

        totalPrice = total
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

            countTotalItem()
            countTotalPrice()
        }
    }
}