package com.example.balikitchenclub.screens.transaction

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.TransactionResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTransactionViewModel() : ViewModel() {

    var detailTransaction: MutableStateFlow<TransactionResponseItem?> = MutableStateFlow(null)

    fun getDetailTransaction(id: Int){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.getDetailTransaction(id)
            }

            if (response.isSuccessful){
                var result = response.body()
                Log.d("RESULT", result.toString())
                detailTransaction.value = result
            }
        }
    }

    fun setLunas(context: Context){
        viewModelScope.launch {
            val currentDetailTransaction = detailTransaction.value
            val updatedTransaction = currentDetailTransaction!!.copy(status = "Lunas")
            detailTransaction.value = updatedTransaction

            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.setTransactionLunas(detailTransaction.value!!.id)
            }

            if (response.isSuccessful) {
                if (response.body()!!.error){
                    detailTransaction.value = currentDetailTransaction!!.copy(status = "Belum Lunas")

                    withContext(Dispatchers.Main){
                        Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}