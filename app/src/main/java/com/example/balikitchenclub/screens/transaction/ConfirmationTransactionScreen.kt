package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfirmationTransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val transactions by viewModel.transactions.observeAsState(emptyList())

    LaunchedEffect(Unit) {

    }

    Column {
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Transaksi", modifier = Modifier.weight(1F))
            Text("Tambah Transaksi", modifier = Modifier.clickable{ navController.navigate("transaction-add") }, color = Color.Blue)
        }
        LazyColumn(){
            items(items = transactions){ transaction ->
                Text(text = transaction.customer)
                Text(text = transaction.grand_total.toString())
                HorizontalDivider()
            }
        }
        Text("Total Data ${transactions?.size}")
    }
}
