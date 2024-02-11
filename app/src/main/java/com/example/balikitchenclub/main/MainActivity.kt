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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.balikitchenclub.screens.master.menu.AddMenuScreen
import com.example.balikitchenclub.screens.master.menu.DetailMenuScreen
import com.example.balikitchenclub.screens.master.menu.MenuScreen
import com.example.balikitchenclub.screens.master.sesi.AddSesiScreen
import com.example.balikitchenclub.screens.master.sesi.SesiScreen
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
                DrawerContent(navController) {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
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
                    composable("menu-add"){ AddMenuScreen(navController) }
                    composable("menu-detail/{menuId}") { backStackEntry -> DetailMenuScreen(navController = navController, id = backStackEntry.arguments?.getString("menuId")) }
                    composable("sesi"){ SesiScreen(navController = navController) }
                    composable("sesi-add"){ AddSesiScreen(navController = navController) }
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
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BaliKitchenClubTheme {
        MainScreen()
    }
}