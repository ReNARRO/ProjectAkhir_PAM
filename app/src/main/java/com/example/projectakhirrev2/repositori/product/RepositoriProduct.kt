package com.example.projectakhirrev2.repositori.product

import com.example.projectakhirrev2.data.product.Product
import kotlinx.coroutines.flow.Flow

interface RepositoriProduct {
    fun getAllProductStream(): Flow<List<Product>>

    fun getProductStream(id: Int): Flow<Product?>

    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
}