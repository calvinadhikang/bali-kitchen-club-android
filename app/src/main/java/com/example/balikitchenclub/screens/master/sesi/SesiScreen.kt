package com.example.balikitchenclub.screens.master.sesi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable()
fun SesiScreen(navController: NavController, viewModel: SesiViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val sesis by viewModel.sesis.observeAsState(emptyList())

    LaunchedEffect(Unit){
        viewModel.getAllSesi()
    }

    Column {
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Sesi", modifier = Modifier.weight(1F))
            Text("Tambah Sesi", modifier = Modifier.clickable{ navController.navigate("sesi-add") }, color = Color.Blue)
        }
        LazyColumn(){
            items(items = sesis, key = { item -> item.id }){sesi ->
                Column(
                    modifier = Modifier.clickable { navController.navigate("menu-detail/${sesi.id}") }
                ) {
                    Text(sesi.name)
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
        Text("Total Data ${sesis!!.size}")
    }
}
