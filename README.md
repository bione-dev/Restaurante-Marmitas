---

## **Sistema de Gestão de Restaurante de Marmitas**

---

### **Descrição do Projeto**
Este projeto visa desenvolver um sistema desktop para gerenciar um restaurante que oferece marmitas (quentinhas) e bebidas, com vendas disponíveis via delivery ou para retirada no local. O sistema proporciona uma interface prática e eficiente para realizar as principais operações do dia a dia do restaurante. Além disso, o sistema é flexível e pode ser utilizado tanto com uma interface gráfica intuitiva quanto sem interface gráfica, permitindo integração com outros sistemas ou uso em ambientes automatizados.

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

7. **Confirmação de Pedidos**
   - Funcionalidade para confirmar pedidos diretamente na interface gráfica, marcando-os como entregues.

---

### **Requisitos Técnicos**
- **Interface:** Aplicação desktop simples desenvolvida com Java Swing.
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

#### **Dia 08/12 - Adição do Botão "Confirmar Pedido", Correção no Carregamento de Dados e Adaptações Backend-Frontend**
1. **Adição do Botão "Confirmar Pedido"**
   - Implementado um novo botão denominado **"Confirmar Pedido"** na aba de **Pedidos**.
   - O botão permite marcar um pedido como confirmado diretamente na interface gráfica.
   - Utiliza o método existente `confirmarEntrega(int id)` da classe `PedidoDAO` para atualizar o status de confirmação no banco de dados.

2. **Correção no Carregamento de Dados na Aba de Pedidos**
   - Garantido que os pedidos existentes sejam carregados automaticamente ao iniciar a aplicação.
   - O método `atualizarTabelaPedidos` é chamado durante a criação do painel de pedidos, assegurando que todas as informações sejam exibidas corretamente sem a necessidade de adicionar um novo pedido para visualizar os existentes.

3. **Adaptações para Backend e Frontend**
   - **Sincronização de Operações:** 
     - Ajustes feitos para garantir que as operações realizadas no frontend (como adicionar, remover e confirmar pedidos) reflitam imediatamente no backend, mantendo os dados consistentes.
   - **Atualização Automática das Tabelas:**
     - Implementação de chamadas ao método `atualizarTabelaPedidos` após cada operação de modificação para garantir que as tabelas exibidas no frontend estejam sempre atualizadas com o estado atual do banco de dados.
   - **Feedback ao Usuário:**
     - Utilização de `JOptionPane` para fornecer feedback imediato sobre o sucesso ou falha das operações, melhorando a interação entre usuário e sistema.
   - **Melhorias na Interface de Usuário:**
     - Ajustes nos layouts e posicionamento de componentes para acomodar novas funcionalidades e garantir uma experiência de usuário fluida e intuitiva.

4. **Renomeação de Variáveis de Exceção**
   - Todas as variáveis de exceção nos blocos `catch` foram renomeadas para `ex` para evitar conflitos com os parâmetros `e` das expressões lambda dos `ActionListener`.
   - Garantido que não haja duplicação de parâmetros, melhorando a clareza e evitando possíveis erros.

5. **Feedback e Atualização das Tabelas**
   - Após confirmar um pedido, as tabelas de **Delivery** e **Retirada** são atualizadas automaticamente para refletir o novo status de confirmação.
   - Utilização de `JOptionPane` para fornecer feedback imediato ao usuário sobre o sucesso ou falha das operações realizadas.

6. **Melhorias na Interface de Usuário**
   - Ajustes nos layouts para acomodar o novo botão de confirmação.
   - Garantido que todas as operações (adicionar, remover, confirmar) atualizem as tabelas de forma consistente e imediata.

---

### **Modos de Uso**

O sistema foi desenvolvido para ser flexível e pode ser utilizado de duas maneiras distintas:

