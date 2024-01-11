package com.example.projectakhirrev2.repositori.product

import com.example.projectakhirrev2.data.product.Product

data class AddUIState(
    val addEvent: AddEvent = AddEvent(),
)

data class AddEvent(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val deskripsi: String = "",
    val imageUrl: String = ""
)
fun AddEvent.toProduct() = Product(
    id = id,
    name = name,
    price =price,
    deskripsi = deskripsi,
    imageUrl = imageUrl
)

data class DetailUIState(
    val addEvent: AddEvent = AddEvent(),
)

fun Product.toDetailProduct(): AddEvent =
    AddEvent(
        id = id,
        name = name,
        price =price,
        deskripsi = deskripsi,
        imageUrl = imageUrl
    )

fun Product.toUIStateProduct(): AddUIState = AddUIState(
    addEvent = this.toDetailProduct()
)

data class HomeUIState(
    val listKontak: List<Product> = listOf(),
    val dataLength: Int = 0
)