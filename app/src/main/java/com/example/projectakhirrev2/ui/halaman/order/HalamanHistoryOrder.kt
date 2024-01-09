package com.example.projectakhirrev2.ui.halaman.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.pelanggan.Pelanggan
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.viewmodel.Order.HistoryOrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel

object HistoryDestinasi: DestinasiNavigasi {
    override val route = "historyorder"
    override val titleRes = R.string.history
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HistoryOrderViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PreOrderAppBar(
                title = stringResource(HistoryDestinasi.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                )
        },
    ) {
            innerPadding ->
        val uiStateSiswa by viewModel.homeUiState.collectAsState()
        BodyHistory(
            itemPelanggan = uiStateSiswa.listSiswa,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            onPelangganClick = onDetailClick,
        )
    }
}
@Composable
fun BodyHistory(
    itemPelanggan : List<Pelanggan>,
    modifier: Modifier = Modifier,
    onPelangganClick: (Int) -> Unit = {},

    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if(itemPelanggan.isEmpty()){
            Text(
                text = stringResource(id = R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        }else{
            ListPelanggan(
                itemPelanggan = itemPelanggan,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onItemClick = {onPelangganClick(it.id)}
            )
        }
    }
}
@Composable
fun ListPelanggan(
    itemPelanggan : List<Pelanggan>,
    modifier: Modifier = Modifier,
    onItemClick: (Pelanggan) -> Unit
){
    LazyColumn(modifier = Modifier){
        items(items = itemPelanggan, key = {it.id}){
                person ->
            DataPelanggan(
                pelanggan = person,
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(person) }
            )
        }
    }
}
@Composable
fun DataPelanggan(
    pelanggan: Pelanggan,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Text(
                    text = pelanggan.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                )
                Text(
                    text = pelanggan.telpon,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Text(
                text = pelanggan.ukuran,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = pelanggan.angkatan,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}