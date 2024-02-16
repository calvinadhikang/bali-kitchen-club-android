package com.example.balikitchenclub.screens.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.balikitchenclub.network.dro.SesiResponseItem
import com.example.balikitchenclub.network.dro.TransactionResponseItem

class TransactionViewModel: ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionResponseItem>>()
    val transactions: LiveData<List<TransactionResponseItem>> = _transactions

    fun getAllTransactions(){

    }
}