package com.example.projectakhirrev2.ui.halaman.product

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi

object DetailproductDestination : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_product
    const val productId = "itemId"
    val routeWithArgs = "$route/{$productId}"
}

@Composable
fun DetailScreen(
) {

    Text(text = "hello World")
}