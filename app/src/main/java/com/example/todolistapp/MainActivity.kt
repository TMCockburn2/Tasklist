package com.example.todolistapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.Interface.DeletedItemsScreen
import com.example.todolistapp.Interface.Screens
import com.example.todolistapp.Interface.SettingsScreen
import com.example.todolistapp.Interface.TaskScreen
import com.example.todolistapp.VM.TaskViewModel
import com.example.todolistapp.ui.theme.ToDoListAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //view model is injected so it can be pulled into the compose UIs
    private val viewModel: TaskViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ToDoListAppTheme {
                val navItems = listOf(
                    NavItem(
                        "Task Screen", "taskScreen", Icons.Filled.Home, Icons.Outlined.Home
                    ),
                    NavItem(
                        "Deleted Items", "deleted", Icons.Filled.Delete, Icons.Outlined.Delete
                    ),
                    NavItem(
                        "Settings", "settings", Icons.Filled.Settings, Icons.Outlined.Settings
                    )
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val state = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var itemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    val navController = rememberNavController()
                    ModalNavigationDrawer(
                        drawerState = state,
                        gesturesEnabled = true,
                        drawerContent = {
                            ModalDrawerSheet {
                                navItems.forEachIndexed { index, item ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(text = item.title)
                                        },
                                        selected = index == itemIndex,
                                        onClick = {
                                            itemIndex = index
                                            scope.launch {
                                                state.close()
                                            }
                                            navController.navigate(item.nav) {
                                                popUpTo(0)
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == itemIndex) {
                                                    item.selectedIcon
                                                } else item.unselected,
                                                contentDescription = item.title
                                            )
                                        }
                                    )
                                }
                            }
                        },
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(text = "TaskList")
                                    },
                                    navigationIcon = {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    state.open()
                                                }
                                            }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                contentDescription = "Menu"
                                            )
                                        }

                                    }
                                )
                            }



                        ) {
                                innerPadding ->
                            Box(
                                modifier = Modifier.padding(
                                   innerPadding)){
                                NavHost(
                                    navController = navController,
                                    startDestination = Screens.taskScreen.screen
                                ){
                                    composable(Screens.taskScreen.screen){ TaskScreen(viewModel) }
                                    composable(Screens.deleted.screen){ DeletedItemsScreen(viewModel) }
                                    composable(Screens.settings.screen){ SettingsScreen() }
                                }
                                }
                        }
                        }

                    }
                }
            }
        }
    }



data class NavItem(
    val title: String,
    val nav:String,
    val selectedIcon: ImageVector,
    val unselected: ImageVector
)