package com.example.projectakhirrev2.repositori.pelanggan

import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.data.pelanggan.PelangganDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoriPelanggan (private val pelangganDao: PelangganDao): RepositoriPelanggan {
    override fun getAllPelangganStream(): Flow<List<Pelanggan>> = pelangganDao.getAllPelanggan()


    override fun getPelangganStream(id: Int): Flow<Pelanggan?> = pelangganDao.getPelanggan(id)

    override suspend fun insertPelanggan(pelanggan: Pelanggan) = pelangganDao.insert(pelanggan)

    override suspend fun deletePelanggan(pelanggan: Pelanggan) = pelangganDao.delete(pelanggan)

    override suspend fun updatePelanggan(pelanggan: Pelanggan) = pelangganDao.update(pelanggan)
}