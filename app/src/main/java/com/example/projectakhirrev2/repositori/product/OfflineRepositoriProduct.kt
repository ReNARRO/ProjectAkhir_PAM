package com.example.projectakhirrev2.repositori.product

import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.data.product.ProductDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoriProduct(private val productDao: ProductDao): RepositoriProduct {
    override fun getAllProductStream(): Flow<List<Product>> = productDao.getAllProduct()


    override fun getProductStream(id: Int): Flow<Product?> = productDao.getProduct(id)

    override suspend fun insertProduct(product: Product) = productDao.insert(product)

    override suspend fun deleteProduct(product: Product) = productDao.delete(product)

    override suspend fun updateProduct(product: Product) = productDao.update(product)
}

