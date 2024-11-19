import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class SistemaGestao extends JFrame {
    private JTabbedPane tabbedPane;
    private ClienteDAO clienteDAO;

    public SistemaGestao() {
        clienteDAO = new ClienteDAO();

        setTitle("Restaurante Modelo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        JPanel clientePanel = criarPainelClientes();
        tabbedPane.add("Clientes", clientePanel);

        JPanel pedidoPanel = criarPainelPedidos();
        tabbedPane.add("Pedidos", pedidoPanel);

        add(tabbedPane);
    }

    private JPanel criarPainelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        // Atualização: Incluindo os novos campos na tabela
        String[] colunas = {"ID", "Nome", "Aniversário", "WhatsApp", "Rua", "Número", "Bairro", "Complemento"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabelaClientes(modeloTabela);

        // Alterado para GridBagLayout para ajustar o espaçamento
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margem entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nomeField = new JTextField();
        JTextField aniversarioField = new JTextField();
        JTextField whatsappField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField complementoField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Aniversário (AAAA-MM-DD):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(aniversarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("WhatsApp:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(whatsappField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Rua:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(ruaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Número:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(numeroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Bairro:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(bairroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Complemento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        formPanel.add(complementoField, gbc);

        JButton adicionarButton = new JButton("Adicionar");
        JButton removerButton = new JButton("Remover Selecionado");

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(adicionarButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(removerButton, gbc);

        panel.add(formPanel, BorderLayout.SOUTH);

        adicionarButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                LocalDate aniversario = LocalDate.parse(aniversarioField.getText());
                String whatsapp = whatsappField.getText();
                String rua = ruaField.getText();
                String numero = numeroField.getText();
                String bairro = bairroField.getText();
                String complemento = complementoField.getText();

                Cliente cliente = new Cliente(0, nome, aniversario, whatsapp, rua, numero, bairro, complemento);
                clienteDAO.adicionarCliente(cliente);

                atualizarTabelaClientes(modeloTabela);

                // Limpando os campos após adicionar
                nomeField.setText("");
                aniversarioField.setText("");
                whatsappField.setText("");
                ruaField.setText("");
                numeroField.setText("");
                bairroField.setText("");
                complementoField.setText("");

                JOptionPane.showMessageDialog(panel, "Cliente cadastrado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        });

        return panel;
    }

    private void atualizarTabelaClientes(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0); // Limpa a tabela antes de adicionar os novos dados
        List<Cliente> clientes = clienteDAO.listarClientes(); // Obtém a lista de clientes da DAO

        // Adiciona cada cliente à tabela
        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getAniversario(),
                    cliente.getWhatsapp(),
                    cliente.getEndereco_rua(), // Corrigido: usa getEndereco_rua()
                    cliente.getEndereco_numero(), // Corrigido: usa getEndereco_numero()
                    cliente.getEndereco_bairro(), // Corrigido: usa getEndereco_bairro()
                    cliente.getEndereco_complemento() // Corrigido: usa getEndereco_complemento()
            });
        }
    }
    private JPanel criarPainelPedidos() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Funcionalidade de pedidos ainda será implementada."));
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaGestao app = new SistemaGestao();
            app.setVisible(true);
        });
    }
}
