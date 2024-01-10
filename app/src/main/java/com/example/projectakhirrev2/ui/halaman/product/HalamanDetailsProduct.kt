package com.example.projectakhirrev2.ui.halaman.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    const val productId = "itemId"
    val routeWithArgs = "${route}/{$productId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(
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
){
    Column(
        modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Box(modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Image(painter = painterResource(R.drawable.percobaan),
                contentDescription = "",
                modifier.clip(RoundedCornerShape(16.dp))
            )
        }
        Text(text = "Nama Product", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text(text = "Deskripsi Product", fontSize = 18.sp)
        Text(text = "Harga Product", fontSize = 18.sp)
    }
}

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