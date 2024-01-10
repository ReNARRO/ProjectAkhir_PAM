package com.example.projectakhirrev2.ui.halaman.product

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.repositori.product.FirestoreUtil
import com.example.projectakhirrev2.repositori.product.StorageUtil
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar


object AddDestinasi : DestinasiNavigasi {
    override val route = "add"
    override val titleRes = R.string.entry_product
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PreOrderAppBar(
                title = stringResource(AddDestinasi.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        halamanform(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun halamanform(modifier: Modifier) {
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    var name by remember {
        mutableStateOf("")
    }

    var price by remember {
        mutableStateOf("")
    }

    var deskripsi by remember {
        mutableStateOf("")
    }

    var isUploading by remember {
        mutableStateOf(false)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current

    Column {
        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(248.dp))
        Button(onClick = {
            singlePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text("Pick Single Image")
        }


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(id = R.string.nm_product)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text(stringResource(id = R.string.price)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = deskripsi,
            onValueChange = { deskripsi = it },
            label = { Text(stringResource(id = R.string.desk)) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Row {

            // Tombol untuk Submit Form (Simpan ke Firestore)
            Button(onClick = {
                if (name.isNotEmpty() && price.isNotEmpty() && deskripsi.isNotEmpty() && uri != null) {
                    try {


                        isUploading = true // Set status uploading menjadi true
                        StorageUtil.uploadToStorage(uri!!, context, "image") { imageUrl ->
                            isUploading =
                                false // Set status uploading menjadi false setelah selesai
                            // Panggil fungsi untuk menyimpan data ke Firestore
                            FirestoreUtil.saveData(name, price, deskripsi, imageUrl)
                            Toast.makeText(
                                context,
                                "Data submitted successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.d(
                                "FirestoreUtil",
                                "Data submitted successfully: Name=$name, Price=$price, Deskripsi=$deskripsi, ImageUrl=$imageUrl"
                            )
                        }
                    }catch (e: Exception) {
                        Log.e("FirestoreError", "Error during Firestore operation", e)
                    }
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Submit Form")
            }
            if (isUploading) {
                CircularProgressIndicator()
            }
        }
    }
}

