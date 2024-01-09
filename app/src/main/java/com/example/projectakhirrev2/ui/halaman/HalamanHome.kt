package com.example.projectakhirrev2.ui.halaman

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.product.Product
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.halaman.product.AddDestinasi
import com.example.projectakhirrev2.ui.halaman.product.DetailsPDestinasi
import com.example.projectakhirrev2.ui.viewmodel.HomeViewModel
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
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        val uiStateSiswa by viewModel.homeUiState.collectAsState()
        BodyHome(
            itemProduct = uiStateSiswa.listSiswa,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            onProductClick = onDetailClick,
        )
    }
}
@Composable
fun BodyHome(
    itemProduct : List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (Int) -> Unit = {},

    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(itemProduct.isEmpty()){
            Text(
                text = stringResource(id = R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        }else{
            ListSiswa(
                itemProduct = itemProduct,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onItemClick = {onProductClick(it.id)}
            )
        }
    }
}
@Composable
fun ListSiswa(
    itemProduct : List<Product>,
    modifier: Modifier = Modifier,
    onItemClick: (Product) -> Unit
){
    LazyColumn(modifier = Modifier){
        items(items = itemProduct, key = {it.id}){
                person ->
            DataSiswa(
                product = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(person) }
            )
        }
    }
}
@Composable
fun DataSiswa(
    product: Product,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier) {
            //Menentapkan gambar
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = product.price,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}