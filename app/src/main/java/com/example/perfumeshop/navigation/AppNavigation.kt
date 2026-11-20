package com.example.perfumeshop.navigation
}
    }

}
    }
}
    }
}
package com.example.perfumeshop.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.perfumeshop.data.Perfume
import com.example.perfumeshop.ui.screens.HomeScreen
import com.example.perfumeshop.viewmodel.PerfumeViewModel

// ---------- Routes ----------
object Routes {
    const val HOME = "home"
    const val CART = "cart"
    const val DETAIL = "detail/{perfumeId}"
    fun detail(id: Int) = "detail/$id"
}

// ---------- NavHost + bottom bar ----------
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Created above the NavHost so every screen shares the same instance
    val perfumeVm: PerfumeViewModel = viewModel()
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
                    onPerfumeClick = { id -> navController.navigate(Routes.detail(id)) }
                )
            }

            composable(Routes.CART) {
                // TODO: replace with CartScreen() once the 4th member pushes it
                Text("Cart", modifier = Modifier.padding(16.dp))
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
                    onAddToCart = { perfume ->
                        // TODO: replace with cartVm.addToCart(perfume) once CartViewModel exists
                        Toast.makeText(context, "${perfume.name} added to cart", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

// ---------- Detail screen ----------
@Composable
fun DetailScreen(
    perfumeId: Int,
    viewModel: PerfumeViewModel,
    onBack: () -> Unit,
    onAddToCart: (Perfume) -> Unit,
    modifier: Modifier = Modifier
) {    val perfume = viewModel.getPerfumeById(perfumeId)

    Column(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        if (perfume == null) {
            Text("Perfume not found", modifier = Modifier.padding(16.dp))
            return@Column
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(perfume.imageRes),
                contentDescription = perfume.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = perfume.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = perfume.formattedPrice(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text("Volume: ${perfume.volumeMl} ml", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text("Notes: ${perfume.notes}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
