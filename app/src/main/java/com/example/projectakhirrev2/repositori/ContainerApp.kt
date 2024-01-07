package com.example.projectakhirrev2.repositori

import android.content.Context
import com.example.projectakhirrev2.data.DatabasePreOrder
import com.example.projectakhirrev2.repositori.pelanggan.OfflineRepositoriPelanggan
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan
import com.example.projectakhirrev2.repositori.product.OfflineRepositoriProduct
import com.example.projectakhirrev2.repositori.product.RepositoriProduct

interface ContainerApp{
    val repositoriProduct: RepositoriProduct
    val repositoriPelanggan: RepositoriPelanggan
}
class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriProduct: RepositoriProduct by lazy {
        OfflineRepositoriProduct(DatabasePreOrder.getDatabase(context).productDao())
    }
    override val repositoriPelanggan: RepositoriPelanggan by lazy {
        OfflineRepositoriPelanggan(DatabasePreOrder.getDatabase(context).pelangganDao())
    }

}