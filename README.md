# 🏦 ByteBank JDBC

Projeto de simulação de um sistema bancário desenvolvido em **Java** com acesso a banco de dados via **JDBC** (Java Database Connectivity). Criado como parte dos estudos na plataforma [Alura](https://www.alura.com.br/).

---

## 📋 Sobre o Projeto

O ByteBank é uma aplicação back-end que simula operações bancárias básicas, como cadastro de contas e movimentações financeiras, com persistência de dados utilizando JDBC puro — sem frameworks ORM como Hibernate ou JPA.

---

## 🚀 Tecnologias Utilizadas

- **Java** — Linguagem principal
- **JDBC** — Conexão e manipulação do banco de dados
- **HikariCP** — Pool de conexões de alta performance
- **Maven** — Gerenciamento de dependências e build
- **MySQL** — Banco de dados relacional

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
│               ├── BytebankApplication.java # Classe principal (main)
│               └── ConnectionFactory.java   # Configuração do pool de conexões (HikariCP)
├── config.properties                        # Configurações de conexão com o banco
├── pom.xml
└── README.md
```

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 11+
- Maven 3.6+
- Banco de dados (PostgreSQL ou MySQL) instalado e rodando

### 1. Clone o repositório

```bash
git clone https://github.com/Pgustavols/bytebank-jdbc.git
cd bytebank-jdbc
```

### 2. Configure o banco de dados

Crie o banco de dados e as tabelas necessárias. Exemplo para PostgreSQL:

```sql
CREATE DATABASE bytebank;

CREATE TABLE conta (
    id SERIAL PRIMARY KEY,
    numero INTEGER NOT NULL,
    titular VARCHAR(100) NOT NULL,
    saldo DECIMAL(10,2) NOT NULL
);
```

### 3. Configure a conexão

As credenciais do banco ficam no arquivo `config.properties` na raiz do projeto:

```properties
jdbc.url=jdbc:postgresql://localhost/bytebank
jdbc.username=seu_usuario
jdbc.password=sua_senha
```

O arquivo `ConnectionFactory.java` lê essas configurações e inicializa o pool de conexões com **HikariCP**:

```java
HikariConfig config = new HikariConfig("/config.properties");
HikariDataSource ds = new HikariDataSource(config);
```

### 4. Execute com Maven

```bash
mvn compile exec:java
```

---

## 🔧 Funcionalidades

- [x] Cadastro de contas bancárias
- [x] Listagem de contas
- [x] Depósito e saque
- [x] Encerramento de conta
- [x] Persistência com JDBC

---

## 📚 Aprendizados

Este projeto aborda os seguintes conceitos:

- Conexão com banco de dados utilizando `DriverManager`
- Gerenciamento de pool de conexões com **HikariCP**
- Uso de `PreparedStatement` para queries parametrizadas
- Padrão **DAO** (Data Access Object) para separação de responsabilidades
- Separação de domínio em pacotes (`cliente` e `conta`)
- Tratamento de regras de negócio com exceções customizadas (`RegraDeNegocioException`)
- Boas práticas de fechamento de recursos com `try-with-resources`
- Tratamento de exceções com `SQLException`
- Externalização de configurações com `config.properties`

---

## 👤 Autor

**Paulo Gustavo** — [@Pgustavols](https://github.com/Pgustavols)

---
