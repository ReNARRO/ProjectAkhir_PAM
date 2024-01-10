package com.example.projectakhirrev2.data.product

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class StorageUtil {
    companion object {

        fun uploadToStorage(uri: Uri, context: Context, type: String, callback: (String) -> Unit) {
            val storage = Firebase.storage
            val storageRef = storage.reference

            val uniqueImageName = UUID.randomUUID().toString()
            val spaceRef: StorageReference = if (type == "image") {
                storageRef.child("images/$uniqueImageName.jpg")
            } else {
                storageRef.child("videos/$uniqueImageName.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                val uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnSuccessListener { taskSnapshot ->
                    // Get the download URL
                    spaceRef.downloadUrl.addOnCompleteListener { downloadTask ->
                        if (downloadTask.isSuccessful) {
                            val downloadUrl = downloadTask.result.toString()
                            callback.invoke(downloadUrl) // Panggil callback dengan URL gambar
                        } else {
                            Toast.makeText(context, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
