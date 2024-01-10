package com.example.projectakhirrev2.ui.halaman

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.repositori.product.GetData
import com.example.projectakhirrev2.ui.halaman.product.AddDestinasi
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel
import com.google.firebase.firestore.FirebaseFirestore

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onAddProduct: NavHostController,
    onAddCustClicked:() -> Unit,
    onHistoryCustClicked: () -> Unit,
){
    var kode by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var selectedItems by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PreOrderAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isDialogVisible = true
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Ke Halaman Kedua")
            }
            if (isDialogVisible) {
                AlertDialog(
                    onDismissRequest = {
                        isDialogVisible = false
                    },
                    title = {
                        Text(text = "Masukkan Kode")
                    },
                    text = {
                        // Text field to input the code in the dialog
                        TextField(
                            value = kode,
                            onValueChange = {
                                kode = it
                            },
                            label = { Text("Masukkan Kode") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isDialogVisible = false
                                // Navigate to the second screen only if the code is "12345"
                                if (kode == "12345") {
                                    onAddProduct.navigate(AddDestinasi.route)
                                }
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedItems == "Home",
                    onClick = { },
                    label = { Text(text = "Home")},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "")
                    }
                )
                NavigationBarItem(
                    selected = selectedItems == "History Order",
                    onClick = onHistoryCustClicked,
                    label = { Text(text = "History Order")},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "")
                    })
                NavigationBarItem(
                    selected = selectedItems == "Order",
                    onClick = onAddCustClicked,
                    label = { Text(text = "Order")},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "")
                    })
            }
        }
    ) {
            innerPadding ->
        BodyHome(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            onProductClick = onDetailClick,
        )
    }
}
@Composable
fun BodyHome(
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit = {},

    ){
    var productData by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(true) {
        // Ambil data dari Firestore saat komposisi dijalankan
        GetData.fetchProductDataFromFirestore { products ->
            productData = products
            isLoading = false
        }
    }

    LazyColumn (
        contentPadding = PaddingValues(top = 70.dp)
    ){
        // Tampilkan indikator loading jika sedang mengambil data
        if (isLoading) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    CircularProgressIndicator()
                }
            }
        } else {
            // Tampilkan data dari Firestore
            items(productData) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onProductClick(product.id) },
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Tampilkan gambar (di sini kamu bisa tambahkan Image jika kamu memiliki URL gambar)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            // Tampilkan nama product
                            Text(text = "Product Name: ${product.name}", fontWeight = FontWeight.Bold)

                            // Tampilkan harga product
                            Text(text = "Price: ${product.price}")

                            // Tampilkan deskripsi product
                            Text(text = "Description: ${product.deskripsi}")
                        }
                    }
                }
            }
        }
    }
}
