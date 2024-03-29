package com.example.projectakhirrev2.ui.halaman.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.data.pelanggan.DataProduct.jenis_p
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.viewmodel.Order.DetailPelanggan
import com.example.projectakhirrev2.ui.viewmodel.Order.OrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.UIStatePelanggan
import kotlinx.coroutines.launch

object OrderDestinasi : DestinasiNavigasi {
    override val route = "order"
    override val titleRes = R.string.pelanggan
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderPelangganScreen(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PreOrderAppBar(
                title = stringResource(OrderDestinasi.titleRes),
                canNavigateBack = true,
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        OrderPelangganBody(
            uiStatePelanggan = viewModel.uiStatePelanggan,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePelanggan()
                    navigateBack
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}


@Composable
fun OrderPelangganBody(
    uiStatePelanggan: UIStatePelanggan,
    onSiswaValueChange: (DetailPelanggan) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputPelanggan(
            detailPelanggan = uiStatePelanggan.detailPelanggan,
            onValueChange = onSiswaValueChange,
            modifier = modifier.fillMaxWidth(),
            pilihanjenis = jenis_p.map { id -> context.resources.getString(id) },
            onSelectionChanged = { uiStatePelanggan.detailPelanggan.jenis_p }
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStatePelanggan.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.btn_save))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPelanggan(
    detailPelanggan: DetailPelanggan,
    modifier: Modifier = Modifier,
    pilihanjenis: List<String>,
    onSelectionChanged: (String) -> Unit,
    onValueChange: (DetailPelanggan) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = detailPelanggan.nama,
            onValueChange = { onValueChange(detailPelanggan.copy(nama = it)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "") },
            label = { Text(stringResource(R.string.nama)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,

            )
        OutlinedTextField(
            value = detailPelanggan.angkatan,
            onValueChange = { onValueChange(detailPelanggan.copy(angkatan = it)) },
            label = { Text(stringResource(R.string.angkatan)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = detailPelanggan.ukuran,
            onValueChange = { onValueChange(detailPelanggan.copy(ukuran = it)) },
            label = { Text(stringResource(R.string.ukuran)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = detailPelanggan.telpon,
            onValueChange = { onValueChange(detailPelanggan.copy(telpon = it)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "") },
            label = { Text(stringResource(R.string.telpon)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        pilihanjenis.forEach { item ->
            Row(modifier = Modifier.selectable(
                selected = detailPelanggan.jenis_p == item,
                onClick = {
                    onValueChange(detailPelanggan.copy(jenis_p = item))
                    onSelectionChanged(item)
                }
            ),
                verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = detailPelanggan.jenis_p == item,
                    onClick = {
                        onValueChange(detailPelanggan.copy(jenis_p = item))
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
    }
    if (enabled) {
        Text(
            text = stringResource(id = R.string.required_field),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
        )
    }
    Divider(
        thickness = dimensionResource(id = R.dimen.padding_small),
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))

    )
}
