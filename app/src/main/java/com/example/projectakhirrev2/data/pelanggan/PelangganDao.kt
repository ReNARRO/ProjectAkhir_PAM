package com.example.projectakhirrev2.data.pelanggan

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PelangganDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pelanggan: Pelanggan)

    @Update
    suspend fun update(pelanggan: Pelanggan)

    @Delete
    suspend fun delete(pelanggan: Pelanggan)

    @Query("SELECT * from tblPelanggan WHERE id = :id")
    fun getPelanggan(id: Int): Flow<Pelanggan>

    @Query("SELECT * from tblPelanggan ORDER BY nama ASC")
    fun getAllPelanggan(): Flow<List<Pelanggan>>
}