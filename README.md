# Sistema de Gestão de Restaurante de Marmitas

## Descrição do Projeto

Este projeto visa desenvolver um sistema desktop para gerenciar um restaurante que oferece marmitas (quentinhas) e bebidas, com vendas disponíveis via delivery ou para retirada no local. O sistema proporciona uma interface prática e eficiente para realizar as principais operações do dia a dia do restaurante.

## Funcionalidades

1. **Cadastro de Produtos**:
   - Ofertas disponíveis: Marmita Completa, Marmita + Refrigerante, e Marmita + Suco.

2. **Cadastro de Clientes**:
   - Informações armazenadas: Nome, Data de Aniversário, WhatsApp, Endereço (Rua, Número, Bairro, Complemento).

3. **Registro de Pedidos Diários**:
   - Detalhes do pedido: Data, Cliente, Ordem de Chegada, Forma de Entrega, Forma de Pagamento, Confirmação de Entrega.

4. **Impressão de Pedidos**:
   - Impressão de dados relevantes do pedido para auxiliar o motoboy durante a entrega.

5. **Integração com Pagamento via PIX** (opcional):
   - Possibilidade de integração com um player de pagamento para aceitar PIX como método de pagamento.

6. **Resumo Diário dos Pedidos**:
   - Exibição de um resumo diário, detalhando todos os pedidos realizados.

## Requisitos Técnicos

- **Interface**: Aplicação desktop sem complexidades visuais (sem "firulas").
- **Banco de Dados**: Banco de dados relacional, com suporte para PostgreSQL ou armazenamento em arquivo utilizando H2.

## Funcionalidades Não Necessárias

Para manter o sistema simples e eficiente, as seguintes tecnologias e arquiteturas foram descartadas do escopo deste projeto:
- Microsserviços
- REST API
- Docker
- Mensageria
- Aplicação Web

---

## Registro de Desenvolvimento

### Dia 05/11 - Etapa 1 Concluída

1. **Banco de Dados**:
   - Configurado no **pgAdmin4** e nomeado como `rest`.
   - Criadas as tabelas:
     - **Cliente**
     - **Pedido**
     - **Produto**
   - Foco inicial na tabela **Produto**, com as seguintes colunas:
     - `id`
     - `nome`
     - `preço`
     - `tipo`
   - Optou-se por criar a tabela diretamente por comandos no PostgreSQL e configurar a aplicação para listar os produtos ao ser executada.

2. **Implementação das Classes e CRUD**:
   - Criadas as classes Java:
     - **Produto.java**: Classe de modelo para produtos.
     - **ProdutoDAO.java**: Data Access Object para realizar operações de CRUD na tabela Produto.
     - **TesteCRUD.java**: Classe de teste para executar operações de Create, Read, Update e Delete.
   
Este progresso inicial estabeleceu a estrutura do banco de dados e implementou as operações CRUD para o gerenciamento de produtos, permitindo que o sistema comece a manipular dados reais do restaurante.
