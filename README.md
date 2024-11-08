# Sistema de Gestão de Restaurante de Marmitas

## Descrição do Projeto
Este projeto visa desenvolver um sistema desktop para gerenciar um restaurante que oferece marmitas (quentinhas) e bebidas, com vendas disponíveis via delivery ou para retirada no local. O sistema proporciona uma interface prática e eficiente para realizar as principais operações do dia a dia do restaurante.

## Funcionalidades

### Cadastro de Produtos
- Ofertas disponíveis: Marmita Completa, Marmita + Refrigerante, e Marmita + Suco.
- A classe `Produto` inclui validação para o preço (não pode ser negativo) e uma descrição personalizada com base no nome e tipo do produto.

### Cadastro de Clientes
- Informações armazenadas: Nome, Data de Aniversário, WhatsApp (validação de formato), Endereço (Rua, Número, Bairro, Complemento).

### Registro de Pedidos Diários
- Detalhes do pedido: Data e hora, Cliente, Produtos incluídos no pedido, Forma de Entrega, Forma de Pagamento, Confirmação de Entrega.

### Impressão de Pedidos
- Impressão de dados relevantes do pedido para auxiliar o motoboy durante a entrega.

### Integração com Pagamento via PIX (opcional)
- Possibilidade de integração com um player de pagamento para aceitar PIX como método de pagamento.

### Resumo Diário dos Pedidos
- Exibição de um resumo diário, detalhando todos os pedidos realizados.

## Requisitos Técnicos
- **Interface**: Aplicação desktop simples, sem complexidades visuais.
- **Banco de Dados**: Relacional, com suporte para PostgreSQL ou armazenamento em arquivo utilizando H2.

## Funcionalidades Não Necessárias
Para manter o sistema simples e eficiente, as seguintes tecnologias e arquiteturas foram descartadas do escopo deste projeto:

- Microsserviços
- REST API
- Docker
- Mensageria
- Aplicação Web

## Registro de Desenvolvimento

### Dia 05/11 - Etapa 1 Concluída
#### Banco de Dados
- Configurado no pgAdmin4 e nomeado como `rest`.
- Criadas as tabelas `Cliente`, `Pedido`, `Produto`.
- Foco inicial na tabela `Produto`, com as colunas:
  - `id`
  - `nome`
  - `preço`
  - `tipo`
- Optou-se por criar a tabela diretamente por comandos no PostgreSQL e configurar a aplicação para listar os produtos ao ser executada.

#### Implementação das Classes e CRUD
- **Produto.java**: Classe de modelo para produtos.
- **ProdutoDAO.java**: Data Access Object para realizar operações de CRUD na tabela Produto.
- **TesteCRUD.java**: Classe de teste para executar operações de Create, Read, Update e Delete.

### Dia 08/11 - Novas Implementações e Alterações
#### Classes e Métodos Novos
- **Cliente.java**: Classe de modelo para armazenar informações do cliente, incluindo `nome`, `aniversário`, `whatsapp` e endereço.
- **Pedido.java**: Classe de modelo para pedidos, com atributos como data/hora do pedido, cliente, lista de produtos, forma de entrega, forma de pagamento e confirmação de entrega. Inclui métodos para calcular o total do pedido e gerar um resumo.
- **ClienteDAO.java**: Data Access Object para realizar operações de CRUD na tabela Cliente.
- **PedidoDAO.java**: Data Access Object para realizar CRUD de pedidos, incluindo associação dos produtos ao pedido.

#### Atualizações no CRUD
- **Produto.java**: Adicionado validador de preço para evitar valores negativos e método `toString` para representação textual.
- **ProdutoDAO.java**: Atualizado para incluir novos métodos de busca por nome.
- **TesteCRUD.java**: Atualizado para incluir operações de gerenciamento de clientes e pedidos, como cadastro, edição, exclusão, e confirmação de entrega.

Essas mudanças ampliam a funcionalidade do sistema para gerenciar produtos, clientes e pedidos de forma mais completa, permitindo manipulação de dados reais e execução das operações essenciais do restaurante.
