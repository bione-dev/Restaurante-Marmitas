import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Método para adicionar um pedido ao banco de dados
    public void adicionarPedido(Pedido pedido) {
        String sqlPedido = "INSERT INTO Pedido (data, data_hora, cliente_id, forma_entrega, forma_pagamento, entrega_confirmada) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPedidoProduto = "INSERT INTO Pedido_Produto (pedido_id, nome_produto) VALUES (?, ?)";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtPedidoProduto = conn.prepareStatement(sqlPedidoProduto)) {

            // Inserir o pedido na tabela Pedido
            pstmtPedido.setDate(1, Date.valueOf(pedido.getData())); // Data do pedido
            pstmtPedido.setTimestamp(2, Timestamp.valueOf(pedido.getDataHora())); // Data e hora do pedido
            pstmtPedido.setInt(3, pedido.getCliente().getId()); // ID do cliente
            pstmtPedido.setString(4, pedido.getFormaEntrega()); // Forma de entrega
            pstmtPedido.setString(5, pedido.getFormaPagamento()); // Forma de pagamento
            pstmtPedido.setBoolean(6, pedido.isEntregaConfirmada()); // Entrega confirmada
            pstmtPedido.executeUpdate();

            // Obter o ID do pedido gerado
            ResultSet generatedKeys = pstmtPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pedidoId = generatedKeys.getInt(1);

                // Inserir os produtos relacionados na tabela Pedido_Produto
                for (Produto produto : pedido.getProdutos()) {
                    pstmtPedidoProduto.setInt(1, pedidoId);
                    pstmtPedidoProduto.setString(2, produto.getNome()); // Usar nome_produto
                    pstmtPedidoProduto.addBatch();
                }
                pstmtPedidoProduto.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os pedidos
    public List<Pedido> listarPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int clienteId = rs.getInt("cliente_id");
                String formaEntrega = rs.getString("forma_entrega");
                String formaPagamento = rs.getString("forma_pagamento");
                boolean confirmado = rs.getBoolean("entrega_confirmada");

                // Buscar o cliente associado ao pedido
                Cliente cliente = buscarClientePorId(clienteId);

                // Buscar os produtos associados ao pedido
                List<Produto> produtos = buscarProdutosPorPedidoId(id);

                // Criar o objeto Pedido
                Pedido pedido = new Pedido(id, cliente, produtos, formaEntrega, formaPagamento);
                pedido.setEntregaConfirmada(confirmado);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    // Método para buscar os produtos associados a um pedido
    private List<Produto> buscarProdutosPorPedidoId(int pedidoId) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.* FROM Produto p " +
                     "JOIN Pedido_Produto pp ON p.nome = pp.nome_produto " +
                     "WHERE pp.pedido_id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedidoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("tipo")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos para o pedido: " + e.getMessage());
        }

        return produtos;
    }

    // Método para buscar um pedido pelo ID
    public Pedido buscarPedidoPorId(int id) {
        Pedido pedido = null;
        String sql = "SELECT * FROM Pedido WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String formaEntrega = rs.getString("forma_entrega");
                String formaPagamento = rs.getString("forma_pagamento");
                boolean confirmado = rs.getBoolean("entrega_confirmada");

                // Buscar o cliente associado ao pedido
                Cliente cliente = buscarClientePorId(clienteId);

                // Buscar os produtos associados ao pedido
                List<Produto> produtos = buscarProdutosPorPedidoId(id);

                // Criar o objeto Pedido
                pedido = new Pedido(id, cliente, produtos, formaEntrega, formaPagamento);
                pedido.setEntregaConfirmada(confirmado);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido: " + e.getMessage());
        }

        return pedido;
    }

    // Método auxiliar para buscar um cliente pelo ID
    private Cliente buscarClientePorId(int clienteId) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("aniversario").toLocalDate(),
                    rs.getString("whatsapp"),
                    rs.getString("endereco_rua"),
                    rs.getString("endereco_numero"),
                    rs.getString("endereco_bairro"),
                    rs.getString("endereco_complemento")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

    // Método para cancelar um pedido pelo ID
    public void cancelarPedido(int id) {
        String sql = "DELETE FROM Pedido WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Pedido com ID " + id + " foi cancelado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    // Método para confirmar a entrega de um pedido pelo ID
    public void confirmarEntrega(int id) {
        String sql = "UPDATE Pedido SET entrega_confirmada = TRUE WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Entrega do pedido com ID " + id + " foi confirmada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao confirmar entrega do pedido: " + e.getMessage());
        }
    }
}
