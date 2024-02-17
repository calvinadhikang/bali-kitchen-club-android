package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.MenuTransactionInput

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
                MenuTransactionInput(name = menu.name, stock = menu.stock.toString(), price = menu.price.toString(), qty = menu.qty.toString(),
                    addQty = { viewModel.mutateQty(1, menu.id) },
                    subsQty = { viewModel.mutateQty(-1, menu.id) }
                )
            }
        }
        OutlinedButton(
            onClick = { navController.navigate("transaction-confirmation") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White)
        ) {
            Text("Buat Transaksi", modifier = Modifier.weight(1F))
            Text("${menus.size} items - Rp ${menus.size}")
        }
    }
}