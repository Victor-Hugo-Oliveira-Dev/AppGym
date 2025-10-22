package com.example.tcccaio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tcccaio.Interfaces.About
import com.example.tcccaio.Interfaces.Create
import com.example.tcccaio.Interfaces.Login
import com.example.tcccaio.Interfaces.Main
import com.example.tcccaio.Interfaces.MoutTrainner
import com.example.tcccaio.Interfaces.Register
import com.example.tcccaio.ui.theme.TccCaioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TccCaioTheme {
                TccCaioApp()
            }
        }
    }
}

@Composable
fun TccCaioApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {
        composable("Login") {
            Login(navController)
        }
        composable("Main") {
            Main(navController)
        }
        composable("MoutTrainner") {
            MoutTrainner(navController)
        }
        composable("Register") {
            Register(navController)
        }
        composable("About") {
            About(navController)
        }
        composable  ("Create") {
            Create(navController)
        }
    }
}
