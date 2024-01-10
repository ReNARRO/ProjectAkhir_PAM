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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectakhirrev2.R
import com.example.projectakhirrev2.ui.halaman.DestinasiHome
import com.example.projectakhirrev2.ui.halaman.product.DetailProductScreen
import com.example.projectakhirrev2.ui.halaman.product.DetailsPDestinasi
import com.example.projectakhirrev2.ui.halaman.HomeScreen
import com.example.projectakhirrev2.ui.halaman.order.DetailDestination
import com.example.projectakhirrev2.ui.halaman.order.DetailsScreen
import com.example.projectakhirrev2.ui.halaman.order.HistoryDestinasi
import com.example.projectakhirrev2.ui.halaman.order.HistoryScreen
import com.example.projectakhirrev2.ui.halaman.order.ItemEditDestination
import com.example.projectakhirrev2.ui.halaman.order.ItemEditScreen
import com.example.projectakhirrev2.ui.halaman.order.OrderDestinasi
import com.example.projectakhirrev2.ui.halaman.order.OrderPelangganScreen
import com.example.projectakhirrev2.ui.halaman.product.AddDestinasi
import com.example.projectakhirrev2.ui.halaman.product.AddProductScreen
import com.example.projectakhirrev2.ui.halaman.product.DetailScreen
import com.example.projectakhirrev2.ui.halaman.product.DetailproductDestination

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
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {},
                onAddProduct = navController,
                onDetailClick = { itemId ->
                    navController.navigate("${DetailsPDestinasi.route}/$itemId")
                    println("itemId: $itemId")
                } ,
                onAddCustClicked = { navController.navigate(OrderDestinasi.route) },
                onHistoryCustClicked = { navController.navigate(HistoryDestinasi.route) }
            )
        }
        composable(AddDestinasi.route){
            AddProductScreen(navigateBack = {navController.popBackStack()})
        }
        composable(DetailsPDestinasi.route){
            DetailProductScreen(
                navigateBack = {navController.popBackStack()},
            )
        }
        composable(OrderDestinasi.route){
            OrderPelangganScreen(
                navigateBack = { navController.popBackStack() })
        }
        composable(HistoryDestinasi.route){
            HistoryScreen(
                navigateBack = {navController.popBackStack()},
                onDetailClick = {navController.navigate("${DetailDestination.route}/$it")}
            )
        }
        composable(
            DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.pelangganIdArg) {
                type = NavType.IntType
            })
        ) {
            DetailsScreen(navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                navigasiBack = { navController.popBackStack() }
            )
        }
        composable(
            ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailproductDestination.productId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val kontakId = backStackEntry.arguments?.getString(DetailproductDestination.productId)
            kontakId?.let {
                DetailScreen()
            }
        }
    }
}