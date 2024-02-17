package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.balikitchenclub.components.MenuTransaction

@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val menus by viewModel.menus.collectAsState(emptyList())

    LaunchedEffect(Unit){
        viewModel.getAllMenuTransaction()
    }

    Column(
        Modifier.fillMaxSize()
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tambah Transaksi")
        }
        LazyColumn(
            Modifier.weight(1F)
        ){
            items(items = menus, key = { it.id }){ menu ->
                MenuTransaction(name = menu.name, stock = menu.stock.toString(), price = menu.price.toString(), qty = menu.qty.toString(),
                    addQty = { viewModel.mutateQty(1, menu.id) },
                    subsQty = { viewModel.mutateQty(-1, menu.id) }
                )
            }
        }
        Button(onClick = { navController.navigate("transaction-confirmation") }) {
            Text("Buat Transaksi")
        }
    }
}