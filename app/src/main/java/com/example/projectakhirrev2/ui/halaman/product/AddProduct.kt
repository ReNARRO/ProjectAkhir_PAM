package com.example.projectakhirrev2.ui.halaman.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.viewmodel.Order.DetailPelanggan
import com.example.projectakhirrev2.ui.viewmodel.Order.OrderViewModel
import com.example.projectakhirrev2.ui.viewmodel.Order.UIStatePelanggan
import com.example.projectakhirrev2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


object AddDestinasi: DestinasiNavigasi {
    override val route = "add"
    override val titleRes = R.string.entry_product
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(factory = PenyediaViewModel.Factory )
){
    val coroutineScope = rememberCoroutineScope()
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
    ) {
            innerPadding ->
        AddProductBody(
            uiStatePelanggan = viewModel.uiStatePelanggan,
            onSiswaValueChange = {},
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
fun AddProductBody(
    uiStatePelanggan: UIStatePelanggan,
    onSiswaValueChange: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        FormInputProduct(
            onValueChange = onSiswaValueChange,
            modifier = modifier.fillMaxWidth()
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
fun FormInputProduct(
    modifier: Modifier = Modifier,
    onValueChange: () -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = "",
            onValueChange ={},
            leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "") },
            label = { Text(stringResource(R.string.nm_product)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,

            )
        OutlinedTextField(
            value = "",
            onValueChange ={},
            label = { Text(stringResource(R.string.price)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = "",
            onValueChange ={},
            label = { Text(stringResource(R.string.desk)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        Text(text = stringResource(id = R.string.gambar))


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
}
