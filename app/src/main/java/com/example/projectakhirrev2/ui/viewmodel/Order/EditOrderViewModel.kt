package com.example.projectakhirrev2.ui.viewmodel.Order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan
import com.example.projectakhirrev2.ui.halaman.order.ItemEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class EditOrderViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriPelanggan: RepositoriPelanggan
): ViewModel() {
    var pelangganUiState by mutableStateOf(UIStatePelanggan())
        private set

    private val itemId : Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            pelangganUiState = repositoriPelanggan.getPelangganStream(itemId)
                .filterNotNull()
                .first()
                .toUiStatePelanggan(true)
        }
    }
    suspend fun updatePelanggan(){
        if (validasiInput(pelangganUiState.detailPelanggan)){
            repositoriPelanggan.updatePelanggan(pelangganUiState.detailPelanggan.toPelanggan())
        }
        else{
            println("Data tidak valid")
        }
    }
    fun updateUiState (detailPelanggan : DetailPelanggan){
        pelangganUiState =
            UIStatePelanggan(detailPelanggan = detailPelanggan, isEntryValid = validasiInput(detailPelanggan))
    }
    private fun validasiInput(uiState: DetailPelanggan = pelangganUiState.detailPelanggan):Boolean{
        return with(uiState){
            nama.isNotBlank() && angkatan.isNotBlank() && ukuran.isNotBlank() && telpon.isNotBlank()
        }
    }
}