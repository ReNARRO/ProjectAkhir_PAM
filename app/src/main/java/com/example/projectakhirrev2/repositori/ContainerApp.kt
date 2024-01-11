package com.example.projectakhirrev2.repositori

import android.content.Context
import com.example.projectakhirrev2.data.DatabasePreOrder
import com.example.projectakhirrev2.repositori.pelanggan.OfflineRepositoriPelanggan
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan
import com.example.projectakhirrev2.repositori.product.ProductRepo
import com.example.projectakhirrev2.repositori.product.ProductRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore

interface ContainerApp{
    val repositoriPelanggan: RepositoriPelanggan
    val productRepo : ProductRepo
}
class ContainerDataApp(private val context: Context): ContainerApp{
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override val repositoriPelanggan: RepositoriPelanggan by lazy {
        OfflineRepositoriPelanggan(DatabasePreOrder.getDatabase(context).pelangganDao())
    }

    override val productRepo: ProductRepo by lazy {
        ProductRepositoryImpl(firestore)
    }

}