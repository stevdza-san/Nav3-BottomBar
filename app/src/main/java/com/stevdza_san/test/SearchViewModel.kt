package com.stevdza_san.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel() : ViewModel() {
    var number2 by mutableIntStateOf(0)
        private set

    fun incrementNumber2() {
        number2++
    }

    init {
        println("INITIALIZING SEARCH VIEW MODEL...")
    }

    override fun onCleared() {
        super.onCleared()
        println("CLEARING SEARCH VIEW MODEL...")
    }
}