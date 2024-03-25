package com.example.balikitchenclub.screens.master.sesi

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSesiScreen(
    id: String?,
    viewModel: SesiViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
){
    val context = LocalContext.current
    var loaded = mutableStateOf(false)
    var startTime = rememberTimePickerState(viewModel.detailStartHour, viewModel.detailStartMinute, true)
    var endTime = rememberTimePickerState(viewModel.detailEndHour, viewModel.detailEndMinute, true)

    if (viewModel.detailSesiName != ""){
        startTime = rememberTimePickerState(viewModel.detailStartHour, viewModel.detailStartMinute, true)
        endTime = rememberTimePickerState(viewModel.detailEndHour, viewModel.detailEndMinute, true)
    }

    LaunchedEffect(Unit){
        viewModel.getDetailSesi(id!!)
        loaded.value = true
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Box(Modifier.fillMaxWidth()){
            Text("Detail Sesi", modifier = Modifier.align(Alignment.Center))
        }
        if (viewModel.detailSesiName != ""){
            LazyColumn(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {
                    OutlinedTextField(value = viewModel.detailSesiName, onValueChange = { newValue -> viewModel.detailSesiName = newValue }, label = { Text("Nama") })

                    Spacer(modifier = Modifier.padding(10.dp))
                    Text("Waktu Mulai : ${viewModel.detailStartHour}:${viewModel.detailStartMinute}")
                    TimePicker(state = startTime)

                    Spacer(modifier = Modifier.padding(10.dp))
                    Text("Waktu Berakhir : ${viewModel.detailEndHour}:${viewModel.detailEndMinute}")
                    TimePicker(state = endTime)

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
                        viewModel.updateSesi(onSuccess = {
                            Toast.makeText(context, "Berhasil Update Sesi ${viewModel.detailSesiName}", Toast.LENGTH_SHORT).show()
                        }) }
                    ) {
                        Text("Update Sesi")
                    }
                }
            }
        }
    }
}


