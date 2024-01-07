package com.example.projectakhirrev2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.data.pelanggan.PelangganDao
import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.data.product.ProductDao

@Database(entities = [Pelanggan::class, Product::class], version = 1, exportSchema = false)
abstract class DatabasePreOrder: RoomDatabase() {
    abstract fun pelangganDao(): PelangganDao
    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private  var Instance: DatabasePreOrder? = null

        fun getDatabase(context: Context): DatabasePreOrder {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context,
                    DatabasePreOrder::class.java,
                    "siswa_database")
                    .build()
                    .also{ Instance=it}
            })
        }
    }
}