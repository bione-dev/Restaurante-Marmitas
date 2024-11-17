
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

            pstmtPedido.setDate(1, Date.valueOf(pedido.getData())); // Usando o método getData()
            pstmtPedido.setTimestamp(2, Timestamp.valueOf(pedido.getDataHora()));
            pstmtPedido.setInt(3, pedido.getCliente().getId());
            pstmtPedido.setString(4, pedido.getFormaEntrega()); // Salva a forma de entrega como String
            pstmtPedido.setString(5, pedido.getFormaPagamento()); // Salva a forma de pagamento como String
            pstmtPedido.setBoolean(6, pedido.isEntregaConfirmada());
            pstmtPedido.executeUpdate();

            ResultSet generatedKeys = pstmtPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pedidoId = generatedKeys.getInt(1);

                for (Produto produto : pedido.getProdutos()) {
                    pstmtPedidoProduto.setInt(1, pedidoId);
                    pstmtPedidoProduto.setString(2, produto.getNome());
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
        String sqlPedido = "SELECT * FROM Pedido";
        String sqlProdutos = "SELECT p.* FROM Produto p INNER JOIN Pedido_Produto pp ON p.nome = pp.nome_produto WHERE pp.pedido_id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido);
             PreparedStatement pstmtProdutos = conn.prepareStatement(sqlProdutos)) {

            ResultSet rsPedido = pstmtPedido.executeQuery();
            while (rsPedido.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rsPedido.getInt("id"));
                pedido.setData(rsPedido.getDate("data").toLocalDate());
                pedido.setDataHora(rsPedido.getTimestamp("data_hora").toLocalDateTime());

                Cliente cliente = new ClienteDAO().buscarCliente(rsPedido.getInt("cliente_id"));
                pedido.setCliente(cliente);

                pedido.setFormaEntrega(rsPedido.getString("forma_entrega")); // Lida com forma de entrega como String
                pedido.setFormaPagamento(rsPedido.getString("forma_pagamento")); // Lida com forma de pagamento como String
                pedido.setEntregaConfirmada(rsPedido.getBoolean("entrega_confirmada"));

                List<Produto> produtos = new ArrayList<>();
                pstmtProdutos.setInt(1, pedido.getId());
                ResultSet rsProdutos = pstmtProdutos.executeQuery();
                while (rsProdutos.next()) {
                    Produto produto = new Produto();
                    produto.setId(rsProdutos.getInt("id"));
                    produto.setNome(rsProdutos.getString("nome"));
                    produto.setPreco(rsProdutos.getDouble("preco"));
                    produto.setTipo(rsProdutos.getString("tipo")); // Trata o tipo como String
                    produtos.add(produto);
                }
                pedido.setProdutos(produtos);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    // Método para buscar um pedido específico pelo ID
    public Pedido buscarPedido(int id) {
        Pedido pedido = null;
        String sqlPedido = "SELECT * FROM Pedido WHERE id = ?";
        String sqlProdutos = "SELECT p.* FROM Produto p INNER JOIN Pedido_Produto pp ON p.nome = pp.nome_produto WHERE pp.pedido_id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido);
             PreparedStatement pstmtProdutos = conn.prepareStatement(sqlProdutos)) {

            pstmtPedido.setInt(1, id);
            ResultSet rsPedido = pstmtPedido.executeQuery();

            if (rsPedido.next()) {
                pedido = new Pedido();
                pedido.setId(rsPedido.getInt("id"));
                pedido.setData(rsPedido.getDate("data").toLocalDate());
                pedido.setDataHora(rsPedido.getTimestamp("data_hora").toLocalDateTime());

                Cliente cliente = new ClienteDAO().buscarCliente(rsPedido.getInt("cliente_id"));
                pedido.setCliente(cliente);

                pedido.setFormaEntrega(rsPedido.getString("forma_entrega")); // Lida com forma de entrega como String
                pedido.setFormaPagamento(rsPedido.getString("forma_pagamento")); // Lida com forma de pagamento como String
                pedido.setEntregaConfirmada(rsPedido.getBoolean("entrega_confirmada"));

                List<Produto> produtos = new ArrayList<>();
                pstmtProdutos.setInt(1, pedido.getId());
                ResultSet rsProdutos = pstmtProdutos.executeQuery();
                while (rsProdutos.next()) {
                    Produto produto = new Produto();
                    produto.setId(rsProdutos.getInt("id"));
                    produto.setNome(rsProdutos.getString("nome"));
                    produto.setPreco(rsProdutos.getDouble("preco"));
                    produto.setTipo(rsProdutos.getString("tipo")); // Trata o tipo como String
                    produtos.add(produto);
                }
                pedido.setProdutos(produtos);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedido;
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
            e.printStackTrace();
        }
    }

    // Método para confirmar entrega de um pedido pelo ID
    public void confirmarEntrega(int id) {
        String sql = "UPDATE Pedido SET entrega_confirmada = TRUE WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Entrega do pedido com ID " + id + " foi confirmada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
