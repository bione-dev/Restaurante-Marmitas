
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Pedido {
    private int id;
    private LocalDateTime dataHora; // Data e hora do pedido
    private Cliente cliente;
    private List<Produto> produtos;
    private String formaEntrega;  // Alterado para String para representar valor diretamente do banco
    private String formaPagamento; // Alterado para String para representar valor diretamente do banco
    private boolean entregaConfirmada;

    // Construtor padrão
    public Pedido() {}

    // Construtor para inicialização de todos os campos principais
    public Pedido(int id, Cliente cliente, List<Produto> produtos, String formaEntrega, String formaPagamento) {
        this.id = id;
        this.dataHora = LocalDateTime.now();
        this.cliente = cliente;
        this.produtos = produtos;
        this.formaEntrega = formaEntrega;
        this.formaPagamento = formaPagamento;
        this.entregaConfirmada = false;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            throw new IllegalArgumentException("A data e hora do pedido não podem ser nulas.");
        }
        this.dataHora = dataHora;
    }

    public LocalDate getData() {
        return this.dataHora.toLocalDate();
    }

    public void setData(LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula.");
        }
        this.dataHora = data.atStartOfDay();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("A lista de produtos não pode ser nula ou vazia.");
        }
        this.produtos = produtos;
    }

    public String getFormaEntrega() {
        return formaEntrega;
    }

    public void setFormaEntrega(String formaEntrega) {
        if (formaEntrega == null || formaEntrega.isEmpty()) {
            throw new IllegalArgumentException("A forma de entrega não pode ser nula ou vazia.");
        }
        this.formaEntrega = formaEntrega;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        if (formaPagamento == null || formaPagamento.isEmpty()) {
            throw new IllegalArgumentException("A forma de pagamento não pode ser nula ou vazia.");
        }
        this.formaPagamento = formaPagamento;
    }

    public boolean isEntregaConfirmada() {
        return entregaConfirmada;
    }

    public void setEntregaConfirmada(boolean entregaConfirmada) {
        this.entregaConfirmada = entregaConfirmada;
    }

    // Método para calcular o total do pedido
    public double calcularTotal() {
        return produtos.stream()
                       .mapToDouble(Produto::getPreco)
                       .sum();
    }

    // Método para imprimir o resumo do pedido
    public void imprimirResumoPedido() {
        System.out.println("Pedido ID: " + id);
        System.out.println("Data e Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
        System.out.println("Produtos:");
        produtos.forEach(produto -> System.out.println("- " + produto.getNome() + " (R$ " + produto.getPreco() + ")"));
        System.out.println("Forma de Entrega: " + formaEntrega);
        System.out.println("Forma de Pagamento: " + formaPagamento);
        System.out.println("Total: R$ " + calcularTotal());
        System.out.println("Entrega Confirmada: " + (entregaConfirmada ? "Sim" : "Não"));
        System.out.println("-----------------------------");
    }

    // Método toString para representação textual
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/A") +
                ", produtos=" + produtos +
                ", formaEntrega='" + formaEntrega + '\'' +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", entregaConfirmada=" + entregaConfirmada +
                ", total=" + calcularTotal() +
                '}';
    }
}
