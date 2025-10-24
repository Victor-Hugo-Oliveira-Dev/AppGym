package com.example.tcccaio.Interfaces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tcccaio.DataClass.Aluno

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoutTrainner(navController: NavController) {

    // Estado para a lista de alunos (em uma aplicação real, isso viria do banco de dados)
    var alunos by remember { mutableStateOf<List<Aluno>>(emptyList()) }

    // Estado para controlar qual aluno está selecionado para criar treino
    var alunoSelecionado by remember { mutableStateOf<Aluno?>(null) }

    // Simular alguns alunos cadastrados (remova isso quando conectar com o banco)
    LaunchedEffect(Unit) {
        alunos = listOf(
            Aluno(
                id = "1",
                nome = "João Silva",
                idade = "25",
                altura = "1.75",
                peso = "70.5",
                proposito = "Hipertrofia",
                diasTreino = listOf("Segunda-feira", "Quarta-feira", "Sexta-feira")
            ),
            Aluno(
                id = "2",
                nome = "Maria Santos",
                idade = "30",
                altura = "1.65",
                peso = "60.0",
                proposito = "Emagrecimento",
                diasTreino = listOf("Terça-feira", "Quinta-feira")
            ),
            Aluno(
                id = "3",
                nome = "Carlos Oliveira",
                idade = "22",
                altura = "1.80",
                peso = "80.0",
                proposito = "Força",
                diasTreino = listOf("Segunda-feira", "Terça-feira", "Quinta-feira", "Sexta-feira")
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de treinos")},
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
                    // Botão Cadastrar
                    FilledTonalButton(
                        onClick = {
                            navController.navigate("Register")
                        },
                        modifier = Modifier.weight(1f),
                        enabled = alunoSelecionado == null
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Cadastrar",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cadastrar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botão Criar Treino
                    FilledTonalButton(
                        onClick = {
                            alunoSelecionado?.let { aluno ->
                                // Navegar para tela de criar treino
                                navController.navigate("Create")
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = alunoSelecionado != null
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Criar Treino",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Criar Treino")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botão Sair
                    OutlinedButton(
                        onClick = {
                            navController.navigate("Login") {
                                popUpTo("MoutTrainner") { inclusive = true }
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (alunos.isEmpty()) {
                // Estado quando não há alunos cadastrados
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Nenhum aluno",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nenhum aluno cadastrado",
                        style = typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Clique em 'Cadastrar' para adicionar seu primeiro aluno",
                        style = typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                // Lista de alunos
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(alunos, key = { it.id }) { aluno ->
                        AlunoCard(
                            aluno = aluno,
                            isSelected = alunoSelecionado?.id == aluno.id,
                            onSelect = {
                                alunoSelecionado = if (alunoSelecionado?.id == aluno.id) {
                                    null
                                } else {
                                    aluno
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlunoCard(
    aluno: Aluno,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 2.dp
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder()
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header com nome e idade
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = aluno.nome,
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${aluno.idade} anos",
                    style = typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Informações físicas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (aluno.altura.isNotBlank()) {
                    InfoChip(
                        label = "Altura",
                        value = "${aluno.altura}m"
                    )
                }
                if (aluno.peso.isNotBlank()) {
                    InfoChip(
                        label = "Peso",
                        value = "${aluno.peso}kg"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Propósito
            Text(
                text = "Objetivo: ${aluno.proposito}",
                style = typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dias de treino
            if (aluno.diasTreino.isNotEmpty()) {
                Text(
                    text = "Dias: ${aluno.diasTreino.joinToString(", ")}",
                    style = typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Indicador de seleção
            if (isSelected) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Selecionado para criar treino",
                    style = typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label:",
                style = typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                style = typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}