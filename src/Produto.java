
public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String tipo; // Alteração de TipoProduto para String

    // Construtor padrão
    public Produto() {}

    // Construtor para inicialização de todos os campos
    public Produto(int id, String nome, double preco, String tipo) {
        this.id = id;
        this.nome = nome;
        this.setPreco(preco); // Utiliza o setter para aplicar o validador
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return nome + " - " + tipo;
    }

    // Método toString para representação textual
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
