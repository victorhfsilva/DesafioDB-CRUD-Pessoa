# Readme

Este desafio foi parte do treinamento oferecido pela DB no Start DB 2023. 

**Desafio:** [Crud - Pessoa 3](./CRUD%20-%20Pessoa%203.pdf)

**Data de Conclusão:** 14/03/2024

## Sobre

O projeto consistia em um CRUD da entidade Pessoa com relacionamento um para muitos com a entidade Endereço.

Com isto em mente foram criados endpoints para ler todos os dados das entidades utilizando o Spring Data Rest. Para acessar estes endpoints é necessário ter permissões administrativas no sistema.

Além disso, foram criados endpoints para que os usuários sem permissões administrativas possam executar tarefas de ler, atualizar, ativar e desativar seus próprios dados.

Também foram criados endpoints para que usuários administrativos possam atualizar, ativar, desativar e excluir os dados de qualquer usuário.

Também existe um endpoint para login e para registrar novos usuários. Para criar novos administradores é necessário antes fazer login com a conta administrativa padrão:

- **Cpf:** admin
- **Senha:** admin

Recomenda-se que esta conta seja atualizada após feito login.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Spring Data Rest
- Spring Web
- Spring Security
- Lombok
- Validation
- Passay
- PostgreSQL
- Docker
- Rest Assured

## Variáveis de Ambiente

Para reproduzir as configurações deste projeto é necessário criar um arquivo .env dentro da pasta `crud-pessoa-back-end` com as seguintes variáveis de ambiente:

```
DEV_DB_URL=jdbc:postgresql://localhost:5432/<banco de dados de desenvolvimento>
TEST_DB_URL=jdbc:postgresql://localhost:5432/<banco de dados de teste>
POSTGRES_USER=<usuário do banco de dados>
POSTGRES_PWD=<senha do banco de dados>
```

Lembre-se de criar os bancos de dados previamente e substituir as configurações entre chaves pelas específicas ao seu ambiente de desenvolvimento.

## Docker

Já para criar um container da aplicação é necessário utilizar as seguintes configurações:

- .env dentro da raiz do repositório:

```
POSTGRES_USER=<usuário do banco de dados do container>
POSTGRES_PASSWORD=<senha do banco de dados do container>
POSTGRES_DEV_DB=<banco de dados de desenvolvimento>
```

- .env dentro da pasta `crud-pessoa-back-end`:

```
DEV_DB_URL=jdbc:postgresql://crud-pessoa-dev-db:5432/<banco de dados de desenvolvimento>
POSTGRES_USER=<senha do banco de dados do container>
POSTGRES_PWD=<senha do banco de dados do container>
```

- Em seguida basta subir os containers com o seguinte comando na pasta raiz do repositório:

```bash
docker-compose up
```

## Débitos Técnicos

- Algumas exceções específicas estão sendo traduzidas como Internal Server Error pelo Exception Handler Advicer.
- Utilizar variáveis de ambiente para declarar o segredo dos tokens JWT.
