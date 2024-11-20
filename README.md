## **Sistema de Gestão de Restaurante de Marmitas**

---

### **Descrição do Projeto**
Este projeto visa desenvolver um sistema desktop para gerenciar um restaurante que oferece marmitas (quentinhas) e bebidas, com vendas disponíveis via delivery ou para retirada no local. O sistema proporciona uma interface prática e eficiente para realizar as principais operações do dia a dia do restaurante.

---

### **Funcionalidades**

1. **Cadastro de Produtos**
   - Ofertas disponíveis: Marmita Completa, Marmita + Refrigerante, e Marmita + Suco.
   - Validação para preço (não pode ser negativo).
   - Descrição personalizada com base no nome e tipo do produto.

2. **Cadastro de Clientes**
   - Informações armazenadas: Nome, Data de Aniversário, WhatsApp (com validação de formato), Endereço (Rua, Número, Bairro, Complemento).

3. **Registro de Pedidos Diários**
   - Detalhes do pedido: Data e hora, Cliente, Produtos incluídos no pedido, Forma de Entrega, Forma de Pagamento, Confirmação de Entrega.

4. **Impressão de Pedidos**
   - Dados relevantes para auxiliar o motoboy durante a entrega.

5. **Integração com Pagamento via PIX** (opcional)
   - Possibilidade de integração com um player de pagamento para aceitar PIX como método de pagamento.

6. **Resumo Diário dos Pedidos**
   - Exibição de um resumo detalhando todos os pedidos realizados no dia.

---

### **Requisitos Técnicos**
- **Interface:** Aplicação desktop simples.
- **Banco de Dados:** Relacional, com suporte para PostgreSQL ou armazenamento em arquivo (H2).

---

### **Funcionalidades Não Necessárias**
Para manter o sistema simples e eficiente, as seguintes tecnologias e arquiteturas foram descartadas:
- Microsserviços
- REST API
- Docker
- Mensageria
- Aplicação Web

---

### **Registro de Desenvolvimento**

#### **Dia 05/11 - Etapa 1 Concluída**
1. **Banco de Dados**
   - Criado no `pgAdmin4`, nomeado como `rest`.
   - Estruturadas as tabelas:
     - **Cliente**
     - **Pedido**
     - **Produto**
   - Tabela **Produto** configurada com as colunas:
     - `id`
     - `nome`
     - `preço`
     - `tipo`
   - Conexão com o PostgreSQL configurada para listar produtos automaticamente.

2. **Implementação das Classes e CRUD**
   - **Produto.java:** Classe de modelo.
   - **ProdutoDAO.java:** Operações de CRUD para a tabela Produto.
   - **TesteCRUD.java:** Testes de `Create`, `Read`, `Update` e `Delete` com foco inicial nos produtos.

---

#### **Dia 08/11 - Novas Implementações e Alterações**
1. **Classes e Métodos Novos**
   - **Cliente.java:** Modelo para dados do cliente (nome, aniversário, WhatsApp e endereço).
   - **Pedido.java:** Modelo para pedidos, com:
     - Data/hora do pedido.
     - Cliente associado.
     - Lista de produtos.
     - Forma de entrega/pagamento.
     - Confirmação de entrega.
     - Métodos para cálculo do total e geração de resumo do pedido.
   - **ClienteDAO.java:** Operações CRUD para a tabela Cliente.
   - **PedidoDAO.java:** Operações CRUD para pedidos, incluindo associação entre produtos e pedidos.

2. **Atualizações no CRUD**
   - **Produto.java:**
     - Validação para preços não negativos.
     - Método `toString` para exibição textual.
   - **ProdutoDAO.java:**
     - Adição de métodos para busca por nome.
   - **TesteCRUD.java:**
     - Integração com gerenciamento de clientes e pedidos:
       - Cadastro
       - Edição
       - Exclusão
       - Confirmação de entrega.

---

#### **Dia 17/11 - Integração com Front-End**
1. **Painéis de Clientes e Produtos**
   - Painel para **clientes**:
     - Exibição de dados em tabela (com ID, nome, aniversário, WhatsApp, endereço).
     - Campos para adicionar clientes.
     - Função para remover clientes da tabela.
   - Painel para **produtos**:
     - Exibição de dados em tabela (com ID, nome, preço, tipo).
     - Campos para adicionar produtos.
     - Função para remover produtos.

2. **Painel de Pedidos**
   - Integração com dados de clientes e produtos.
   - Tabelas separadas para:
     - **Pedidos Delivery**
     - **Pedidos para Retirada**
   - Funcionalidades:
     - Adicionar pedido com validações:
       - Cliente selecionado.
       - Produtos não podem estar vazios.
     - Remover pedidos.
     - Exibir detalhes do cliente (nome, telefone, endereço).

3. **Correções e Melhorias**
   - **Atualização Automática:**
     - Clientes e produtos listados ao abrir a aplicação.
     - Pedidos carregados automaticamente.
   - Exibição de complemento do endereço no Delivery.
   - Ajustes em layouts e botões.

---

#### **Dia 18/11 - Funcionalidades Avançadas**
1. **Detalhes do Pedido**
   - Exibição completa dos dados do cliente nos pedidos delivery:
     - Nome
     - WhatsApp
     - Endereço completo (incluindo complemento, se houver).

2. **Validação ao Criar Pedido**
   - Bloqueio caso:
     - Cliente não seja selecionado.
     - Nenhum produto tenha sido adicionado.

3. **Correções no PedidoDAO**
   - Ajuste no mapeamento da tabela `Pedido_Produto`:
     - Correção no relacionamento com `Produto`.

---

#### **Dia 19/11 - Ajustes Finais e Refinamentos**
1. **Carregamento Inicial**
   - Ajustado para carregar pedidos Delivery e Retirada automaticamente ao abrir o sistema.

2. **Separação de Lógicas**
   - Melhor segregação entre lógicas de exibição e banco de dados.

3. **Erros Corrigidos**
   - Erros de referência a métodos não definidos corrigidos:
     - `atualizarTabelaClientes()`
     - `atualizarTabelaProdutos()`
     - `removerCliente()` e `removerProduto()` implementados.

4. **Polimento na Interface**
   - Layout ajustado para melhor usabilidade.
   - Botões e tabelas reorganizados para maior clareza.

---

