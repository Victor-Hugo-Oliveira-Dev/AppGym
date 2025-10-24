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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tcccaio.Componente.CaixaDeTexto
import com.example.tcccaio.DataClass.Aluno
import kotlinx.coroutines.launch

// Data class para representar um aluno

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavController) {

    // Estado do formulário
    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var proposito by remember { mutableStateOf("") }
    var diasTreino by remember { mutableStateOf<List<String>>(emptyList()) }

    // Estados para dropdowns
    var propositoExpanded by remember { mutableStateOf(false) }
    var diasExpanded by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Opções para os dropdowns
    val propositoOptions = listOf(
        "Emagrecimento",
        "Hipertrofia",
        "Condicionamento Físico",
        "Força",
        "Reabilitação",
        "Outros"
    )

    val diasSemanaOptions = listOf(
        "Segunda-feira",
        "Terça-feira",
        "Quarta-feira",
        "Quinta-feira",
        "Sexta-feira",
        "Sábado",
        "Domingo"
    )

    // Validação dos campos obrigatórios
    val isNomeValid = nome.isNotBlank()
    val isIdadeValid = idade.isNotBlank() && idade.toIntOrNull() != null
    val isPropositoValid = proposito.isNotBlank()

    val isFormValid = isNomeValid && isIdadeValid && isPropositoValid

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cadastrar Aluno") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xff1b2e3a),
                    titleContentColor = Color.White
                )
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
                text = "Dados do Aluno",
                style = MaterialTheme.typography.headlineSmall
            )

            // Campo Nome (obrigatório)
            CaixaDeTexto(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth(),
                label = "Nome completo *",
                enabled = !isLoading
            )
            if (showErrors && !isNomeValid) {
                Text(
                    text = "Nome é obrigatório",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Campo Idade (obrigatório)
            CaixaDeTexto(
                value = idade,
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && it.length <= 3) {
                        idade = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = "Idade *",
                enabled = !isLoading,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
            )
            if (showErrors && !isIdadeValid) {
                Text(
                    text = if (idade.isBlank()) "Idade é obrigatória" else "Idade inválida",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Campo Altura (opcional)
            CaixaDeTexto(
                value = altura,
                onValueChange = {
                    // Formato: 1.75
                    if (it.matches(Regex("^\\d{0,1}(\\.\\d{0,2})?$"))) {
                        altura = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = "Altura (metros)",
                enabled = !isLoading,
                placeholder = { Text("Ex: 1.75") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
            )

            // Campo Peso (opcional)
            CaixaDeTexto(
                value = peso,
                onValueChange = {
                    if (it.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
                        peso = it
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                label = "Peso (kg)",
                enabled = !isLoading,
                placeholder = { Text("Ex: 70.5") },
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
            )

            // Dropdown Propósito (obrigatório)
            Text(
                text = "Tipo do Treino *",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { propositoExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(
                        text = proposito.ifBlank { "Selecione o tipo" },
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir menu tipo de treino",
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = propositoExpanded,
                    onDismissRequest = { propositoExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    propositoOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                proposito = option
                                propositoExpanded = false
                            }
                        )
                    }
                }
            }
            if (showErrors && !isPropositoValid) {
                Text(
                    text = "Tipo de treino é obrigatório",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Seleção de Dias de Treino (opcional)
            Text(
                text = "Dias de Treino",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { diasExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(
                        text = when {
                            diasTreino.isEmpty() -> "Selecione os dias de treino"
                            diasTreino.size == 1 -> diasTreino.first()
                            else -> "${diasTreino.size} dias selecionados"
                        },
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Abrir menu dias",
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = diasExpanded,
                    onDismissRequest = { diasExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    diasSemanaOptions.forEach { dia ->
                        val isSelected = diasTreino.contains(dia)
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(dia)
                                    Spacer(modifier = Modifier.weight(1f))
                                    if (isSelected) {
                                        Text("✓")
                                    }
                                }
                            },
                            onClick = {
                                diasTreino = if (isSelected) {
                                    diasTreino - dia
                                } else {
                                    diasTreino + dia
                                }
                            }
                        )
                    }
                }
            }

            // Mostrar dias selecionados
            if (diasTreino.isNotEmpty()) {
                Column {
                    Text(
                        text = "Dias selecionados:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    diasTreino.forEach { dia ->
                        Text(
                            text = "• $dia",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        if (isFormValid) {
                            showErrors = false
                            isLoading = true

                            scope.launch {
                                // Aqui você salvaria no banco de dados
                                val aluno = Aluno(
                                    nome = nome,
                                    idade = idade,
                                    altura = altura,
                                    peso = peso,
                                    proposito = proposito,
                                    diasTreino = diasTreino,
                                    treinadorId = "treinador_atual" // Substitua pelo ID real do treinador
                                )

                                // Simulação de salvamento
                                try {
                                    // TODO: Implementar salvamento no banco de dados
                                    // saveAluno(aluno)

                                    snackbarHostState.showSnackbar("Aluno cadastrado com sucesso!")
                                    navController.popBackStack()
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Erro ao cadastrar aluno: ${e.message}")
                                } finally {
                                    isLoading = false
                                }
                            }
                        } else {
                            showErrors = true
                            scope.launch {
                                snackbarHostState.showSnackbar("Preencha os campos obrigatórios")
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
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
                        Text("Salvar")
                    }
                }
            }
        }
    }
}

// Função para simular o salvamento no banco de dados (substitua pela sua implementação real)
// suspend fun saveAluno(aluno: Aluno) {
//     // Implemente aqui a lógica para salvar no seu banco de dados
//     // Exemplo com Room Database:
//     // database.alunoDao().insert(aluno)
// }