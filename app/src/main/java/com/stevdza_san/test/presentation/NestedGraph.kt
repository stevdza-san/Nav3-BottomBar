package com.stevdza_san.test.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.stevdza_san.test.R
import com.stevdza_san.test.SearchViewModel
import com.stevdza_san.test.navigation.BottomBarScreen
import com.stevdza_san.test.navigation.BottomBarScreenSaver
import com.stevdza_san.test.navigation.bottomBarItems
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NestedGraph(navigateToSettings: () -> Unit) {
    val backStack = rememberNavBackStack<BottomBarScreen>(BottomBarScreen.Home)

    var currentBottomBarScreen: BottomBarScreen by rememberSaveable(
        stateSaver = BottomBarScreenSaver
    ) { mutableStateOf(BottomBarScreen.Home) }

    val stateHolder = rememberSaveableStateHolder()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nested Graph") },
                actions = {
                    IconButton(onClick = navigateToSettings) {
                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = "Settings icon"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBottomBarScreen == destination,
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = "$destination icon"
                            )
                        },
                        onClick = {
                            if (backStack.lastOrNull() != destination) {
                                if (backStack.lastOrNull() in bottomBarItems) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(destination)
                                currentBottomBarScreen = destination
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
//                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomBarScreen.Home> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Home",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        )
                    }
                }
                entry<BottomBarScreen.Search> {
                    stateHolder.SaveableStateProvider(key = it.title) {
                        val viewModel = viewModel<SearchViewModel>()
                        var number by rememberSaveable { mutableIntStateOf(0) }
                        val number2 = viewModel.number2

                        LaunchedEffect(Unit) {
                            while (true) {
                                delay(2000)
                                number++
                                viewModel.incrementNumber2()
                            }
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Search ($number - $number2)",
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        }
                    }
                }
                entry<BottomBarScreen.Profile> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Profile",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        )
                    }
                }
            }
        )
    }
}