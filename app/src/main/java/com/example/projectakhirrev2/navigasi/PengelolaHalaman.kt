package com.example.projectakhirrev2.navigasi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.ui.halaman.DestinasiHome
import com.example.projectakhirrev2.ui.halaman.product.DetailProductScreen
import com.example.projectakhirrev2.ui.halaman.product.DetailsPDestinasi
import com.example.projectakhirrev2.ui.halaman.HomeScreen
import com.example.projectakhirrev2.ui.halaman.order.HistoryDestinasi
import com.example.projectakhirrev2.ui.halaman.order.HistoryScreen
import com.example.projectakhirrev2.ui.halaman.order.OrderDestinasi
import com.example.projectakhirrev2.ui.halaman.order.OrderPelangganScreen

@Composable
fun PreOrderApp(navController: NavHostController = rememberNavController()) {
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreOrderAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }
    )
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DetailsPDestinasi.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(navigateToItemEntry = { /*TODO*/ }, onAddProduct = navController )
        }
        composable(DetailsPDestinasi.route){
            DetailProductScreen(
                navigateBack = {navController.popBackStack()},
                onAddCustClicked = { navController.navigate(OrderDestinasi.route) },
                onHistoryCustClicked = { navController.navigate(HistoryDestinasi.route) })
        }
        composable(OrderDestinasi.route){
            OrderPelangganScreen(
                navigateBack = { navController.popBackStack() })
        }
        composable(HistoryDestinasi.route){
            HistoryScreen(
                navigateBack = {navController.popBackStack()}
            )
        }
    }
}