1. **Com Interface Gráfica (Frontend):**
   - **Descrição:** Utiliza a aplicação desktop desenvolvida com Java Swing, proporcionando uma interface amigável para interação direta com o sistema.
   - **Uso:** Ideal para usuários que preferem uma interface visual para gerenciar clientes, produtos e pedidos.
   - **Como Utilizar:**
     - Execute a classe `SistemaGestao.java` para iniciar a aplicação gráfica.
     - Navegue pelas abas para gerenciar clientes, produtos e pedidos.
     - Utilize os formulários e botões para adicionar, remover e confirmar pedidos.

2. **Sem Interface Gráfica (Backend):**
   - **Descrição:** As classes DAO (`ClienteDAO.java`, `ProdutoDAO.java`, `PedidoDAO.java`) podem ser utilizadas em aplicações sem interface gráfica, como sistemas automatizados, scripts ou outras aplicações que interajam diretamente com o banco de dados.
   - **Uso:** Ideal para integração com outros sistemas, desenvolvimento de APIs REST, ou operações automatizadas que não requerem interação visual.
   - **Como Utilizar:**
     - **Importação das Classes DAO:**
       - Inclua as classes DAO no seu projeto Java.
       - Certifique-se de que a conexão com o banco de dados (`ConexaoBancoDados.java`) está corretamente configurada.
     - **Exemplos de Uso:**
       - **Adicionar Cliente:**
         ```java
         ClienteDAO clienteDAO = new ClienteDAO();
         Cliente novoCliente = new Cliente(0, "João Silva", LocalDate.of(1990, 5, 20), "11987654321", "Rua A", "123", "Bairro B", "Apto 45");
         clienteDAO.adicionarCliente(novoCliente);
         ```
       - **Listar Pedidos:**
         ```java
         PedidoDAO pedidoDAO = new PedidoDAO();
         List<Pedido> pedidos = pedidoDAO.listarPedidos();
         for (Pedido pedido : pedidos) {
             System.out.println(pedido);
         }
         ```
       - **Confirmar Entrega de um Pedido:**
         ```java
         PedidoDAO pedidoDAO = new PedidoDAO();
         pedidoDAO.confirmarEntrega(1); // Confirma o pedido com ID 1
         ```
     - **Integração com Outros Sistemas:**
       - Utilize as classes DAO para construir APIs, serviços web ou scripts que realizem operações no banco de dados conforme a necessidade.

**Observação:** Ao utilizar o backend sem interface gráfica, é importante gerenciar as operações de forma adequada para evitar inconsistências no banco de dados. Considere implementar validações e tratamentos de exceções conforme necessário.

---

### **Considerações Finais**
O sistema de gestão foi aprimorado com a adição de funcionalidades essenciais que melhoram a experiência do usuário e a eficiência operacional do restaurante. A inclusão do botão "Confirmar Pedido" e a correção no carregamento inicial dos dados na aba de pedidos garantem que a interface gráfica esteja sempre sincronizada com o estado atual do banco de dados, proporcionando uma gestão mais intuitiva e eficaz. Além disso, as adaptações para uso tanto com interface gráfica quanto sem ela oferecem maior flexibilidade e possibilidade de integração com outros sistemas ou automações.

**Próximos Passos:**
- **Implementar Feedback Visual:** Adicionar cores ou ícones para indicar visualmente quais pedidos estão confirmados.
- **Desabilitar Botões Condicionalmente:** Desabilitar o botão "Confirmar Pedido" se nenhum pedido estiver selecionado ou se o pedido já estiver confirmado.
- **Adicionar Filtros e Pesquisas:** Melhorar a interface com opções de filtro e pesquisa para facilitar a localização de pedidos específicos.
- **Melhorias na Validação de Dados:** Implementar validações adicionais para garantir a integridade dos dados inseridos pelos usuários.
- **Documentação Completa:** Manter a documentação do código e do projeto atualizada para facilitar futuras manutenções e expansões.

---

**Data da Última Atualização:** 08/12/2024

---
