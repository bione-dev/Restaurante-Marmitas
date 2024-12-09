import java.time.LocalDate;

public class Cliente {
    private int id;
    private String nome;
    private LocalDate aniversario;
    private String whatsapp;
    private String endereco_rua; 
    private String endereco_numero; 
    private String endereco_bairro; 
    private String endereco_complemento; 

    
    public Cliente() {}

    
    public Cliente(int id, String nome, LocalDate aniversario, String whatsapp, 
                   String endereco_rua, String endereco_numero, String endereco_bairro, 
                   String endereco_complemento) {
        this.id = id;
        this.nome = nome;
        this.aniversario = aniversario;
        this.whatsapp = whatsapp;
        this.endereco_rua = endereco_rua;
        this.endereco_numero = endereco_numero;
        this.endereco_bairro = endereco_bairro;
        this.endereco_complemento = endereco_complemento;
    }

   
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

    public String getEndereco_rua() { 
        return endereco_rua;
    }

    public void setEndereco_rua(String endereco_rua) {  
        this.endereco_rua = endereco_rua;
    }

    public String getEndereco_numero() {  
        return endereco_numero;
    }

    public void setEndereco_numero(String endereco_numero) {
        this.endereco_numero = endereco_numero;
    }

    public String getEndereco_bairro() { 
        return endereco_bairro;
    }

    public void setEndereco_bairro(String endereco_bairro) {  
        this.endereco_bairro = endereco_bairro;
    }

    public String getEndereco_complemento() {  
        return endereco_complemento;
    }

    public void setEndereco_complemento(String endereco_complemento) {  
        this.endereco_complemento = endereco_complemento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", aniversario=" + aniversario +
                ", whatsapp='" + whatsapp + '\'' +
                ", endereco_rua='" + endereco_rua + '\'' +
                ", endereco_numero='" + endereco_numero + '\'' +
                ", endereco_bairro='" + endereco_bairro + '\'' +
                ", endereco_complemento='" + endereco_complemento + '\'' +
                '}';
    }
}
