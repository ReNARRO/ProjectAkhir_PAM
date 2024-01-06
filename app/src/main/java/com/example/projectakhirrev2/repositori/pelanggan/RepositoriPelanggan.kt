package com.example.projectakhirrev2.repositori.pelanggan

import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import kotlinx.coroutines.flow.Flow

interface RepositoriPelanggan {
    fun getAllPelangganStream(): Flow<List<Pelanggan>>

    fun getPelangganStream(id: Int): Flow<Pelanggan?>

    suspend fun insertPelanggan(pelanggan: Pelanggan)
    suspend fun deletePelanggan(pelanggan: Pelanggan)
    suspend fun updatePelanggan(pelanggan: Pelanggan)
}