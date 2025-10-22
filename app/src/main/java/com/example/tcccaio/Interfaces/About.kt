package com.example.tcccaio.Interfaces

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sobre") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xff1b2e3a),
                    titleContentColor = Color.White
                )
            )
        },

        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão WhatsApp
                    FilledTonalButton(
                        onClick = {
                            navController.navigate("Main")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Tela Principal",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Inicio")
                    }

                    Spacer(modifier = Modifier.width(8.dp))


                    OutlinedButton(
                        onClick = {
                            navController.navigate("About") {
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF4C5660), // Azul
                            contentColor = Color.White // Texto branco
                        )
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Sobre",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sobre")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botão Sair
                    OutlinedButton(
                        onClick = {
                            navController.navigate("Login") {
                                popUpTo("Main") { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sair",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sair")
                    }
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0E0E0)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Versão",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff1b2e3a)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Beta, 1.0",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0E0E0)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Créditos",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff1b2e3a)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Desenvolvedor: Caio",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE0E0E0)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Informações",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff1b2e3a)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Aqui o Caio coloca informações do projeto dele",
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    )
}