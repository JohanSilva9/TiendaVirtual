package com.example.tienda.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.tienda.viewmodel.AuthViewModel
import com.example.tienda.viewmodel.CartViewModel
import com.example.tienda.viewmodel.ProductViewModel
import com.example.tienda.ui.screens.CartScreen
import com.example.tienda.ui.screens.LoginScreen
import com.example.tienda.ui.screens.ProductListScreen
import kotlinx.coroutines.flow.collectAsState
import androidx.compose.runtime.collectAsState

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    isLoggedInFlow: kotlinx.coroutines.flow.StateFlow<Boolean>
) {
    val navController = rememberNavController()
    val isLoggedIn by isLoggedInFlow.collectAsState()

    val startDestination = if (isLoggedIn) "products" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(authViewModel = authViewModel, onLoginSuccess = {
                navController.navigate("products") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("products") {
            ProductListScreen(productViewModel, cartViewModel, onOpenCart = {
                navController.navigate("cart")
            }, onLogout = {
                authViewModel.logout {
                    navController.navigate("login") { popUpTo("products") { inclusive = true } }
                }
            })
        }
        composable("cart") {
            CartScreen(cartViewModel = cartViewModel, onBack = { navController.popBackStack() })
        }
    }
}
