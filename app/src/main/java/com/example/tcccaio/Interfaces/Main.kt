package com.example.tcccaio.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import com.example.tcccaio.DataClass.Exercicio
import com.example.tcccaio.DataClass.Treino

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(navController: NavController) {

    var treinos by remember { mutableStateOf<List<Treino>>(emptyList()) }
    var diaExpandido by remember { mutableStateOf<String?>(null) }

    // Simular alguns treinos cadastrados (remova isso quando conectar com o banco)
    LaunchedEffect(Unit) {
        treinos = listOf(
            Treino(
                id = "1",
                diaSemana = "Segunda-feira",
                exercicios = listOf(
                    Exercicio(
                        nome = "Supino Reto",
                        series = "4",
                        repeticoes = "12",
                        observacoes = "Peso moderado, descanso 60s"
                    ),
                    Exercicio(
                        nome = "Agachamento Livre",
                        series = "3",
                        repeticoes = "15",
                        observacoes = "Foco na execução"
                    ),
                    Exercicio(
                        nome = "Remada Curvada",
                        series = "4",
                        repeticoes = "10",
                        observacoes = "Peso pesado"
                    )
                ),
                temCardio = true,
                tempoCardio = "20",
                observacoesGerais = "Foco em peitoral e costas"
            ),
            Treino(
                id = "2",
                diaSemana = "Quarta-feira",
                exercicios = listOf(
                    Exercicio(
                        nome = "Desenvolvimento Halteres",
                        series = "4",
                        repeticoes = "12",
                        observacoes = "Peso moderado"
                    ),
                    Exercicio(
                        nome = "Elevação Lateral",
                        series = "3",
                        repeticoes = "15",
                        observacoes = "Controle o movimento"
                    ),
                    Exercicio(
                        nome = "Tríceps Corda",
                        series = "3",
                        repeticoes = "12",
                        observacoes = "Foco na contração"
                    )
                ),
                temCardio = false,
                observacoesGerais = "Dia de ombros e tríceps"
            ),
            Treino(
                id = "3",
                diaSemana = "Sexta-feira",
                exercicios = listOf(
                    Exercicio(
                        nome = "Leg Press",
                        series = "4",
                        repeticoes = "12",
                        observacoes = "Carga progressiva"
                    ),
                    Exercicio(
                        nome = "Cadeira Extensora",
                        series = "3",
                        repeticoes = "15",
                        observacoes = "Foco no quadríceps"
                    ),
                    Exercicio(
                        nome = "Stiff",
                        series = "4",
                        repeticoes = "10",
                        observacoes = "Cuidado com a coluna"
                    )
                ),
                temCardio = true,
                tempoCardio = "15",
                observacoesGerais = "Dia de pernas completo"
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Meus Treinos",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
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
                    OutlinedButton(
                        onClick = {
                            navController.navigate("Main")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF456179), // Azul
                            contentColor = Color.White // Texto branco
                        )
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Tela Principal",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Inicio")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate("About") {
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Sobre",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sobre")
                    }

                    Spacer(modifier = Modifier.width(5.dp))

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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (treinos.isEmpty()) {
                // Estado quando não há treinos
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.AddCircle,
                        contentDescription = "Nenhum treino",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nenhum treino cadastrado",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Entre em contato com seu treinador para receber seu treino personalizado",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            navController.navigate("Main")
                        }
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tela Principal")
                    }
                }
            } else {
                // Lista de treinos por dia
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(treinos, key = { it.id }) { treino ->
                        DiaTreinoCard(
                            treino = treino,
                            isExpanded = diaExpandido == treino.diaSemana,
                            onExpandToggle = {
                                diaExpandido = if (diaExpandido == treino.diaSemana) {
                                    null
                                } else {
                                    treino.diaSemana
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
fun DiaTreinoCard(
    treino: Treino,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandToggle() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header com dia da semana e informações resumidas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = treino.diaSemana,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${treino.exercicios.size} exercícios",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                // Indicador de expansão
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if (isExpanded) "Recolher" else "Expandir",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Informações do cardio se houver
            if (treino.temCardio && treino.tempoCardio.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💨 Cardio: ${treino.tempoCardio} minutos",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Observações gerais
            if (treino.observacoesGerais.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "📝 ${treino.observacoesGerais}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            // Lista de exercícios (expandida)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    treino.exercicios.forEachIndexed { index, exercicio ->
                        ExercicioAlunoCard(
                            exercicio = exercicio,
                            numero = index + 1
                        )
                    }
                }
            }

            // Indicador de clique para expandir/recolher
            if (!isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Clique para ver os exercícios →",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ExercicioAlunoCard(
    exercicio: Exercicio,
    numero: Int
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Nome do exercício e número
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = numero.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = exercicio.nome,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Séries e repetições
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoItem(
                    label = "Séries",
                    value = exercicio.series
                )
                InfoItem(
                    label = "Repetições",
                    value = exercicio.repeticoes
                )
            }

            // Observações se houver
            if (exercicio.observacoes.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "💡 ${exercicio.observacoes}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}