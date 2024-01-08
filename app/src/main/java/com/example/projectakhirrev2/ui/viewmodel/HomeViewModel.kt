package com.example.projectakhirrev2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.repositori.product.RepositoriProduct
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repositoriProduct: RepositoriProduct): ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val homeUiState: StateFlow<HomeUiState> = repositoriProduct.getAllProductStream()
        .filterNotNull()
        .map { HomeUiState(listSiswa = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState())

    data class HomeUiState(
        val listSiswa: List<Product> = listOf()
    )

}