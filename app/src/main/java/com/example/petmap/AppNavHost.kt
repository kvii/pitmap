package com.example.petmap

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petmap.ui.page.HomeScreen
import com.example.petmap.ui.page.LoginScreen
import com.example.petmap.ui.page.MessageScreen
import com.example.petmap.ui.page.MyScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = DEST_LOGIN,
        modifier = modifier,
    ) {
        composable(route = DEST_LOGIN) {
            LoginScreen(
                snackBarHostState = snackBarHostState,
                navigateToHome = {
                    navController.navigate(DEST_HOME) {
                        popUpTo(DEST_LOGIN) { inclusive = true }
                    }
                },
            )
        }
        composable(route = DEST_HOME) {
            HomeScreen(
                navigateToMy = { navController.navigate(DEST_MY) },
                navigateToMessage = { navController.navigate(DEST_MESSAGE) },
            )
        }
        composable(route = DEST_MY) {
            MyScreen()
        }
        composable(route = DEST_MESSAGE) {
            MessageScreen()
        }
    }
}

const val DEST_LOGIN = "login"
const val DEST_HOME = "home"
const val DEST_MY = "my"
const val DEST_MESSAGE = "message"
