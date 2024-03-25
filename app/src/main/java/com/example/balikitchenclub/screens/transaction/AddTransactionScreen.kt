package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.components.MenuTransactionInput
import com.example.balikitchenclub.ui.theme.Brown
import com.example.balikitchenclub.ui.theme.LightGreen
import com.example.balikitchenclub.ui.theme.LightRed
import com.example.balikitchenclub.utils.getContentPadding

@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val menus by viewModel.menus.collectAsState(emptyList())
    val sesiNow by viewModel.sesiNow.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getAllMenuTransaction()
        viewModel.getSesiNow()
    }

    Column(
        Modifier.fillMaxSize()
    ){
        ColumnWraper {
            Text(
                text = "Tambah Transaksi",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Column(
                modifier = Modifier
                    .background(
                        if (sesiNow?.name != null) LightGreen else LightRed,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sesi Sekarang : ${sesiNow?.name ?: "Tidak Ada Sesi"}")
            }
        }
        Spacer(Modifier.padding(vertical = 10.dp))
        ColumnWraper(
            Modifier.weight(1F)
        ) {
            if (viewModel.loadingMenus.value){
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }else{
                LazyColumn {
                    items(items = menus, key = { it.id }){ menu ->
                        MenuTransactionInput(name = menu.name, stock = menu.stock.toString(), price = menu.price.toString(), qty = menu.qty.toString(),
                            addQty = { viewModel.mutateQty(1, menu.id) },
                            subsQty = { viewModel.mutateQty(-1, menu.id) }
                        )
                        Spacer(Modifier.padding(4.dp))
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.setConfirmedMenu()
                navController.navigate("transaction-confirmation")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            enabled = sesiNow?.name != null,
        ) {
            if (sesiNow?.name != null){
                Text("Buat Transaksi", modifier = Modifier.weight(1F))
                Text("${viewModel.totalItem} items - Rp ${viewModel.totalPrice}")
            }else {
                Text("Harus Ada Sesi Aktif", color = Color.Red)
            }
        }
    }
}