package com.example.projectakhirrev2.ui.viewmodel.Order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan
import com.example.projectakhirrev2.ui.halaman.order.DetailpelangganDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailsOrderViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriPelanggan: RepositoriPelanggan
) : ViewModel(){
    private val pelangganId: Int = checkNotNull(savedStateHandle[DetailpelangganDestination.pelangganIdArg])
    val uiState : StateFlow<ItemDetailsUiState> =
        repositoriPelanggan.getPelangganStream(pelangganId).filterNotNull().map{
            ItemDetailsUiState(detailPelanggan = it.toDetailPelanggan())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ItemDetailsUiState()
        )

    suspend fun deleteItem(){
        repositoriPelanggan.deletePelanggan(uiState.value.detailPelanggan.toPelanggan())
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ItemDetailsUiState(
    val outOfStock : Boolean = true,
    val detailPelanggan: DetailPelanggan = DetailPelanggan(),
)