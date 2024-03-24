package com.example.balikitchenclub.components

import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balikitchenclub.network.ApiClient
import com.example.balikitchenclub.network.dro.MenuResponseItem
import com.example.balikitchenclub.network.dro.StockDro
import com.example.balikitchenclub.network.dto.CreateStockDto
import com.example.balikitchenclub.ui.theme.LightGreen
import com.example.balikitchenclub.ui.theme.LightRed
import com.example.balikitchenclub.utils.checkDigitInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun StockMutationList(
    menuId: Int,
    onClick: () -> Unit,
    viewModel: StockMutationComponentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val stockMutationList by viewModel.stockMutationList.collectAsState()
    var newStock by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(Unit){
        viewModel.fetchStockMutation(menuId)
    }

    Text("Tambah Stock:", fontWeight = FontWeight.SemiBold)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(value = "$newStock", onValueChange = { newValue -> newStock = checkDigitInput(newValue) }, label = { Text("Tambah Stock Sebanyak") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1F))
        FilledIconButton(onClick = {
            viewModel.addStock(menuId, newStock, onSuccess = {
                onClick()
                newStock = 0
                Toast.makeText(context, "Berasil Tambah Stock", Toast.LENGTH_SHORT).show()
            })
        }) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
        }
    }
    Spacer(Modifier.padding(top = 12.dp))
    Text("Mutasi Stock:", fontWeight = FontWeight.SemiBold)
    LazyColumn(){
        items(items = stockMutationList, key = {it -> it.id}){stock ->
            Column(
                Modifier
                    .background(if (stock.status == "Masuk") LightGreen else LightRed, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ){
                Text(stock.status, fontWeight = FontWeight.SemiBold)
                Row(
                    Modifier.fillMaxWidth()
                ){
                    Text(stock.type, Modifier.weight(1F))
                    Text(stock.qty.toString())
                }
            }
            Spacer(Modifier.padding(4.dp))
        }
    }

}

class StockMutationComponentViewModel() : ViewModel() {

    var stockMutationList: MutableStateFlow<List<StockDro>> = MutableStateFlow(mutableStateListOf())

    fun fetchStockMutation(menuId: Int){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.getStockMutationByMenuId(menuId)
            }

            if (response.isSuccessful){
                val result = response.body()!!
                stockMutationList.value = result
            }
        }
    }

    fun addStock(menuId: Int, qty: Int, onSuccess:() -> Unit){
        viewModelScope.launch {
            val api = ApiClient.apiService
            val response = withContext(Dispatchers.IO){
                api.addStockByMenuId(createStockDto = CreateStockDto(menu = menuId, qty = qty, employee = 1))
            }

            if (response.isSuccessful){
                onSuccess()
                fetchStockMutation(menuId)
            }
        }
    }
}