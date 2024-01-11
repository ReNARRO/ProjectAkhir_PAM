package com.example.projectakhirrev2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhirrev2.AplikasiPreOrder
import com.example.projectakhirrev2.ui.viewmodel.Order.DetailsOrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.EditOrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.HistoryOrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.OrderViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            OrderViewModel(aplikasiPreOrder().container.repositoriPelanggan)
        }
        initializer {
            HistoryOrderViewModel(aplikasiPreOrder().container.repositoriPelanggan)
        }
        initializer {
            DetailsOrderViewModel(createSavedStateHandle(),
                aplikasiPreOrder().container.repositoriPelanggan
            )
        }
        initializer {
            EditOrderViewModel(createSavedStateHandle(),
                aplikasiPreOrder().container.repositoriPelanggan
            )
        }
        initializer {
            DetailsPViewModel(createSavedStateHandle(),
                aplikasiPreOrder().container.productRepo
            )
        }

    }
}

fun CreationExtras.aplikasiPreOrder(): AplikasiPreOrder =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiPreOrder)