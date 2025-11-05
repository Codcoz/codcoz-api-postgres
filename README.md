# Codcoz API SQL (PostgreSQL)

API REST construída com **Spring Boot 3** e **Java 21**, conectada a um banco de dados **PostgreSQL** e hospedada na **Koyeb**. Esta API é a contraparte SQL da arquitetura Codcoz, gerenciando a persistência de dados relacionais.

A API serve como backend para as seguintes aplicações:
- Aplicativo mobile: [codcoz-mobile](https://github.com/Codcoz/codcoz-mobile )
- Aplicação web: [codcoz-react](https://github.com/Codcoz/codcoz-react )

---

## Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Spring Web**: Para a construção de endpoints RESTful.
- **Spring Data JPA**: Para o mapeamento objeto-relacional e persistência em banco de dados SQL.
- **PostgreSQL Driver**: Driver JDBC para conectar a aplicação ao banco de dados PostgreSQL.
- **Spring Boot Actuator**: Para monitoramento e health checks (`/health`).
- **Spring Validation**: Para validação dos dados de entrada (DTOs).
- **SpringDoc OpenAPI (Swagger UI)**: Para geração de documentação interativa da API.
- **Lombok**: Para reduzir a verbosidade do código Java.
- **Spring Dotenv**: Para gerenciamento de variáveis de ambiente.
- **Docker**: Para criar imagens de contêiner da aplicação.

---

## Endpoints e Documentação

A documentação completa dos endpoints está disponível e é gerada automaticamente via **Swagger UI**.

🔗 **Swagger UI:** [https://codcoz-api-postgres.koyeb.app/swagger-ui/index.html](https://codcoz-api-postgres.koyeb.app/swagger-ui/index.html )  
🌍 **Base URL:** [https://codcoz-api-postgres.koyeb.app](https://codcoz-api-postgres.koyeb.app )

---

## Requisitos

- **Java 21+**
- **Maven 3.9+**
- Banco de dados **PostgreSQL** (local ou em nuvem).

---

## Estrutura do Projeto

```text
.
├─ .mvn/                      # Wrapper do Maven
├─ repository/                # Scripts de inicialização do banco (se houver)
├─ src/
│  └─ main/
│     └─ java/
│        └─ com/codcozapipostgres/
│           ├─ config/        # Configurações do Spring (ex: segurança)
│           ├─ controller/    # Controladores REST
│           ├─ dto/           # Data Transfer Objects
│           ├─ exception/     # Tratamento de exceções globais
│           ├─ model/         # Entidades JPA
│           ├─ projection/    # Projeções para consultas otimizadas
│           ├─ repository/    # Interfaces do Spring Data JPA
│           ├─ service/       # Lógica de negócio
│           └─ CodcozApiPostgresApplication.java # Classe principal
├─ .gitignore                 # Arquivos ignorados pelo Git
├─ Dockerfile                 # Definição da imagem Docker
├─ LICENSE                    # Licença do projeto
├─ mvnw                       # Executável do Maven Wrapper
├─ pom.xml                    # Dependências e build do projeto
├─ PULL_REQUEST_TEMPLATE.md   # Template para Pull Requests
└─ README.md                  # Este arquivo
```

---


## Licença

Este projeto é distribuído sob a licença MIT.
Consulte o arquivo LICENSE (se aplicável) para mais detalhes.

---

## Deploy

Hospedado na Render
Fluxo padrão:
1. Gerar o build com Maven:

      ```mvn clean package```

2. A Render executa o .jar gerado automaticamente após o push.

3. Endpoint público:
	👉 https://codcoz-api-mongo-eemr.onrender.com

Também é compatível com outros provedores (Railway, Fly.io, AWS, etc).
