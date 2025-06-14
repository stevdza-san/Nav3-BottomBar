package com.stevdza_san.test.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.stevdza_san.test.navigation.Screen

@Composable
fun RootGraph() {
    val backStack = rememberNavBackStack<Screen>(Screen.Auth)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screen.Auth> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { backStack.add(Screen.NestedGraph) }) {
                        Text(text = "Sign in")
                    }
                }
            }
            entry<Screen.Settings> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { backStack.removeLastOrNull() }) {
                        Text(text = "Navigate Back")
                    }
                }
            }
            entry<Screen.NestedGraph> {
                NestedGraph(
                    navigateToSettings = {
                        backStack.add(Screen.Settings)
                    }
                )
            }
        }
    )
}