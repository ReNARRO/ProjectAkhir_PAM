package com.example.projectakhirrev2.ui.halaman

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.halaman.product.AddDestinasi
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onAddProduct: NavHostController,
){
    var kode by remember { mutableStateOf("") }
    var isDialogVisible by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
    onProductClick: (Int) -> Unit = {},

    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
    }
}
