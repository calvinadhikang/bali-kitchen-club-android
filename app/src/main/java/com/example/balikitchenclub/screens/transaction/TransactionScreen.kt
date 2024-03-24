package com.example.balikitchenclub.screens.transaction

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.SelectOptions
import com.example.balikitchenclub.components.TransactionListCard
import com.example.balikitchenclub.utils.thousandDelimiter

@Composable
fun TransactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val transactions by viewModel.transactions.observeAsState(emptyList())
    val listSesiName by viewModel.sesiListName.collectAsState()
    val listSesiId by viewModel.sesiListId.collectAsState()

    var sesiSelected by viewModel.sesiSelected
    var timeSelected by viewModel.timeSelected

    val context = LocalContext.current

//    DisposableEffect(Unit) {
//        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
//            // Execute your side-effect here
//            sesiSelected = 0
//        }
//        navController.addOnDestinationChangedListener(callback)
//        onDispose {
//            navController.removeOnDestinationChangedListener(callback)
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.getAllSesi()
    }

    LaunchedEffect(sesiSelected){
        viewModel.getAllTransactions()
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tampilkan data transaksi:", Modifier.weight(1F))
            OutlinedButton(onClick = {
                if (timeSelected == "today"){
                    timeSelected = "past"
                }else{
                    timeSelected = "today"
                }

                viewModel.getAllTransactions()
            }) {
                Text(if (timeSelected == "today") "Hari Ini" else "Kemarin")
            }
        }

        if (listSesiId.isNotEmpty() && listSesiName.isNotEmpty()){
            SelectOptions(names = listSesiName, values = listSesiId, defaultIndex = 0, labelText = "Pilih sesi:",
                onOptionSelected = {
                    sesiSelected = it
                }
            )
        }

        Row(Modifier.padding(bottom = 16.dp, top = 16.dp)) {
            Text("List Transaksi", modifier = Modifier.weight(1F))
            Text(
                "Tambah Transaksi",
                modifier = Modifier.clickable {
                    navController.navigate("transaction-add")
                },
                color = Color.Blue,
            )
        }
        LazyColumn(){
            items(items = transactions){ transaction ->
                TransactionListCard(customer = transaction.customer, totalPrice = thousandDelimiter(transaction.grand_total), status = transaction.status ) {
                    navController.navigate("transaction-detail/${transaction.id}")
                }
            }
            item {
                Text("Total Data ${transactions?.size}")
                Text("Total Uang Diterima : Rp ${thousandDelimiter(viewModel.totalEarnings.value)}")
            }
        }
    }
}
