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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.utils.checkDigitInput

@Composable
fun AddMenuScreen(
    viewModel: MenuScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableIntStateOf(0) }
    val isLoading by viewModel.isLoading.observeAsState(false)

    ColumnWraper {
        Text("Tambah Menu", modifier = Modifier.padding(bottom = 16.dp), style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(value = name, onValueChange = { newValue -> name = newValue }, label = { Text("Nama") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = price.toString(), onValueChange = { newValue -> price = checkDigitInput(newValue) }, label = { Text("Harga") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Button(
                onClick = {
                    viewModel.createMenu(name, price, context)
                },
                enabled = name != "" && price > 0 && !isLoading,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Tambah")
            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}