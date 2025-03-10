# Projeto Menu - POO (2º Período) 🪄

Este projeto foi desenvolvido para a disciplina de Programação Orientada a Objetos (POO) no 2º período do curso de Análise e Desenvolvimento de Sistemas. O sistema tem como objetivo gerenciar um cardápio digital, permitindo múltiplos cadastros com gerenciamento de usuários, produtos, categorias, clientes e pedidos.

# Participantes
- Jéfter Lucas @jefter-dev
- Jorge Allan @Allan177
- José Davi @DaviJ13

## Funcionalidades

- **CRUD Usuários|Estabelecimentos** (Gerenciar Usuários|Estabelecimetos).
- **CRUD Clientes** (Gerenciar clientes do estabelecimento).
- **CRUD Categorias** (Gerenciar categorias de produtos).
- **CRUD Produtos** (Gerenciar produtos do cardápio).
- **CRUD Adicionais de Produtos**.
- **CRUD Pedidos** (Gerenciar pedidos dos clientes).
- **Login de Usuário|Estabelecimento**.
- **Login de Clientes**.
- **Efetuar Pedido**.
- **Alterar status do pedido**.
- **Histórico de Pedidos do Cliente**.
- **Pesquisa de Produtos do Usuário**.
- **Pegar Produtos com Categorias**.
- **Pegar Adicionais do Produto pelo ID**.
- **Filtrar Produto pelo Nome**.
- **Pegar Dados do Usuário**.
- **Pegar Pedidos pelo ID do Cliente**.
- **Obter Categorias com Produtos do Estabelecimento**.
- **Obter Adicionais do Produto**.
- **Obter Dados do Estabelecimento**.
- **Obter Dados do Cliente**.
- **Obter Dados do Carrinho do Cliente**.
- **Obter Pedidos do Cliente**.
  
## Tecnologias Utilizadas
- **Java** (Programação Orientada a Objetos)
- **Java Swing** (Interface gráfica)
- **JDBC** (Integração com banco de dados)
- **PostgresSQL** (Persistência de dados)
- **Padrões de Projeto** (MVC - Model, View, Controller)

## Como Executar o Projeto

1. **Clonar o repositório**:
   ```sh
   git clone https://github.com/jefter-dev/menu.git
   ```
2. **Navegar até o diretório do projeto**:
   ```sh
   cd menu
   ```
3. **Compilar o projeto**:
   ```sh
   mvn clean install -DskipTests
   ```
4. **Executar a aplicação**:
   ```sh
   java -jar .\target\app-0.0.1-SNAPSHOT.jar
   ```

## Estrutura do Projeto 📂

O projeto segue a arquitetura MVC (Model-View-Controller) e está estruturado da seguinte forma:

```
menu/
├── .idea/
├── .mvn/
├── .vscode/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── br.edu.ifpb.poo.menu/
│   │   │   │   ├── configuration/
│   │   │   │   ├── controller/
│   │   │   │   ├── exception/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   ├── MenuApplication
│   ├── resources/
│   ├── test/
├── target/
├── uploads/
│   ├── product/
│   ├── user/
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
```

## Principais Rotas da API para testes [CONTROLLER] 📍

### Adicionais
- `GET /additional/{id}` - Obtém um adicional pelo ID
- `POST /additional` - Cria um novo adicional
- `PUT /additional/{additionalId}` - Atualiza um adicional pelo ID
- `DELETE /additional/{additionalId}` - Remove um adicional pelo ID

### Categorias
- `GET /category/{id}` - Obtém uma categoria pelo ID
- `GET /category` - Lista todas as categorias
- `POST /category` - Cria uma nova categoria
- `PUT /category/{categoryId}` - Atualiza uma categoria pelo ID
- `DELETE /category/{categoryId}` - Remove uma categoria pelo ID

### Cliente
- `GET /client/{id}` - Obtém um cliente pelo ID
- `POST /client` - Cria um novo cliente
- `PUT /client/{clientId}` - Atualiza um cliente pelo ID
- `DELETE /client/{clientId}` - Remove um cliente pelo ID

### Pedido
- `GET /order/{id}` - Obtém um pedido pelo ID
- `GET /order` - Lista todos os pedidos
- `POST /order` - Cria um novo pedido
- `PUT /order/{orderId}` - Atualiza um pedido pelo ID
- `DELETE /order/{orderId}` - Remove um pedido pelo ID

### Produto
- `GET /product/{id}` - Obtém um produto pelo ID
- `GET /product` - Lista todos os produtos
- `POST /product` - Cria um novo produto
- `PUT /product/{productId}` - Atualiza um produto pelo ID
- `DELETE /product/{productId}` - Remove um produto pelo ID

### Usuário
- `GET /user/{id}` - Obtém um usuário pelo ID
- `GET /user` - Lista todos os usuários
- `POST /user` - Cria um novo usuário
- `PUT /user/{userId}` - Atualiza um usuário pelo ID
- `DELETE /user/{userId}` - Remove um usuário pelo ID

