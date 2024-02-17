package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfirmationTransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val menus by viewModel.menus.collectAsState(emptyList())
    var name by rememberSaveable { mutableStateOf("") }

    Column {
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Transaksi", modifier = Modifier.weight(1F))
            Text("Tambah Transaksi", modifier = Modifier.clickable{ navController.navigate("transaction-add") }, color = Color.Blue)
        }
        LazyColumn(){
            items(items = menus ){ menu ->
                Text(text = menu.name)
                Text("${menu.price} x ${menu.qty} = Rp ${menu.qty * menu.price}")
                HorizontalDivider()
            }
        }
        Text("Total Data ${menus?.size}")
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Row() {
            Icon(Icons.Filled.Person, "")
            Text("User")
        } })
        Button(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "")
            Text("Buat Transaksi")
        }
    }
}
