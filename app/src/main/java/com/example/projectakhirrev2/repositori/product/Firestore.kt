package com.example.projectakhirrev2.repositori.product

import android.util.Log
import com.example.projectakhirrev2.data.product.Product
import com.google.firebase.firestore.FirebaseFirestore
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

        db.collection("barang")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("FirestoreUtil", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreUtil", "Error adding document", e)
            }
    }
}

object GetData {
    private val db = FirebaseFirestore.getInstance()

    fun fetchProductDataFromFirestore(callback: (List<Product>) -> Unit) {
        db.collection("barang")
            .get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { document ->
                    val id = document.id
                    val name = document.getString("name")
                    val price = document.getString("price")
                    val deskripsi = document.getString("deskripsi")
                    if (name != null && price != null && deskripsi != null) {
                        Product(id = id, name = name, price = price, deskripsi = deskripsi, imageUrl = "")
                    } else {
                        null
                    }
                }
                callback.invoke(products)
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreUtil", "Error getting documents: ", exception)
                callback.invoke(emptyList())
            }
    }
}

