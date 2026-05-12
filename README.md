# Gym Manager - Sistema de Gerenciamento de Alunos

Aplicativo mobile desenvolvido com **Jetpack Compose** e **Firebase** para administração de alunos em academias de musculação.  
Gerencie matrículas, treinos, pagamentos e evolução dos alunos de forma prática e eficiente.

## Funcionalidades

- **Autenticação** (login/cadastro) com Firebase Auth
- **Cadastro de alunos** com dados pessoais e fotos
- **Controle de matrículas** (ativa/inativa)
- **Registro de treinos** e fichas de exercícios
- **Gestão de pagamentos** mensais com status
- **Acompanhamento de evolução** (peso, medidas, IMC)
- **Notificações** sobre vencimentos e aniversários
- Interface moderna e responsiva com Material 3

## Tecnologias

- **Kotlin** (100%)
- **Jetpack Compose** (UI)
- **Firebase**:
  - Authentication (e-mail/senha)
  - Firestore (banco de dados)
  - Storage (armazenamento de fotos)
  - Cloud Messaging (notificações)
- **Arquitetura MVVM** com ViewModel e StateFlow
- **Injeção de dependência** manual (ou Hilt, se aplicável)

## Pré-requisitos

- Android Studio Hedgehog (ou superior)
- Dispositivo ou emulador com Android 5.0+ (API 21+)
- Conta no [Firebase Console](https://console.firebase.google.com/)

## Como executar o projeto

1. **Clone o repositório**

```bash
git clone https://github.com/victor-hugo-oliveira-dev/appgym.git
```
2. **Utilize Android Studio**
Baixe a IDE Android Studio e exculte o sistema, para persistencia de dados utilizei fireBase.
Mas é utilizavél liteSQL, alterando o banco de dados para o uso de tal BD.
