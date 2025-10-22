package com.example.tcccaio.Interfaces

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tcccaio.Componente.CaixaDeTexto
import kotlinx.coroutines.launch
import com.example.tcccaio.DataClass.Exercicio
import com.example.tcccaio.DataClass.Treino

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Create(navController: NavController) {

    // Estados do formulário
    var diaSemana by remember { mutableStateOf("") }
    var exercicios by remember { mutableStateOf<List<Exercicio>>(emptyList()) }
    var temCardio by remember { mutableStateOf(false) }
    var tempoCardio by remember { mutableStateOf("") }
    var observacoesGerais by remember { mutableStateOf("") }

    // Estados para dropdowns
    var diaExpanded by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Opções para os dropdowns
    val diasSemanaOptions = listOf(
        "Segunda-feira",
        "Terça-feira",
        "Quarta-feira",
        "Quinta-feira",
        "Sexta-feira",
        "Sábado",
        "Domingo"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Criar Treino",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Configuração do Treino",
                style = MaterialTheme.typography.headlineSmall
            )

            // Seleção do Dia da Semana
            Text(
                text = "Dia da Semana *",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { diaExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(
                        text = diaSemana.ifBlank { "Selecione o dia" },
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Abrir menu dias",
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = diaExpanded,
                    onDismissRequest = { diaExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    diasSemanaOptions.forEach { dia ->
                        DropdownMenuItem(
                            text = { Text(dia) },
                            onClick = {
                                diaSemana = dia
                                diaExpanded = false
                            }
                        )
                    }
                }
            }

            // Seção de Exercícios
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Exercícios",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Button(
                        onClick = {
                            exercicios = exercicios + Exercicio()
                        },
                        enabled = !isLoading
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar exercício")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Adicionar")
                    }
                }

                // Lista de exercícios
                exercicios.forEachIndexed { index, exercicio ->
                    ExercicioCard(
                        exercicio = exercicio,
                        onUpdate = { updated ->
                            exercicios = exercicios.toMutableList().apply {
                                this[index] = updated
                            }
                        },
                        onDelete = {
                            exercicios = exercicios.toMutableList().apply {
                                removeAt(index)
                            }
                        },
                        enabled = !isLoading
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (exercicios.isEmpty()) {
                    Text(
                        text = "Nenhum exercício adicionado. Clique em 'Adicionar' para começar.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            // Seção de Cardio
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cardio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = temCardio,
                            onCheckedChange = { temCardio = it }
                        )
                    }

                    if (temCardio) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CaixaDeTexto(
                            value = tempoCardio,
                            onValueChange = {
                                if (it.all { char -> char.isDigit() } && it.length <= 3) {
                                    tempoCardio = it
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = "Tempo de cardio (minutos)",
                            enabled = !isLoading,
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                            placeholder = { Text("Ex: 30") }
                        )
                    }
                }
            }

            // Observações Gerais
            CaixaDeTexto(
                value = observacoesGerais,
                onValueChange = { observacoesGerais = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Observações gerais",
                enabled = !isLoading,
                maxLines = 3,
                placeholder = { Text("Observações sobre o treino...") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botão Cancelar
                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("Cancelar")
                }

                // Botão Salvar
                Button(
                    onClick = {
                        if (diaSemana.isBlank() || exercicios.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Preencha o dia da semana e adicione pelo menos um exercício."
                                )
                            }
                        } else {
                            isLoading = true

                            scope.launch {
                                // Aqui você salvaria no banco de dados
                                val treino = Treino(
                                    diaSemana = diaSemana,
                                    exercicios = exercicios,
                                    temCardio = temCardio,
                                    tempoCardio = tempoCardio,
                                    observacoesGerais = observacoesGerais
                                )

                                // Simulação de salvamento
                                try {
                                    // TODO: Implementar salvamento no banco de dados
                                    // saveTreino(treino)

                                    snackbarHostState.showSnackbar("Treino criado com sucesso!")
                                    navController.popBackStack()
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Erro ao criar treino: ${e.message}")
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading && diaSemana.isNotBlank() && exercicios.isNotEmpty()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Salvando...")
                    } else {
                        Text("Salvar Treino")
                    }
                }
            }
        }
    }
}

@Composable
fun ExercicioCard(
    exercicio: Exercicio,
    onUpdate: (Exercicio) -> Unit,
    onDelete: () -> Unit,
    enabled: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header com botão de deletar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Exercício",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                IconButton(
                    onClick = onDelete,
                    enabled = enabled
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remover exercício",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nome do exercício
            CaixaDeTexto(
                value = exercicio.nome,
                onValueChange = { onUpdate(exercicio.copy(nome = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = "Nome do exercício *",
                enabled = enabled,
                placeholder = { Text("Ex: Supino reto") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Séries e repetições
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CaixaDeTexto(
                    value = exercicio.series,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() } && it.length <= 2) {
                            onUpdate(exercicio.copy(series = it))
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = "Séries *",
                    enabled = enabled,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    placeholder = { Text("Ex: 3") }
                )

                CaixaDeTexto(
                    value = exercicio.repeticoes,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() } && it.length <= 3) {
                            onUpdate(exercicio.copy(repeticoes = it))
                        }
                    },
                    modifier = Modifier.weight(1f),
                    label = "Repetições *",
                    enabled = enabled,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    placeholder = { Text("Ex: 12") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Observações do exercício
            CaixaDeTexto(
                value = exercicio.observacoes,
                onValueChange = { onUpdate(exercicio.copy(observacoes = it)) },
                modifier = Modifier.fillMaxWidth(),
                label = "Observações",
                enabled = enabled,
                placeholder = { Text("Ex: Peso moderado, descanso 60s") }
            )
        }
    }
}

// Função para simular o salvamento no banco de dados
// suspend fun saveTreino(treino: Treino) {
//     // Implemente aqui a lógica para salvar no seu banco de dados
//     // Exemplo com Room Database:
//     // database.treinoDao().insert(treino)
// }