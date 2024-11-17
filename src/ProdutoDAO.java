
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Método para adicionar um produto ao banco de dados
    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO Produto (nome, preco, tipo) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setString(3, produto.getTipo()); // Salva o tipo como String
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os produtos
    public List<Produto> listarTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setTipo(rs.getString("tipo")); // Lê o tipo como String
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    // Método para buscar um produto por ID
    public Produto buscarProduto(int id) {
        Produto produto = null;
        String sql = "SELECT * FROM Produto WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setTipo(rs.getString("tipo")); // Lê o tipo como String
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    // Método para atualizar um produto
    public void atualizarProduto(Produto produto) {
        String sql = "UPDATE Produto SET nome = ?, preco = ?, tipo = ? WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setString(3, produto.getTipo()); // Atualiza o tipo como String
            pstmt.setInt(4, produto.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um produto
    public void excluirProduto(int id) {
        String sql = "DELETE FROM Produto WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar produtos por nome
    public List<Produto> buscarProdutoPorNome(String nome) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produto WHERE nome LIKE ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setTipo(rs.getString("tipo")); // Lê o tipo como String
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
}
