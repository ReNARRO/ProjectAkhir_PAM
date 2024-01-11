package com.example.projectakhirrev2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.repositori.product.DetailUIState
import com.example.projectakhirrev2.repositori.product.ProductRepo
import com.example.projectakhirrev2.repositori.product.toDetailProduct
import com.example.projectakhirrev2.ui.halaman.product.DetailproductDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsPViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepo
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val productId: String = checkNotNull(savedStateHandle[DetailproductDestination.productId])

    val uiState: StateFlow<DetailUIState> =
        repository.getProductById(productId)
            .filterNotNull()
            .map {
                DetailUIState(addEvent = it.toDetailProduct())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailUIState()
            )

    suspend fun deleteProduct() {
        repository.delete(productId)

    }


}