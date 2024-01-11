package com.example.projectakhirrev2.data.pelanggan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblPelanggan")
data class Pelanggan(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nama : String,
    val angkatan : String,
    val ukuran :String,
    val telpon : String,
    val jenis_p : String,

)
