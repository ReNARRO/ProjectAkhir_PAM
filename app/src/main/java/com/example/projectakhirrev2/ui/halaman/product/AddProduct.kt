package com.example.projectakhirrev2.ui.halaman.product

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.product.FirestoreUtil
import com.example.projectakhirrev2.data.product.StorageUtil
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.viewmodel.Order.DetailPelanggan
import com.example.projectakhirrev2.ui.viewmodel.Order.OrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.UIStatePelanggan
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


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
        Row {
            Button(onClick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Pick Single Image")
            }

            // Tombol untuk Upload Gambar
            Button(
                onClick = {
                    if (uri != null) {
                        isUploading = true // Set status uploading menjadi true
                        StorageUtil.uploadToStorage(uri!!, context, "image") { imageUrl ->
                            isUploading =
                                false // Set status uploading menjadi false setelah selesai
                            Toast.makeText(
                                context,
                                "Image uploaded successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text("Upload Image")
            }
            if (isUploading) {
                CircularProgressIndicator()
            }
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

        // Tombol untuk Submit Form (Simpan ke Firestore)
        Button(onClick = {
            if (name.isNotEmpty() && price.isNotEmpty() && deskripsi.isNotEmpty() && uri != null) {
                isUploading = true // Set status uploading menjadi true
                StorageUtil.uploadToStorage(uri!!, context, "image") { imageUrl ->
                    isUploading = false // Set status uploading menjadi false setelah selesai
                    // Panggil fungsi untuk menyimpan data ke Firestore
                    FirestoreUtil.saveData(name, price, deskripsi, imageUrl)
                    Toast.makeText(context, "Data submitted successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit Form")
        }
    }
}

