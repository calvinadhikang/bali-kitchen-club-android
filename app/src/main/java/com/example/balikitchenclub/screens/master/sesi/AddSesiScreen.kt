package com.example.balikitchenclub.screens.master.sesi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSesiScreen(
    viewModel: SesiViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var startTime = rememberTimePickerState(11, 30, true)
    var endTime = rememberTimePickerState(11, 30, true)

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Box(Modifier.fillMaxWidth()){
            Text("Tambah Menu", modifier = Modifier.align(Alignment.Center))
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                OutlinedTextField(value = name, onValueChange = { newValue -> name = newValue }, label = { Text("Nama") })
                Spacer(modifier = Modifier.padding(10.dp))
                Text("Waktu Mulai : ${startTime.hour}:${startTime.minute}")
                TimePicker(state = startTime)

                Spacer(modifier = Modifier.padding(10.dp))
                Text("Waktu Berakhir : ${endTime.hour}:${endTime.minute}")
                TimePicker(state = endTime)

                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = { viewModel.createSesi(name, startTime.hour.toString(), startTime.minute.toString(), endTime.hour.toString(), endTime.minute.toString(), context) }) {
                    Text("Tambah")
                }
            }
        }
    }
}


