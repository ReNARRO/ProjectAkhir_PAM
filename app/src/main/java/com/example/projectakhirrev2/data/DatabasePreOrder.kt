package com.example.projectakhirrev2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.data.pelanggan.PelangganDao

@Database(entities = [Pelanggan::class], version = 1, exportSchema = false)
abstract class DatabasePreOrder: RoomDatabase() {
    abstract fun pelangganDao(): PelangganDao

    companion object{
        @Volatile
        private  var Instance: DatabasePreOrder? = null

        fun getDatabase(context: Context): DatabasePreOrder {
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context,
                    DatabasePreOrder::class.java,
                    "preorder_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance=it}

            })
        }
    }
}