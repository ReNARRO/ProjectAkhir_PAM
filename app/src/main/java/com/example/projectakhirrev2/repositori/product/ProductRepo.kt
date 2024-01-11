package com.example.projectakhirrev2.repositori.product

import com.example.projectakhirrev2.data.product.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface ProductRepo{
    fun getProductById(peoductId: String): Flow<Product>
    suspend fun delete(kontakId: String)
}

class ProductRepositoryImpl(private val firestore: FirebaseFirestore) : ProductRepo{

    override suspend fun delete(productId: String) {
        firestore.collection("barang").document(productId).delete().await()
    }
    override fun getProductById(productId: String): Flow<Product> {
        return flow {
            val snapshot = firestore.collection("barang").document(productId).get().await()
            val product = snapshot.toObject(Product::class.java)
            emit(product!!)
        }.flowOn(Dispatchers.IO)
    }
}