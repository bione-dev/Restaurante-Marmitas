import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Adiciona um novo cliente ao banco de dados
    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, aniversario, whatsapp, endereco_rua, endereco_numero, endereco_bairro, endereco_complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEnderecoRua());
            pstmt.setString(5, cliente.getEnderecoNumero());
            pstmt.setString(6, cliente.getEnderecoBairro());
            pstmt.setString(7, cliente.getEnderecoComplemento());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Busca clientes com base em parte do nome fornecido
    public List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE nome ILIKE ?"; // Usando ILIKE para buscar insensível a maiúsculas (PostgreSQL)

        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%"); // Usa '%' para busca parcial
            ResultSet rs = pstmt.executeQuery();

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
            e.printStackTrace();
        }
        return clientes;
    }

    // Busca um cliente específico pelo ID
    public Cliente buscarCliente(int id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
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
            e.printStackTrace();
        }
        return null;
    }

    // Atualiza as informações de um cliente existente
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, aniversario = ?, whatsapp = ?, endereco_rua = ?, endereco_numero = ?, endereco_bairro = ?, endereco_complemento = ? WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, Date.valueOf(cliente.getAniversario()));
            pstmt.setString(3, cliente.getWhatsapp());
            pstmt.setString(4, cliente.getEnderecoRua());
            pstmt.setString(5, cliente.getEnderecoNumero());
            pstmt.setString(6, cliente.getEnderecoBairro());
            pstmt.setString(7, cliente.getEnderecoComplemento());
            pstmt.setInt(8, cliente.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Exclui um cliente do banco de dados com base no ID
    public void excluirCliente(int id) {
        String sql = "DELETE FROM Cliente WHERE id = ?";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lista todos os clientes registrados no banco de dados
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection conn = ConexaoBancoDados.obterConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
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
            e.printStackTrace();
        }
        return clientes;
    }
}
