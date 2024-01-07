package com.example.projectakhirrev2.ui.halaman.product

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.navigasi.DestinasiNavigasi
import com.example.projectakhirrev2.navigasi.PreOrderAppBar
import com.example.projectakhirrev2.ui.halaman.DestinasiHome
import com.example.projectakhirrev2.ui.halaman.order.HistoryDestinasi
import com.example.projectakhirrev2.ui.halaman.order.OrderDestinasi

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)
object DetailsPDestinasi: DestinasiNavigasi {
    override val route = "detailsproduct"
    override val titleRes = R.string.detail_product
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(
    onAddCustClicked:() -> Unit,
    onHistoryCustClicked: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
){
    var selectedItems by remember {
        mutableStateOf("")
    }
    Scaffold (
        topBar = {
            PreOrderAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedItems == "Detail Product",
                    onClick = { /*TODO*/ },
                    label = { Text(text = "Detail Product")},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
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
    ){ innerPadding ->
        DetailProductBody(
        modifier = modifier
            .padding(innerPadding)
        )

    }
}

@Composable
fun DetailProductBody(
    modifier: Modifier = Modifier,
){}

@Composable
fun ProductLayout(){}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
){
    Card(

    ) {

    }
}