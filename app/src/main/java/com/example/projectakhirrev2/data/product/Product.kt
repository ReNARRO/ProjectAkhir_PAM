package com.example.projectakhirrev2.data.product

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirestoreUtil {
    private val db = Firebase.firestore

    fun saveData(name: String, price: String, deskripsi: String, imageUrl: String) {
        val data = hashMapOf(
            "name" to name,
            "price" to price,
            "deskripsi" to deskripsi,
            "image_url" to imageUrl
            // Tambahkan field lain jika diperlukan
        )

        db.collection("products")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreUtil", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreUtil", "Error adding document", e)
            }
    }
}
