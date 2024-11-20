import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Adicionar cliente ao banco de dados
    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, aniversario, whatsapp, endereco_rua, endereco_numero, endereco_bairro, endereco_complemento) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, java.sql.Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEndereco_rua());
            pstmt.setString(5, cliente.getEndereco_numero());
            pstmt.setString(6, cliente.getEndereco_bairro());
            pstmt.setString(7, cliente.getEndereco_complemento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("aniversario").toLocalDate(),
                    rs.getString("whatsapp"),
                    rs.getString("endereco_rua"),
                    rs.getString("endereco_numero"),
                    rs.getString("endereco_bairro"),
                    rs.getString("endereco_complemento")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Buscar cliente por ID
    public Cliente buscarCliente(int id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
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
        return null;
    }

    // Atualizar cliente
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, aniversario = ?, whatsapp = ?, endereco_rua = ?, endereco_numero = ?, " +
                     "endereco_bairro = ?, endereco_complemento = ? WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, java.sql.Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEndereco_rua());
            pstmt.setString(5, cliente.getEndereco_numero());
            pstmt.setString(6, cliente.getEndereco_bairro());
            pstmt.setString(7, cliente.getEndereco_complemento());
            pstmt.setInt(8, cliente.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // Excluir cliente
    public void excluirCliente(int id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    // Buscar cliente pelo nome
    public Cliente buscarClientePorNome(String nome) {
        String sql = "SELECT * FROM Cliente WHERE nome = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
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
            System.err.println("Erro ao buscar cliente por nome: " + e.getMessage());
        }
        return null;
    }
}
