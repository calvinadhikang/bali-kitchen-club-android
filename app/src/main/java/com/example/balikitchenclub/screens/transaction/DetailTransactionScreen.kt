package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.balikitchenclub.utils.thousandDelimiter

@Composable
fun DetailTransactionScreen(
    id: String?,
    viewModel: DetailTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val context = LocalContext.current
    val transaction by viewModel.detailTransaction.collectAsState()

    LaunchedEffect(Unit){
        viewModel.getDetailTransaction(id!!.toInt())
    }

    Column {
        Text("Detail Transaction", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold)
        Text("Customer : ${transaction?.customer}")
        Text("Employee : ${transaction?.employee_detail?.name}")
        Text("Status   : ${transaction?.status}")
        Spacer(Modifier.padding(vertical = 8.dp))
        Text("Daftar Transaksi", fontWeight = FontWeight.SemiBold)
        if (transaction?.details != null){
            LazyColumn(Modifier.padding(bottom = 16.dp)){
                items(items = transaction!!.details, key = {it -> it.id}){ menu ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier.weight(1F)
                        ) {
                            Text(menu.name)
                            Text("${menu.qty} x ${menu.price}")
                        }
                        Text("Rp ${thousandDelimiter(menu.subtotal)}")
                    }
                    HorizontalDivider()
                }
            }
        }
        if (transaction?.status == "Belum Lunas"){
            OutlinedButton(
                onClick = {
                    viewModel.setLunas(context)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lunasi Pesanan")
            }
        }
    }
}