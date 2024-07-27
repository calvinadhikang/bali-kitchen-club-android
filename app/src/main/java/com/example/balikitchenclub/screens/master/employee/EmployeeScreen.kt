package com.example.balikitchenclub.screens.master.employee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.balikitchenclub.components.ColumnWraper
import com.example.balikitchenclub.screens.master.sesi.EmployeeViewModel
import com.example.balikitchenclub.ui.theme.Brown

@Composable()
fun EmployeeScreen(
    navController: NavController,
    viewModel: EmployeeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val staffs by viewModel.staffs.observeAsState(emptyList())
    val loading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit){
        viewModel.getAllStaffs()
    }
    Column(
        Modifier.fillMaxSize()
    ){
        Row(Modifier.padding(bottom = 16.dp)) {
            Text("List Karyawan", modifier = Modifier.weight(1F), style = MaterialTheme.typography.titleSmall)
            Text("Tambah Karyawan", modifier = Modifier.clickable{ navController.navigate("employee-add") }, color = Brown)
        }

        ColumnWraper {
            if (loading){
                Column(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }

            LazyColumn(){
                items(items = staffs){ staff ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ){
                        Text(staff.name, Modifier.weight(1F))
                        Text(staff.role)
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
