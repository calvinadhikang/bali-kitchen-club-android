package com.example.balikitchenclub.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.ui.theme.LightGreen
import com.example.balikitchenclub.ui.theme.LightRed
import com.example.balikitchenclub.ui.theme.TonalBrown
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

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ColumnWraper {
            Text("Detail Transaksi", modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), style = MaterialTheme.typography.titleSmall)
            Text("Customer: ${transaction?.customer}")
            Text("Employee: ${transaction?.employee_detail?.name}")
            Text("Grand Total: Rp ${thousandDelimiter(transaction?.grand_total ?: 0)}")

            Text("Status: ${transaction?.status}",
                modifier = Modifier
                    .background(if (transaction?.status == "Belum Lunas") LightRed else LightGreen, shape = RoundedCornerShape(8.dp))
                    .padding(4.dp),
            )
        }
        ColumnWraper {
            Text("Daftar Pesanan", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
            if (transaction?.details != null){
                LazyColumn {
                    items(items = transaction!!.details, key = {it -> it.id}){ menu ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(TonalBrown, shape = RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ){
                            Column(
                                modifier = Modifier.weight(1F)
                            ) {
                                Text(menu.name, fontWeight = FontWeight.Medium)
                                Text("${menu.qty} x ${menu.price}")
                            }
                            Text("Rp ${thousandDelimiter(menu.subtotal)}")
                        }
                        Spacer(Modifier.padding(4.dp))
                    }
                }
            }
        }
        if (transaction?.status == "Belum Lunas"){
            Button(
                onClick = {
                    viewModel.setLunas(context)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Lunasi Pesanan", Modifier.padding(end = 4.dp))
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
            }
        }
    }
}