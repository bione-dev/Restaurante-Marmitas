import java.time.LocalDate;

public class Cliente {
    private int id;
    private String nome;
    private LocalDate aniversario;
    private String whatsapp;
    private String enderecoRua;
    private String enderecoNumero;
    private String enderecoBairro;
    private String enderecoComplemento;

    // Construtor padrão
    public Cliente() {}

    // Construtor para inicialização de todos os campos
    public Cliente(int id, String nome, LocalDate aniversario, String whatsapp, 
                   String enderecoRua, String enderecoNumero, String enderecoBairro, 
                   String enderecoComplemento) {
        this.id = id;
        this.nome = nome;
        this.aniversario = aniversario;
        this.whatsapp = whatsapp;
        this.enderecoRua = enderecoRua;
        this.enderecoNumero = enderecoNumero;
        this.enderecoBairro = enderecoBairro;
        this.enderecoComplemento = enderecoComplemento;
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

    public LocalDate getAniversario() {
        return aniversario;
    }

    public void setAniversario(LocalDate aniversario) {
        this.aniversario = aniversario;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEnderecoRua() {
        return enderecoRua;
    }

    public void setEnderecoRua(String enderecoRua) {
        this.enderecoRua = enderecoRua;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    // Método toString para representação textual
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", aniversario=" + aniversario +
                ", whatsapp='" + whatsapp + '\'' +
                ", enderecoRua='" + enderecoRua + '\'' +
                ", enderecoNumero='" + enderecoNumero + '\'' +
                ", enderecoBairro='" + enderecoBairro + '\'' +
                ", enderecoComplemento='" + enderecoComplemento + '\'' +
                '}';
    }
}
