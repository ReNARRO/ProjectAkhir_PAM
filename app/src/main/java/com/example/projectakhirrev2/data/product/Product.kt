package com.example.projectakhirrev2.data.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblProduct")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: String,
    val description: String,
    val productImageId: String
)
