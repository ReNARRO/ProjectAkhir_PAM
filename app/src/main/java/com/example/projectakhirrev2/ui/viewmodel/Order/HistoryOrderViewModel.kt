package com.example.projectakhirrev2.ui.viewmodel.Order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryOrderViewModel (private val repositoriPelanggan: RepositoriPelanggan): ViewModel() {
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val homeUiState: StateFlow<HistoryUiState> = repositoriPelanggan.getAllPelangganStream()
        .filterNotNull()
        .map { HistoryUiState(listSiswa = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HistoryUiState())

    data class HistoryUiState(
        val listSiswa: List<Pelanggan> = listOf()
    )

}