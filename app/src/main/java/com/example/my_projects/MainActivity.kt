package com.example.my_projects

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.my_projects.coffee.CoffeeMain
import com.example.my_projects.coffee.Comments
import com.example.my_projects.ui.theme.My_projectsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_projectsTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = { NavigationBarSample(navController) }) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "Main",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("Main") { MainProject() }
                        composable(Destination.PHOTOS.route,) { Photo() }
                        composable(Destination.COFFEE.route) {
                            Scaffold(
                                topBar = { TopBar() }
                            ) { innerPadding ->
                                CoffeeMain(navController, modifier = Modifier.padding(innerPadding))
                            }
                        }

                        composable("Comments/{nombre}") { backStackEntry ->
                            Scaffold(
                                topBar = { TopBar() }
                            ) { innerPadding ->
                                val nombre = backStackEntry.arguments?.getString("nombre")?:""
                                Comments(nombre = nombre, modifier = Modifier.padding(innerPadding))
                            }
                        }
                    }

                    //MainProject(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavigationBarSample(navController: NavController) {

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector =destination.icon,
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(destination.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    //Log.d("Navigation", "Navigation to ${destination.route}")
                    navController.navigate(destination.route)
                }
            )
        }
    }
}


enum class Destination(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val contentDescription: String
) {
    PHOTOS(
        route = "Photo",
        icon = Icons.Filled.AccountBox,
        label = "MyPhotos",
        contentDescription = "Go to MyPhotos"
    ),
    COFFEE(
        route = "Coffee",
        icon = Icons.Filled.Favorite,
        label = "CoffeeShops",
        contentDescription = "Go to CoffeeShops"
    ),
    SOL(
        route = "ElSol",
        icon = Icons.Filled.Face,
        label = "ElSol",
        contentDescription = "Go to ElSol"
    );
    /*HOME(
        route = "Main",
        icon = Icons.Filled.Home,
        label = "Home",
        contentDescription = "Go to Home"
    )*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    var expanded by remember { mutableStateOf(false) }
    var option by remember { mutableStateOf("") }

    //val options = listOf<String>("Compartir", "Album")

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFf99aaa),
            titleContentColor = Color.White,
        ),
        title = {
            Text("CoffeeShops")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More options",
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color(0xFFfbe3e3))
            ) {
                DropdownMenuItem(
                    onClick = {
                        option = "Compartir"
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Filled.Share, contentDescription = null) },
                    text = { Text("Compartir") }
                )
                DropdownMenuItem(
                    onClick = {
                        option = "Album"
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    text = { Text("Album") }
                )
            }
        }
    )
}

@Composable
fun MainProject(modifier: Modifier = Modifier) {
    Box(modifier
        .fillMaxSize()
        .background(Color(0xFFfcfcda))
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(
                text = "MyProjects",
                fontSize = 52.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My_projectsTheme {
        MainProject()
    }
}