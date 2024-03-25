package com.example.balikitchenclub.screens.master.sesi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.ui.theme.Brown
import com.example.balikitchenclub.ui.theme.LightGreen
import com.example.balikitchenclub.ui.theme.TonalBrown

@Composable()
fun SesiScreen(navController: NavController, viewModel: SesiViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    val sesis by viewModel.sesis.observeAsState(emptyList())
    val sesiNow by viewModel.sesiNow.observeAsState()

    LaunchedEffect(Unit){
        viewModel.getAllSesi()
        viewModel.getSesiNow()
    }

    ColumnWraper {
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Sesi", modifier = Modifier.weight(1F), style = MaterialTheme.typography.titleSmall)
            Text("Tambah Sesi", modifier = Modifier.clickable{ navController.navigate("sesi-add") }, color = Brown)
        }
        LazyColumn(){
            items(items = sesis, key = { item -> item.id }){sesi ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (sesiNow?.name == sesi.name) TonalBrown else Color.Transparent, shape = RoundedCornerShape(8.dp))
                        .clickable { navController.navigate("sesi-detail/${sesi.id}") }
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        Text(sesi.name, fontWeight = FontWeight.SemiBold)
                        Text("${sesi.start} - ${sesi.end} WIB")
                    }
                    if (sesiNow?.name == sesi.name){
                        Text("Aktif", modifier = Modifier.padding(horizontal = 8.dp))
                    }
                }
            }
        }
    }
}
