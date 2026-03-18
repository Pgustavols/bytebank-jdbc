# 🏦 ByteBank JDBC

Projeto de simulação de um sistema bancário desenvolvido em **Java** com acesso a banco de dados via **JDBC** (Java Database Connectivity). Iniciado como parte dos estudos na plataforma [Alura](https://www.alura.com.br/) e evoluído com a adição de uma **API REST** com Spring Boot e um **frontend moderno** em React.

---

## 📋 Sobre o Projeto

O ByteBank é uma aplicação full stack que simula operações bancárias básicas, como cadastro de contas e movimentações financeiras. O backend utiliza JDBC puro para persistência de dados e expõe uma API REST via Spring Boot. O frontend foi desenvolvido em React + TypeScript + Tailwind CSS e consome essa API.

> ⚠️ O backend está hospedado no Railway com plano gratuito (500h/mês). Caso o serviço esteja inativo, siga as instruções abaixo para rodar localmente.

---

## 🌐 Deploy

| Serviço | URL |
|---------|-----|
| **Frontend** | [bytebank-jdbc.vercel.app](https://bytebank-jdbc.vercel.app) |
| **Backend** | Railway (PostgreSQL) |

---

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17** — Linguagem principal
- **Spring Boot 3** — API REST
- **JDBC** — Conexão e manipulação do banco de dados
- **HikariCP (Singleton)** — Pool de conexões de alta performance
- **Maven** — Gerenciamento de dependências e build
- **PostgreSQL** — Banco de dados relacional


### Frontend
- **React 19** — Interface do usuário
- **TypeScript** — Tipagem estática
- **Tailwind CSS v3** — Estilização
- **Vite** — Bundler e servidor de desenvolvimento
- **Framer Motion** — Animações
- **react-hot-toast** — Notificações toast

### Deploy
- **Railway** — Hospedagem do backend e banco de dados PostgreSQL
- **Vercel** — Hospedagem do frontend

---

## 📁 Estrutura do Projeto
```
bytebank-jdbc/
├── src/
│   └── main/
│       └── java/
│           └── br/com/alura/bytebank/
│               ├── domain/
│               │   ├── cliente/             # Entidades e DAOs relacionados ao cliente
│               │   └── conta/               # Entidades e DAOs relacionados à conta
│               ├── BytebankApplication.java # Classe principal (Spring Boot)
│               ├── ContaController.java     # Controller REST
│               └── ConnectionFactory.java   # Configuração do pool de conexões (HikariCP)
├── frontend/
│   ├── src/
│   │   ├── components/                      # Componentes React
│   │   │   ├── cards/                       # Cards de resumo e conta
│   │   │   ├── modal/                       # Modais de operações
│   │   │   ├── header/                      # Cabeçalho
│   │   │   └── listaContas/                 # Lista de contas
│   │   ├── pages/                           # Páginas da aplicação
│   │   ├── services/                        # Chamadas à API
│   │   └── types/                           # Tipagens TypeScript
│   ├── package.json
│   └── vite.config.ts
├── Procfile                                 # Configuração de deploy no Railway
├── config.properties.example               # Exemplo de configuração do banco
├── pom.xml
└── README.md
```

---

## ⚙️ Como Executar Localmente

### Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL
- Node.js 18+

### 1. Clone o repositório
```bash
git clone https://github.com/TallesDiniz/bytebank-jdbc.git
cd bytebank-jdbc
```

### 2. Configure o banco de dados

No PostgreSQL, crie o banco e a tabela:
```sql
CREATE DATABASE bytebank;

\c bytebank;

CREATE TABLE conta (
    numero INT PRIMARY KEY,
    saldo DECIMAL(10,2) DEFAULT 0,
    cliente_nome VARCHAR(100) NOT NULL,
    cliente_cpf VARCHAR(14) NOT NULL,
    cliente_email VARCHAR(100) NOT NULL
);
```

### 3. Configure a conexão

Crie o arquivo `config.properties` na raiz do projeto baseado no `config.properties.example`:
```properties
db.url=jdbc:postgresql://localhost:5432/bytebank
db.user=postgres
db.password=SUA_SENHA_AQUI
```

### 4. Execute o backend
```bash
mvn spring-boot:run
```

O servidor vai subir em `http://localhost:8080`.

### 5. Execute o frontend
```bash
cd frontend
npm install
npm run dev
```

O frontend vai subir em `http://localhost:5173`.

### 5. Execute o frontend
```bash
cd frontend
npm install
npm run dev
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/contas` | Lista todas as contas |
| POST | `/contas` | Abre uma nova conta |
| DELETE | `/contas/{numero}` | Encerra uma conta |
| POST | `/contas/{numero}/depositar` | Realiza um depósito |
| POST | `/contas/{numero}/sacar` | Realiza um saque |
| POST | `/contas/transferir` | Realiza uma transferência |

### Exemplo de body para abrir conta
```json
{
  "numero": 12345,
  "dadosCliente": {
    "nome": "João Silva",
    "cpf": "12345678901",
    "email": "joao@email.com"
  }
}
```

---

## 🔧 Funcionalidades

- [x] Cadastro de contas bancárias
- [x] Listagem de contas com resumo de saldo total
- [x] Depósito e saque
- [x] Transferência entre contas
- [x] Encerramento de conta com confirmação
- [x] Persistência com JDBC
- [x] API REST com Spring Boot
- [x] Frontend em React + TypeScript
- [x] Animações com Framer Motion
- [x] Tratamento de erros e notificações toast
- [x] Layout responsivo para mobile
- [x] Deploy em produção (Railway + Vercel)

---

## 📚 Aprendizados

Este projeto aborda os seguintes conceitos:

- Conexão com banco de dados utilizando `DriverManager`
- Gerenciamento de pool de conexões com **HikariCP** no padrão **Singleton**
- Uso de `PreparedStatement` para queries parametrizadas
- Padrão **DAO** (Data Access Object) para separação de responsabilidades
- Criação de API REST com **Spring Boot**
- Leitura de variáveis de ambiente para configuração em produção
- Integração entre frontend React e backend Java
- Tipagem com **TypeScript** e interfaces
- Componentização com **React**
- Animações com **Framer Motion**
- Tratamento de erros e feedback visual com **react-hot-toast**
- Layout responsivo com **Tailwind CSS**
- Deploy de aplicação full stack com **Railway** e **Vercel**
- Boas práticas de privacidade de dados (LGPD)
- Externalização de configurações com `config.properties`

---

## 👤 Autores

**Paulo Gustavo** — [@Pgustavols](https://github.com/Pgustavols) — Backend (Java + JDBC)

**Talles Diniz** — [@TallesDiniz](https://github.com/TallesDiniz) — Frontend (React + TypeScript) e deploy