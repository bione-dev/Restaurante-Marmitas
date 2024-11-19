import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Adicionar cliente ao banco de dados
    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, aniversario, whatsapp, endereco_rua, endereco_numero, endereco_bairro, endereco_complemento) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setando os parâmetros
            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEndereco_rua()); // Atualizado
            pstmt.setString(5, cliente.getEndereco_numero()); // Atualizado
            pstmt.setString(6, cliente.getEndereco_bairro()); // Atualizado
            pstmt.setString(7, cliente.getEndereco_complemento()); // Atualizado

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
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setAniversario(rs.getDate("aniversario").toLocalDate());
                cliente.setWhatsapp(rs.getString("whatsapp"));
                cliente.setEndereco_rua(rs.getString("endereco_rua")); // Atualizado
                cliente.setEndereco_numero(rs.getString("endereco_numero")); // Atualizado
                cliente.setEndereco_bairro(rs.getString("endereco_bairro")); // Atualizado
                cliente.setEndereco_complemento(rs.getString("endereco_complemento")); // Atualizado
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Buscar cliente por ID
    public Cliente buscarCliente(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setAniversario(rs.getDate("aniversario").toLocalDate());
                cliente.setWhatsapp(rs.getString("whatsapp"));
                cliente.setEndereco_rua(rs.getString("endereco_rua")); // Atualizado
                cliente.setEndereco_numero(rs.getString("endereco_numero")); // Atualizado
                cliente.setEndereco_bairro(rs.getString("endereco_bairro")); // Atualizado
                cliente.setEndereco_complemento(rs.getString("endereco_complemento")); // Atualizado
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

    // Atualizar cliente
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, aniversario = ?, whatsapp = ?, endereco_rua = ?, endereco_numero = ?, " +
                     "endereco_bairro = ?, endereco_complemento = ? WHERE id = ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEndereco_rua()); // Atualizado
            pstmt.setString(5, cliente.getEndereco_numero()); // Atualizado
            pstmt.setString(6, cliente.getEndereco_bairro()); // Atualizado
            pstmt.setString(7, cliente.getEndereco_complemento()); // Atualizado
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

    // Buscar clientes por nome
    public List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE nome LIKE ?";

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setAniversario(rs.getDate("aniversario").toLocalDate());
                cliente.setWhatsapp(rs.getString("whatsapp"));
                cliente.setEndereco_rua(rs.getString("endereco_rua")); // Atualizado
                cliente.setEndereco_numero(rs.getString("endereco_numero")); // Atualizado
                cliente.setEndereco_bairro(rs.getString("endereco_bairro")); // Atualizado
                cliente.setEndereco_complemento(rs.getString("endereco_complemento")); // Atualizado
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes por nome: " + e.getMessage());
        }

        return clientes;
    }
}
