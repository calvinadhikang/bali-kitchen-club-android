package com.example.balikitchenclub.screens.master.menu

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.balikitchenclub.utils.thousandDelimiter

@Composable
fun MenuScreen(
    viewModel: MenuScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController
){
    val menus by viewModel.data.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllMenus()
    }

    Column {
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Menu", modifier = Modifier.weight(1F), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text("Tambah Menu", modifier = Modifier.clickable{ navController.navigate("menu-add") }, color = Color.Blue)
        }
        LazyColumn(){
            items(items = menus, key = { item -> item.id }){menu ->
                Column(
                    modifier = Modifier.clickable { navController.navigate("menu-detail/${menu.id}") }
                ) {
                    Text(menu.name, fontWeight = FontWeight.SemiBold)
                    Text("Stok: ${menu.stock}")
                    Text("Rp ${thousandDelimiter(menu.price)}")
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
        Text("Total Data ${menus.size}")
    }
}
