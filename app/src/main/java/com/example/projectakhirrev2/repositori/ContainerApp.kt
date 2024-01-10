package com.example.projectakhirrev2.repositori

import android.content.Context
import com.example.projectakhirrev2.data.DatabasePreOrder
import com.example.projectakhirrev2.repositori.pelanggan.OfflineRepositoriPelanggan
import com.example.projectakhirrev2.repositori.pelanggan.RepositoriPelanggan

interface ContainerApp{
    val repositoriPelanggan: RepositoriPelanggan
}
class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriPelanggan: RepositoriPelanggan by lazy {
        OfflineRepositoriPelanggan(DatabasePreOrder.getDatabase(context).pelangganDao())
    }

}