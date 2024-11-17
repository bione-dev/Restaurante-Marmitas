import javax.swing.*; 
import javax.swing.table.DefaultTableModel; 
import java.awt.*; 
import java.util.List; 
import java.time.LocalDate; 

public class SistemaGestao extends JFrame {
    private JTabbedPane tabbedPane;
    private ClienteDAO clienteDAO;

    public SistemaGestao() {
        clienteDAO = new ClienteDAO();

        setTitle("Sistema de Gestão");
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

        String[] colunas = {"ID", "Nome", "Aniversário", "WhatsApp"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        JTable tabelaClientes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabelaClientes(modeloTabela);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Cliente"));

        JTextField nomeField = new JTextField();
        JTextField aniversarioField = new JTextField();
        JTextField whatsappField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField complementoField = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Aniversário (DD-MM-AAAA):"));
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

        JButton adicionarButton = new JButton("Adicionar");
        JButton removerButton = new JButton("Remover Selecionado");
        formPanel.add(adicionarButton);
        formPanel.add(removerButton);

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
        modeloTabela.setRowCount(0);
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getAniversario(),
                    cliente.getWhatsapp()
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
