import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDados {
    private static final String URL = "jdbc:postgresql://localhost:5432/rest";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456";

    // Bloco estático para garantir que o driver seja carregado uma vez
    static {
        try {
            Class.forName("org.postgresql.Driver");  // Carrega o driver do PostgreSQL
            System.out.println("Driver do PostgreSQL carregado com sucesso.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver do PostgreSQL.");
            e.printStackTrace();
        }
    }

    // Método para obter uma nova conexão com o banco de dados
    public static Connection obterConexao() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados.");
            throw e; // Relança a exceção para tratamento em níveis superiores
        }
    }
}
