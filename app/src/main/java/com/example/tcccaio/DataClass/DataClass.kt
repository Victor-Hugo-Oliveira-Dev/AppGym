package com.example.tcccaio.DataClass

data class Aluno(
    val id: String = "",
    val nome: String = "",
    val idade: String = "",
    val altura: String = "",
    val peso: String = "",
    val proposito: String = "",
    val diasTreino: List<String> = emptyList(),
    val treinadorId: String = ""
)

data class Treino(
    val id: String = "",
    val alunoId: String = "",
    val diaSemana: String = "",
    val exercicios: List<Exercicio> = emptyList(),
    val temCardio: Boolean = false,
    val tempoCardio: String = "",
    val observacoesGerais: String = ""
)

data class Exercicio(
    val nome: String = "",
    val series: String = "",
    val repeticoes: String = "",
    val observacoes: String = ""
)
