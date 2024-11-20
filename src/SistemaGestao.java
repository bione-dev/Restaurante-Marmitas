import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SistemaGestao extends JFrame {
    private JTabbedPane tabbedPane;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private PedidoDAO pedidoDAO;

    private JComboBox<String> clienteBoxPedidos;
    private JComboBox<String> produtoBoxPedidos;
    private JTable tabelaPedidosDelivery;
    private JTable tabelaPedidosRetirada;

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

                nomeField.setText("");
                aniversarioField.setText("");
                whatsappField.setText("");
                ruaField.setText("");
                numeroField.setText("");
                bairroField.setText("");
                complementoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erro ao adicionar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        removerButton.addActionListener(e -> {
            int linhaSelecionada = tabelaClientes.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um cliente para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idCliente = (int) tabelaClientes.getValueAt(linhaSelecionada, 0);
            clienteDAO.excluirCliente(idCliente);
            atualizarTabelaClientes(modeloTabela);
            JOptionPane.showMessageDialog(panel, "Cliente removido com sucesso!");
        });

        return panel;
    }

    private void atualizarTabelaClientes(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);
        for (Cliente cliente : clienteDAO.listarClientes()) {
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
    }

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

        adicionarButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                double preco = Double.parseDouble(precoField.getText());
                String tipo = tipoField.getText();

                Produto produto = new Produto(0, nome, preco, tipo);
                produtoDAO.adicionarProduto(produto);

                atualizarTabelaProdutos(modeloTabela);
                JOptionPane.showMessageDialog(panel, "Produto adicionado com sucesso!");

                nomeField.setText("");
                precoField.setText("");
                tipoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erro ao adicionar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        removerButton.addActionListener(e -> {
            int linhaSelecionada = tabelaProdutos.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um produto para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idProduto = (int) tabelaProdutos.getValueAt(linhaSelecionada, 0);
            produtoDAO.excluirProduto(idProduto);
            atualizarTabelaProdutos(modeloTabela);
            JOptionPane.showMessageDialog(panel, "Produto removido com sucesso!");
        });

        return panel;
    }

    private void atualizarTabelaProdutos(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);
        for (Produto produto : produtoDAO.listarTodosProdutos()) {
            modeloTabela.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getTipo()
            });
        }
    }

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

        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(clienteBoxPedidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Produto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(produtoBoxPedidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Produtos Selecionados:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 2;
        inputPanel.add(produtoScrollPane, gbc);
        gbc.gridheight = 1;

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Forma de Pagamento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(pagamentoBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Forma de Entrega:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        inputPanel.add(entregaBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(adicionarProdutoBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        inputPanel.add(criarPedidoBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        inputPanel.add(removerPedidoBtn, gbc);

        formPanel.add(inputPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.SOUTH);

        atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);
        atualizarListaClientesNoPedidos();
        atualizarListaProdutos();

        adicionarProdutoBtn.addActionListener(e -> {
            String produtoSelecionado = (String) produtoBoxPedidos.getSelectedItem();
            if (produtoSelecionado != null && !produtosSelecionados.contains(produtoSelecionado)) {
                produtosSelecionados.addElement(produtoSelecionado);
            }
        });

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

                Cliente cliente = clienteDAO.buscarClientePorNome(clienteNome);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(panel, "Cliente não encontrado no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Produto> produtos = new ArrayList<>();
                for (int i = 0; i < produtosSelecionados.size(); i++) {
                    String produtoNome = produtosSelecionados.getElementAt(i);
                    Produto produto = produtoDAO.buscarProdutoPorNome(produtoNome);
                    if (produto != null) {
                        produtos.add(produto);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Produto não encontrado: " + produtoNome, "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String formaEntrega = (String) entregaBox.getSelectedItem();
                String formaPagamento = (String) pagamentoBox.getSelectedItem();

                Pedido novoPedido = new Pedido(0, cliente, produtos, formaEntrega, formaPagamento);
                pedidoDAO.adicionarPedido(novoPedido);

                atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);
                produtosSelecionados.clear();

                JOptionPane.showMessageDialog(panel, "Pedido criado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erro ao criar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        removerPedidoBtn.addActionListener(e -> {
            int abaSelecionada = tabbedPanePedidos.getSelectedIndex();
            JTable tabela = abaSelecionada == 0 ? tabelaPedidosDelivery : tabelaPedidosRetirada;
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(panel, "Selecione um pedido para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idPedido = (int) tabela.getValueAt(linhaSelecionada, 0);
            pedidoDAO.cancelarPedido(idPedido);
            atualizarTabelaPedidos(modeloTabelaDelivery, modeloTabelaRetirada);
            JOptionPane.showMessageDialog(panel, "Pedido removido com sucesso!");
        });

        tabelaPedidosDelivery.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabelaPedidosDelivery.getSelectedRow();
                if (row != -1) {
                    int pedidoId = (int) tabelaPedidosDelivery.getValueAt(row, 0);
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
                }
            }
        });

        return panel;
    }

    private void atualizarTabelaPedidos(DefaultTableModel modeloDelivery, DefaultTableModel modeloRetirada) {
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
                    pedido.calcularTotal()
            };

            if ("Delivery".equalsIgnoreCase(pedido.getFormaEntrega())) {
                modeloDelivery.addRow(row);
            } else {
                modeloRetirada.addRow(row);
            }
        }
    }

    private void atualizarListaClientesNoPedidos() {
        clienteBoxPedidos.removeAllItems();
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            clienteBoxPedidos.addItem(cliente.getNome());
        }
    }

    private void atualizarListaProdutos() {
        produtoBoxPedidos.removeAllItems();
        List<Produto> produtos = produtoDAO.listarTodosProdutos();
        for (Produto produto : produtos) {
            produtoBoxPedidos.addItem(produto.getNome());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaGestao app = new SistemaGestao();
            app.setVisible(true);
        });
    }
}
