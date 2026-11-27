package com.example.perfumeshop.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perfumeshop.ui.screens.CartScreen
import com.example.perfumeshop.ui.screens.DetailScreen
import com.example.perfumeshop.ui.screens.HomeScreen
import com.example.perfumeshop.viewmodel.CartViewModel
import com.example.perfumeshop.viewmodel.PerfumeViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

// Routes
object Routes {
    const val HOME = "home"
    const val CART = "cart"
    const val DETAIL = "detail/{perfumeId}"
    fun detail(id: Int) = "detail/$id"
}

//  NavHost + bottom bar
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Created above the NavHost so every screen shares the same instance
    val perfumeVm: PerfumeViewModel = viewModel()
    val cartVm: CartViewModel = viewModel()
    val context = LocalContext.current

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Routes.HOME || currentRoute == Routes.DETAIL,
                    onClick = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.CART,
                    onClick = {
                        navController.navigate(Routes.CART) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                    label = { Text("Cart") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    onPerfumeClick = { id -> navController.navigate(Routes.detail(id)) },
                    // same ViewModel as the Detail screen, so the data loads only once
                    viewModel = perfumeVm
                )
            }

            composable(Routes.CART) {
                CartScreen(
                    cartViewModel = cartVm
                )
            }

            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("perfumeId") { type = NavType.IntType })
            ) { entry ->
                val perfumeId = entry.arguments?.getInt("perfumeId") ?: -1
                DetailScreen(
                    perfumeId = perfumeId,
                    viewModel = perfumeVm,
                    onBack = { navController.popBackStack() },
                    onAddToCart = {
                        perfumeVm.getPerfumeById(perfumeId)?.let { perfume ->
                            cartVm.addToCart(perfume)
                            Toast.makeText(context, "${perfume.name} added to cart", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }
}