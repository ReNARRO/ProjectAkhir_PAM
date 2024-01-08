package com.example.projectakhirrev2.data.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * from tblProduct WHERE id = :id")
    fun getProduct(id: Int): Flow<Product>

    @Query("SELECT * from tblProduct ORDER BY title ASC")
    fun getAllProduct(): Flow<List<Product>>
}