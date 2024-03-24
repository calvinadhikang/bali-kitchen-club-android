package com.example.balikitchenclub.screens.master.menu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.components.StockMutationList
import com.example.balikitchenclub.utils.checkDigitInput

@Composable
fun DetailMenuScreen(
    viewModel: MenuScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    id: String?,
){
    val context = LocalContext.current

    LaunchedEffect(Unit){
        if (!id.isNullOrEmpty()){
            viewModel.getDetailMenu(id.toInt())
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        ColumnWraper {
            Text("Detail Menu", modifier = Modifier.padding(bottom = 16.dp), style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(value = viewModel.detailName, onValueChange = { newValue -> viewModel.detailName = newValue }, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = viewModel.detailPrice.toString(), onValueChange = { newValue -> viewModel.detailPrice = checkDigitInput(newValue) }, label = { Text("Price") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            Row(){
                Text("Jumlah Stock:", fontWeight = FontWeight.Medium)
                Text(viewModel.detailStock.toString())
            }
            Button(onClick = { viewModel.updateMenu(id!!.toInt(), context) }, modifier = Modifier.fillMaxWidth()) {
                Text("Update")
            }
        }
        if (!id.isNullOrEmpty()){
            ColumnWraper {
                StockMutationList(menuId = id.toInt(), onClick = { viewModel.getDetailMenu(id.toInt()) })
            }
        }
    }
}
