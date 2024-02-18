package com.example.balikitchenclub.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.balikitchenclub.screens.master.employee.AddEmployeeScreen
import com.example.balikitchenclub.screens.master.menu.AddMenuScreen
import com.example.balikitchenclub.screens.master.menu.DetailMenuScreen
import com.example.balikitchenclub.screens.master.menu.MenuScreen
import com.example.balikitchenclub.screens.master.sesi.AddSesiScreen
import com.example.balikitchenclub.screens.master.sesi.DetailSesiScreen
import com.example.balikitchenclub.screens.master.employee.EmployeeScreen
import com.example.balikitchenclub.screens.master.sesi.SesiScreen
import com.example.balikitchenclub.screens.transaction.AddTransactionScreen
import com.example.balikitchenclub.screens.transaction.ConfirmationTransactionScreen
import com.example.balikitchenclub.screens.transaction.TransactionScreen
import com.example.balikitchenclub.ui.theme.BaliKitchenClubTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaliKitchenClubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    navController = navController,
                    closeDrawer = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { navController.navigate("home") }) {
                            Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                        }
                        IconButton(onClick = { navController.navigate("transaction-add") }) {
                            Icon(imageVector = Icons.Filled.Logout, contentDescription = "")
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate("transaction-add")
                        }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                        }
                    }
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(8.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    IconButton(onClick = {
                        scope.launch {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(Modifier.weight(1F))
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
                
                NavHost(navController, startDestination = "home"){
                    composable("home"){ Text("Home") }
                    composable("menu"){ MenuScreen(navController = navController) }
                    composable("menu-add"){ AddMenuScreen() }
                    composable("menu-detail/{menuId}") { backStackEntry -> DetailMenuScreen(id = backStackEntry.arguments?.getString("menuId")) }
                    composable("sesi"){ SesiScreen(navController = navController) }
                    composable("sesi-add"){ AddSesiScreen() }
                    composable("sesi-detail/{sesiId}"){ backStackEntry -> DetailSesiScreen(id = backStackEntry.arguments?.getString("sesiId")) }
                    composable("transaction") { TransactionScreen(navController = navController) }
                    composable("transaction-add") { AddTransactionScreen(navController = navController) }
                    composable("transaction-confirmation") { ConfirmationTransactionScreen(navController = navController) }
                    composable("employee") { EmployeeScreen(navController = navController) }
                    composable("employee-add") { AddEmployeeScreen() }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, closeDrawer: () -> Unit){
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Drawer")
        Spacer(modifier = Modifier.padding(8.dp))
        Text("Home", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("home")
                closeDrawer()
            })
        Text("Menu", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("menu")
                closeDrawer()
            })
        Text("Sesi", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("sesi")
                closeDrawer()
            })
        Text("Karyawan", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("employee")
                closeDrawer()
            })
        Text("Transaksi", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("transaction")
                closeDrawer()
            })
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BaliKitchenClubTheme {
        MainScreen()
    }
}