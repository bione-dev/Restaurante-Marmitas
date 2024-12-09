import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SistemaGestao extends JFrame {
    private JTabbedPane tabbedPane;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private PedidoDAO pedidoDAO;

    private JComboBox<String> clienteBoxPedidos;
    private JComboBox<String> produtoBoxPedidos;
    private JTable tabelaPedidosDelivery;
    private JTable tabelaPedidosRetirada;

    private Scanner scanner = new Scanner(System.in); // Importado java.util.Scanner

    public SistemaGestao() {
        clienteDAO = new ClienteDAO();
        produtoDAO = new ProdutoDAO();
        pedidoDAO = new PedidoDAO();

        setTitle("Sistema de Gestão - Restaurante Modelo");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        JPanel clientePanel = criarPainelClientes();
        tabbedPane.add("Clientes", clientePanel);

        JPanel produtoPanel = criarPainelProdutos();
        tabbedPane.add("Produtos", produtoPanel);

        JPanel pedidoPanel = criarPainelPedidos();
        tabbedPane.add("Pedidos", pedidoPanel);

        add(tabbedPane);
    }

    /**
     * Cria o painel de clientes com tabelas e formulários.
     */
    private JPanel criarPainelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Aniversário", "WhatsApp", "Rua", "Número", "Bairro", "Complemento"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabelaClientes(modeloTabela);

        // Formulário para adicionar/remover clientes
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        JTextField nomeField = new JTextField();
        JTextField aniversarioField = new JTextField();
        JTextField whatsappField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField complementoField = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Aniversário (AAAA-MM-DD):"));
        formPanel.add(aniversarioField);

        formPanel.add(new JLabel("WhatsApp:"));
        formPanel.add(whatsappField);

        formPanel.add(new JLabel("Rua:"));
        formPanel.add(ruaField);

        formPanel.add(new JLabel("Número:"));
        formPanel.add(numeroField);

        formPanel.add(new JLabel("Bairro:"));
        formPanel.add(bairroField);

        formPanel.add(new JLabel("Complemento:"));
        formPanel.add(complementoField);

        JButton adicionarButton = new JButton("Adicionar Cliente");
        JButton removerButton = new JButton("Remover Cliente");

        formPanel.add(adicionarButton);
        formPanel.add(removerButton);

        panel.add(formPanel, BorderLayout.SOUTH);

        // Ação do botão Adicionar Cliente
        adicionarButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                String aniversario = aniversarioField.getText();
                String whatsapp = whatsappField.getText();
                String rua = ruaField.getText();
                String numero = numeroField.getText();
                String bairro = bairroField.getText();
                String complemento = complementoField.getText();

                Cliente cliente = new Cliente(0, nome, java.time.LocalDate.parse(aniversario), whatsapp, rua, numero, bairro, complemento);
                clienteDAO.adicionarCliente(cliente);

                atualizarTabelaClientes(modeloTabela);
                JOptionPane.showMessageDialog(panel, "Cliente adicionado com sucesso!");

                // Limpar campos
                nomeField.setText("");
                aniversarioField.setText("");
                whatsappField.setText("");
                ruaField.setText("");
                numeroField.setText("");
                bairroField.setText("");
                complementoField.setText("");
            } catch (Exception ex) { // Renomeado para 'ex'
                JOptionPane.showMessageDialog(panel, "Erro ao adicionar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão Remover Cliente
        removerButton.addActionListener(e -> {
            int linhaSelecionada = tabelaClientes.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um cliente para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente = (int) tabelaClientes.getValueAt(linhaSelecionada, 0);
            try {
                clienteDAO.excluirCliente(idCliente);
                atualizarTabelaClientes(modeloTabela);
                JOptionPane.showMessageDialog(panel, "Cliente removido com sucesso!");
            } catch (Exception ex) { // Renomeado para 'ex'
                JOptionPane.showMessageDialog(panel, "Erro ao remover cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Atualiza a tabela de clientes com os dados do banco.
     *
     * @param modeloTabela O modelo da tabela a ser atualizado.
     */
    private void atualizarTabelaClientes(DefaultTableModel modeloTabela) {
        try {
            modeloTabela.setRowCount(0);
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente cliente : clientes) {
                modeloTabela.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getAniversario(),
                        cliente.getWhatsapp(),
                        cliente.getEndereco_rua(),
                        cliente.getEndereco_numero(),
                        cliente.getEndereco_bairro(),
                        cliente.getEndereco_complemento()
                });
            }
        } catch (Exception e) { // Certifique-se de que aqui está 'e' sem duplicação
            JOptionPane.showMessageDialog(this, "Erro ao listar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cria o painel de produtos com tabelas e formulários.
     */
    private JPanel criarPainelProdutos() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Preço", "Tipo"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabelaProdutos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        panel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabelaProdutos(modeloTabela);

        // Formulário para adicionar/remover produtos
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nomeField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField tipoField = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Preço:"));
        formPanel.add(precoField);

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(tipoField);

        JButton adicionarButton = new JButton("Adicionar Produto");
        JButton removerButton = new JButton("Remover Produto");

        formPanel.add(adicionarButton);
        formPanel.add(removerButton);

        panel.add(formPanel, BorderLayout.SOUTH);

        // Ação do botão Adicionar Produto
        adicionarButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                double preco = Double.parseDouble(precoField.getText());
                String tipo = tipoField.getText();

                Produto produto = new Produto(0, nome, preco, tipo);
                produtoDAO.adicionarProduto(produto);

                atualizarTabelaProdutos(modeloTabela);
                JOptionPane.showMessageDialog(panel, "Produto adicionado com sucesso!");

                // Limpar campos
                nomeField.setText("");
                precoField.setText("");
                tipoField.setText("");
            } catch (NumberFormatException ex) { // Renomeado para 'ex'
                JOptionPane.showMessageDialog(panel, "Preço inválido! Insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { // Renomeado para 'ex'
                JOptionPane.showMessageDialog(panel, "Erro ao adicionar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão Remover Produto
        removerButton.addActionListener(e -> {
            int linhaSelecionada = tabelaProdutos.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um produto para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProduto = (int) tabelaProdutos.getValueAt(linhaSelecionada, 0);
            try {
                produtoDAO.excluirProduto(idProduto);
                atualizarTabelaProdutos(modeloTabela);
                JOptionPane.showMessageDialog(panel, "Produto removido com sucesso!");
            } catch (Exception ex) { // Renomeado para 'ex'
                JOptionPane.showMessageDialog(panel, "Erro ao remover produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Atualiza a tabela de produtos com os dados do banco.
     *
     * @param modeloTabela O modelo da tabela a ser atualizado.
     */
    private void atualizarTabelaProdutos(DefaultTableModel modeloTabela) {
        try {
            modeloTabela.setRowCount(0);
            List<Produto> produtos = produtoDAO.listarTodosProdutos();
            for (Produto produto : produtos) {
                modeloTabela.addRow(new Object[]{
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getTipo()
                });
            }
        } catch (Exception e) { // Aqui está 'e', sem duplicação
            JOptionPane.showMessageDialog(this, "Erro ao listar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cria o painel de pedidos com tabelas e formulários.
     */
    private JPanel criarPainelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Separar os pedidos em duas tabelas (Delivery e Retirada)
        String[] colunas = {"ID", "Cliente", "Produtos", "Forma Pagamento", "Confirmado", "Total"};
        DefaultTableModel modeloTabelaDelivery = new DefaultTableModel(colunas, 0);
        DefaultTableModel modeloTabelaRetirada = new DefaultTableModel(colunas, 0);

        tabelaPedidosDelivery = new JTable(modeloTabelaDelivery);
        tabelaPedidosRetirada = new JTable(modeloTabelaRetirada);

        JScrollPane scrollDelivery = new JScrollPane(tabelaPedidosDelivery);
        JScrollPane scrollRetirada = new JScrollPane(tabelaPedidosRetirada);

        // Adicionando abas para Delivery e Retirada
        JTabbedPane tabbedPanePedidos = new JTabbedPane();
        tabbedPanePedidos.add("Delivery", scrollDelivery);
        tabbedPanePedidos.add("Retirada", scrollRetirada);

        panel.add(tabbedPanePedidos, BorderLayout.CENTER);

        // Formulário para criar pedidos
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        clienteBoxPedidos = new JComboBox<>();
        produtoBoxPedidos = new JComboBox<>();
        DefaultListModel<String> produtosSelecionados = new DefaultListModel<>();
        JList<String> listaProdutos = new JList<>(produtosSelecionados);
        JScrollPane produtoScrollPane = new JScrollPane(listaProdutos);
        JComboBox<String> pagamentoBox = new JComboBox<>(new String[]{"Dinheiro", "Cartão", "PIX"});
        JComboBox<String> entregaBox = new JComboBox<>(new String[]{"Retirada", "Delivery"});

        JButton adicionarProdutoBtn = new JButton("Adicionar Produto");
        JButton criarPedidoBtn = new JButton("Criar Pedido");
        JButton removerPedidoBtn = new JButton("Remover Pedido");
        JButton confirmarPedidoBtn = new JButton("Confirmar Pedido"); // Novo botão

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(clienteBoxPedidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Produto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(produtoBoxPedidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Produtos Selecionados:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        formPanel.add(produtoScrollPane, gbc);
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Forma de Pagamento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(pagamentoBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Forma de Entrega:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(entregaBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(adicionarProdutoBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(criarPedidoBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(removerPedidoBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(confirmarPedidoBtn, gbc); // Adiciona o novo botão

        panel.add(formPanel, BorderLayout.SOUTH);

        // Atualizar listas de clientes e produtos
        atualizarListaClientesNoPedidos();
        atualizarListaProdutos();

        // Atualizar a tabela de pedidos inicialmente
        atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);

        // Ação do botão Adicionar Produto
        adicionarProdutoBtn.addActionListener(e -> {
            String produtoSelecionado = (String) produtoBoxPedidos.getSelectedItem();
            if (produtoSelecionado != null && !produtosSelecionados.contains(produtoSelecionado)) {
                produtosSelecionados.addElement(produtoSelecionado);
            }
        });

        // Ação do botão Criar Pedido
        criarPedidoBtn.addActionListener(e -> {
            try {
                String clienteNome = (String) clienteBoxPedidos.getSelectedItem();
                if (clienteNome == null) {
                    JOptionPane.showMessageDialog(panel, "Selecione um cliente válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (produtosSelecionados.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Adicione ao menos um produto ao pedido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Buscar clientes por nome utilizando o método auxiliar
                List<Cliente> clientesEncontrados = buscarClientesPorNome(clienteNome);
                Cliente cliente = null;

                if (clientesEncontrados.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Cliente não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (clientesEncontrados.size() == 1) {
                    cliente = clientesEncontrados.get(0);
                } else {
                    // Se múltiplos clientes com o mesmo nome, permitir seleção
                    String[] nomes = clientesEncontrados.stream()
                            .map(c -> c.getNome() + " (ID: " + c.getId() + ")")
                            .toArray(String[]::new);
                    String clienteSelecionado = (String) JOptionPane.showInputDialog(
                            panel,
                            "Múltiplos clientes encontrados. Selecione:",
                            "Selecionar Cliente",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            nomes,
                            nomes[0]
                    );
                    if (clienteSelecionado == null) {
                        // Usuário cancelou a seleção
                        return;
                    }
                    // Encontrar o cliente selecionado
                    for (Cliente c : clientesEncontrados) {
                        String descricao = c.getNome() + " (ID: " + c.getId() + ")";
                        if (descricao.equals(clienteSelecionado)) {
                            cliente = c;
                            break;
                        }
                    }
                }

                List<Produto> produtos = new ArrayList<>();
                for (int i = 0; i < produtosSelecionados.size(); i++) {
                    String produtoNome = produtosSelecionados.getElementAt(i);
                    Produto produtoEncontrado = produtoDAO.buscarProdutoPorNome(produtoNome);

                    if (produtoEncontrado == null) {
                        JOptionPane.showMessageDialog(panel, "Produto não encontrado: " + produtoNome, "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        produtos.add(produtoEncontrado);
                    }
                }

                String formaEntrega = (String) entregaBox.getSelectedItem();
                String formaPagamento = (String) pagamentoBox.getSelectedItem();

                Pedido novoPedido = new Pedido(0, cliente, produtos, formaEntrega, formaPagamento);
                pedidoDAO.adicionarPedido(novoPedido);

                atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);
                produtosSelecionados.clear();

                JOptionPane.showMessageDialog(panel, "Pedido criado com sucesso!");
            } catch (Exception ex) { // Renomeado para 'ex' para evitar duplicação
                JOptionPane.showMessageDialog(panel, "Erro ao criar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão Remover Pedido
        removerPedidoBtn.addActionListener(e -> {
            int abaSelecionada = tabbedPanePedidos.getSelectedIndex();
            JTable tabela = abaSelecionada == 0 ? tabelaPedidosDelivery : tabelaPedidosRetirada;
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um pedido para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idPedido = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());
            try {
                pedidoDAO.cancelarPedido(idPedido);
                atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);
                JOptionPane.showMessageDialog(panel, "Pedido removido com sucesso!");
            } catch (Exception ex) { // Renomeado para 'ex' para evitar duplicação
                JOptionPane.showMessageDialog(panel, "Erro ao remover pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação do botão Confirmar Pedido
        confirmarPedidoBtn.addActionListener(e -> {
            int abaSelecionada = tabbedPanePedidos.getSelectedIndex();
            JTable tabela = abaSelecionada == 0 ? tabelaPedidosDelivery : tabelaPedidosRetirada;
            int linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um pedido para confirmar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idPedido = Integer.parseInt(tabela.getValueAt(linhaSelecionada, 0).toString());

            try {
                // Chamar o método para confirmar a entrega do pedido no DAO
                pedidoDAO.confirmarEntrega(idPedido);

                // Atualizar a tabela para refletir a alteração
                atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);

                JOptionPane.showMessageDialog(panel, "Pedido confirmado com sucesso!");
            } catch (Exception ex) { // Renomeado para 'ex' para evitar duplicação
                JOptionPane.showMessageDialog(panel, "Erro ao confirmar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ação de clicar na tabela de pedidos de Delivery para mostrar detalhes
        tabelaPedidosDelivery.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabelaPedidosDelivery.getSelectedRow();
                if (row != -1) {
                    int pedidoId = Integer.parseInt(tabelaPedidosDelivery.getValueAt(row, 0).toString());
                    try {
                        Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
                        if (pedido != null) {
                            Cliente cliente = pedido.getCliente();
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Detalhes do Cliente:\n" +
                                            "Nome: " + cliente.getNome() + "\n" +
                                            "WhatsApp: " + cliente.getWhatsapp() + "\n" +
                                            "Endereço: " + cliente.getEndereco_rua() + ", " +
                                            cliente.getEndereco_numero() + " - " + cliente.getEndereco_bairro() +
                                            (cliente.getEndereco_complemento() != null && !cliente.getEndereco_complemento().isEmpty()
                                                    ? " (" + cliente.getEndereco_complemento() + ")" : ""),
                                    "Detalhes do Pedido",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (Exception ex) { // Renomeado para 'ex' para evitar duplicação
                        JOptionPane.showMessageDialog(null, "Erro ao buscar detalhes do pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Opcional: Adicionar MouseListener para a tabela de Retirada também, se desejar
        tabelaPedidosRetirada.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabelaPedidosRetirada.getSelectedRow();
                if (row != -1) {
                    int pedidoId = Integer.parseInt(tabelaPedidosRetirada.getValueAt(row, 0).toString());
                    try {
                        Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
                        if (pedido != null) {
                            Cliente cliente = pedido.getCliente();
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Detalhes do Cliente:\n" +
                                            "Nome: " + cliente.getNome() + "\n" +
                                            "WhatsApp: " + cliente.getWhatsapp() + "\n" +
                                            "Endereço: " + cliente.getEndereco_rua() + ", " +
                                            cliente.getEndereco_numero() + " - " + cliente.getEndereco_bairro() +
                                            (cliente.getEndereco_complemento() != null && !cliente.getEndereco_complemento().isEmpty()
                                                    ? " (" + cliente.getEndereco_complemento() + ")" : ""),
                                    "Detalhes do Pedido",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    } catch (Exception ex) { // Renomeado para 'ex' para evitar duplicação
                        JOptionPane.showMessageDialog(null, "Erro ao buscar detalhes do pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return panel;
    }

    /**
     * Atualiza a tabela de pedidos com os dados do banco, separando por forma de entrega.
     *
     * @param modeloDelivery O modelo da tabela de pedidos de Delivery.
     * @param modeloRetirada O modelo da tabela de pedidos de Retirada.
     */
    private void atualizarTabelaPedidos(DefaultTableModel modeloDelivery, DefaultTableModel modeloRetirada) {
        try {
            modeloDelivery.setRowCount(0);
            modeloRetirada.setRowCount(0);

            List<Pedido> pedidos = pedidoDAO.listarPedidos();

            for (Pedido pedido : pedidos) {
                String produtosNomes = String.join(", ", pedido.getProdutos().stream().map(Produto::getNome).toList());
                Object[] row = {
                        pedido.getId(),
                        pedido.getCliente().getNome(),
                        produtosNomes,
                        pedido.getFormaPagamento(),
                        pedido.isEntregaConfirmada() ? "Sim" : "Não",
                        String.format("%.2f", pedido.calcularTotal())
                };

                if ("Delivery".equalsIgnoreCase(pedido.getFormaEntrega())) {
                    modeloDelivery.addRow(row);
                } else {
                    modeloRetirada.addRow(row);
                }
            }
        } catch (Exception e) { // Aqui está 'e', sem duplicação
            JOptionPane.showMessageDialog(this, "Erro ao listar pedidos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza a lista de clientes no combobox de pedidos.
     */
    private void atualizarListaClientesNoPedidos() {
        try {
            clienteBoxPedidos.removeAllItems();
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente cliente : clientes) {
                clienteBoxPedidos.addItem(cliente.getNome());
            }
        } catch (Exception e) { // Aqui está 'e', sem duplicação
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza a lista de produtos no combobox de pedidos.
     */
    private void atualizarListaProdutos() {
        try {
            produtoBoxPedidos.removeAllItems();
            List<Produto> produtos = produtoDAO.listarTodosProdutos();
            for (Produto produto : produtos) {
                produtoBoxPedidos.addItem(produto.getNome());
            }
        } catch (Exception e) { // Aqui está 'e', sem duplicação
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método auxiliar para buscar clientes por nome utilizando listarClientes()
     *
     * @param nome O nome ou parte do nome do cliente a ser buscado.
     * @return Uma lista de clientes que correspondem ao critério de busca.
     */
    private List<Cliente> buscarClientesPorNome(String nome) throws SQLException {
        List<Cliente> todosClientes = clienteDAO.listarClientes();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : todosClientes) {
            if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaGestao app = new SistemaGestao();
            app.setVisible(true);
        });
    }
}
