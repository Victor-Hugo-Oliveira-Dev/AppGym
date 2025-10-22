package com.example.tcccaio.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tcccaio.Componente.CaixaDeTexto
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController){

    var id by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val userOptions = listOf(
        "Selecione o tipo de usuário:" to "",
        "Professor" to "adm",
        "Aluno" to "user"
    )
    val selectedLabel = userOptions.find { it.second == userType }?.first ?: userOptions[0].first

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = selectedLabel,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir menu",
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    userOptions.forEach { (label, value) ->
                        if (value.isNotEmpty()) {
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    userType = value
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de código de usuário
            CaixaDeTexto(
                value = id,
                onValueChange = { id = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Código de usuário",
                enabled = !isLoading && userType.isNotEmpty()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão de login
            Button(
                onClick = {
                    if (userType.isBlank() || id.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Preencha todos os campos.")
                        }
                    } else {
                        isLoading = true
                        scope.launch {
                            if (userType == "adm") {
                                navController.navigate("MoutTrainner") {
                                    popUpTo("Login") { inclusive = true }
                                }
                            } else {
                                navController.navigate("Main") {
                                    popUpTo("Login") { inclusive = true }
                                }
                            }
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading && userType.isNotEmpty() && id.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Carregando...")
                } else {
                    Text("Entrar")
                }
            }
        }
    }
}