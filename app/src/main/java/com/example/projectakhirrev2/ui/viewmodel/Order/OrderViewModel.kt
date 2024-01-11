package com.example.projectakhirrev2.ui.viewmodel.Order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan

class OrderViewModel(private val repositoriPelanggan: RepositoriPelanggan): ViewModel(){
    var uiStatePelanggan by mutableStateOf(UIStatePelanggan())
        private set

    private fun validasiInput(uiState: DetailPelanggan = uiStatePelanggan.detailPelanggan): Boolean{
        return with(uiState){
            nama.isNotBlank() && angkatan.isNotBlank() && ukuran.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailPelanggan: DetailPelanggan){
        uiStatePelanggan =
            UIStatePelanggan(detailPelanggan = detailPelanggan, isEntryValid = validasiInput(detailPelanggan))
    }

    suspend fun savePelanggan(){
        if(validasiInput()){
            repositoriPelanggan.insertPelanggan(uiStatePelanggan.detailPelanggan.toPelanggan())
        }
    }
}
data class UIStatePelanggan(
    val detailPelanggan: DetailPelanggan = DetailPelanggan(),
    val isEntryValid: Boolean = false
)
data class DetailPelanggan(
    val id: Int = 0,
    val nama: String = "",
    val angkatan: String = "",
    val ukuran: String = "",
    val telpon: String = "",
    val jenis_p : String = ""
)
fun DetailPelanggan.toPelanggan(): Pelanggan = Pelanggan(
    id = id,
    nama = nama,
    angkatan = angkatan,
    ukuran = ukuran,
    telpon = telpon,
    jenis_p = jenis_p
)

fun Pelanggan.toUiStatePelanggan(isEntryValid: Boolean = false): UIStatePelanggan = UIStatePelanggan(
    detailPelanggan = this.toDetailPelanggan(),
    isEntryValid = isEntryValid
)

fun Pelanggan.toDetailPelanggan(): DetailPelanggan = DetailPelanggan(
    id = id,
    nama = nama,
    angkatan = angkatan,
    ukuran = ukuran,
    telpon = telpon,
    jenis_p = jenis_p
)

