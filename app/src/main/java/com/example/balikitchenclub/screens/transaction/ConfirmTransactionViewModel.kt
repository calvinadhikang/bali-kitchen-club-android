package com.example.balikitchenclub.screens.transaction

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseTransaction
import com.example.balikitchenclub.network.dto.CreateTransactionDetailDto
import com.example.balikitchenclub.network.dto.CreateTransactionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmTransactionViewModel() : ViewModel() {
    var menus = MutableStateFlow<List<MenuResponseTransaction>>(emptyList())
    var total by mutableStateOf(0)

    fun getConfirmedMenu(){
        menus = TransactionViewModel.confirmMenus
        var tempTotal = 0
        menus.value.forEachIndexed { index, menu ->
            if (menu.qty > 0){
                tempTotal += menu.qty * menu.price
            }
        }
        total = tempTotal
    }


    fun createTransaction(customer: String, status: Boolean, ifSuccess: () -> Unit){
        viewModelScope.launch {
            val api = ApiClient.apiService

            val detailsList = mutableListOf<CreateTransactionDetailDto>()
            var status = if (status) { "Lunas" } else { "Belum Lunas" }
            var total = 0;
            menus.value.forEachIndexed { index, menu ->
                if (menu.qty > 0){
                    val item = CreateTransactionDetailDto(menu = menu.id, qty = menu.qty, price = menu.price, subtotal = menu.qty * menu.price)
                    detailsList.add(item)

                    total += menu.qty * menu.price
                }
            }

            val newTransaction = CreateTransactionDto(
                customer = customer,
                total = total,
                tax = 0,
                tax_value = total,
                grand_total = total,
                details = detailsList,
                status = status,
                employee = 0
            )

            val response = withContext(Dispatchers.IO){
                api.createTransaction(newTransaction)
            }

            if (response.isSuccessful){
                if (response.code() == 201){
                    ifSuccess()
                }
            }
        }
    }
}