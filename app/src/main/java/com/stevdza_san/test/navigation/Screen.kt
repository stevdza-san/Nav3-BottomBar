package com.stevdza_san.test.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen: NavKey {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object NestedGraph : Screen()

    @Serializable
    data object Settings : Screen()
}