package com.example.petmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.petmap.ui.theme.PetMapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyApp() }
    }
}

@Composable
fun MyApp() {
    PetMapTheme {
        val navController = rememberNavController()
        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    snackBarHostState,
                    modifier = Modifier.padding(WindowInsets.ime.asPaddingValues())
                )
            },
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                snackBarHostState = snackBarHostState,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}