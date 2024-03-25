package com.example.balikitchenclub.screens.transaction

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.ui.theme.TonalBrown
import com.example.balikitchenclub.utils.thousandDelimiter

@Composable
fun ConfirmationTransactionScreen(
    navController: NavController,
    viewModel: ConfirmTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val context = LocalContext.current
    val menus by viewModel.menus.collectAsState()
    var name by rememberSaveable { mutableStateOf("") }
    var paid by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.getConfirmedMenu()
    }

    Column(
    ) {
        Text("Konfirmasi Pesanan", modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleSmall)
        ColumnWraper(
            modifier = Modifier.weight(1F)
        ){
            Text(text = "Data Pesanan", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(4.dp))
            LazyColumn(
                modifier = Modifier
                    .weight(1F)
            ){
                items(items = menus){ menu ->
                    Column(
                        Modifier
                            .background(TonalBrown, RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .padding(4.dp)
                    ){
                        Text(text = menu.name, fontWeight = FontWeight.SemiBold)
                        Text("${menu.price} x ${menu.qty} = Rp ${menu.qty * menu.price}")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row{
                Text("Total Harga", modifier = Modifier.weight(1f))
                Text("Rp ${thousandDelimiter(viewModel.total)}", textAlign = TextAlign.Right)
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        ColumnWraper{
            if (name == ""){
                Text(text = "isi nama customer !", color = Color.Red)
            }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Customer")
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Filled.Person, "")
                },
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier.weight(1f)) {
                    Text("Status Pembayaran", fontWeight = FontWeight.SemiBold)
                    Text(
                        text = if (paid) "Lunas" else "Belum Lunas",
                        color = if (paid) Color.Green else Color.Red
                    )
                }
                Switch(
                    checked = paid,
                    onCheckedChange = { paid = it },
                    thumbContent = {
                        if (paid) Icon(imageVector = Icons.Filled.Check, contentDescription = "") else Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    },
                )
            }
        }
        Button(
            onClick = {
                viewModel.createTransaction(
                    context = context,
                    customer = name,
                    status = paid,
                    ifSuccess = {
                        Toast.makeText(context,"Berhasil Buat Transaksi", Toast.LENGTH_SHORT).show()
                        navController.navigate("transaction")
                    }
                )
            },
            modifier = Modifier
                .padding(top = 8.dp, end = 4.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = name != ""
        ) {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
            Text("Buat Transaksi", modifier = Modifier.padding(8.dp))
        }
    }
}
