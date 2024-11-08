import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class TesteCRUD {
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ProdutoDAO produtoDAO = new ProdutoDAO();
    private static PedidoDAO pedidoDAO = new PedidoDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- Sistema de Gestão de Clientes, Produtos e Pedidos ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Excluir Cliente");
            System.out.println("4. Listar Todos os Clientes");
            System.out.println("5. Cadastrar Pedido");
            System.out.println("6. Cancelar Pedido");
            System.out.println("7. Listar Todos os Pedidos");
            System.out.println("8. Confirmar Entrega de Pedido");
            System.out.println("9. Imprimir Dados para o Motoboy");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    editarCliente();
                    break;
                case 3:
                    excluirCliente();
                    break;
                case 4:
                    listarTodosClientes();
                    break;
                case 5:
                    cadastrarPedido();
                    break;
                case 6:
                    cancelarPedido();
                    break;
                case 7:
                    listarTodosPedidos();
                    break;
                case 8:
                    confirmarEntregaPedido();
                    break;
                case 9:
                    imprimirDadosMotoboy();
                    break;
                case 0:
                    System.out.println("Saindo do sistema.");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Data de Aniversário (DD-MM-AAAA): ");
        LocalDate aniversario = LocalDate.parse(scanner.nextLine(), dateFormatter);

        String whatsapp;
        while (true) {
            System.out.print("WhatsApp (Formato: (xx) xxxxx-xxxx): ");
            whatsapp = scanner.nextLine();
            if (whatsapp.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
                break;
            } else {
                System.out.println("Formato inválido! Insira no formato (xx) xxxxx-xxxx.");
            }
        }

        System.out.print("Endereço - Rua: ");
        String enderecoRua = scanner.nextLine();
        System.out.print("Endereço - Número: ");
        String enderecoNumero = scanner.nextLine();
        System.out.print("Endereço - Bairro: ");
        String enderecoBairro = scanner.nextLine();
        System.out.print("Endereço - Complemento: ");
        String enderecoComplemento = scanner.nextLine();

        Cliente cliente = new Cliente(0, nome, aniversario, whatsapp, enderecoRua, enderecoNumero, enderecoBairro, enderecoComplemento);
        clienteDAO.adicionarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void editarCliente() {
        System.out.print("ID do Cliente a ser editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteDAO.buscarCliente(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Novo Nome (" + cliente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Nova Data de Aniversário (" + cliente.getAniversario().format(dateFormatter) + " - DD-MM-AAAA): ");
        LocalDate aniversario = LocalDate.parse(scanner.nextLine(), dateFormatter);

        String whatsapp;
        while (true) {
            System.out.print("Novo WhatsApp (" + cliente.getWhatsapp() + " - Formato: (xx) xxxxx-xxxx): ");
            whatsapp = scanner.nextLine();
            if (whatsapp.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
                break;
            } else {
                System.out.println("Formato inválido! Insira no formato (xx) xxxxx-xxxx.");
            }
        }

        System.out.print("Novo Endereço - Rua (" + cliente.getEnderecoRua() + "): ");
        String enderecoRua = scanner.nextLine();
        System.out.print("Novo Endereço - Número (" + cliente.getEnderecoNumero() + "): ");
        String enderecoNumero = scanner.nextLine();
        System.out.print("Novo Endereço - Bairro (" + cliente.getEnderecoBairro() + "): ");
        String enderecoBairro = scanner.nextLine();
        System.out.print("Novo Endereço - Complemento (" + cliente.getEnderecoComplemento() + "): ");
        String enderecoComplemento = scanner.nextLine();

        cliente.setNome(nome);
        cliente.setAniversario(aniversario);
        cliente.setWhatsapp(whatsapp);
        cliente.setEnderecoRua(enderecoRua);
        cliente.setEnderecoNumero(enderecoNumero);
        cliente.setEnderecoBairro(enderecoBairro);
        cliente.setEnderecoComplemento(enderecoComplemento);

        clienteDAO.atualizarCliente(cliente);
        System.out.println("Cliente atualizado com sucesso!");
    }

    private static void excluirCliente() {
        System.out.print("ID do Cliente a ser excluído: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        clienteDAO.excluirCliente(id);
        System.out.println("Cliente excluído com sucesso!");
    }

    private static void listarTodosClientes() {
        System.out.println("Listando todos os clientes cadastrados:");
        List<Cliente> clientes = clienteDAO.listarClientes();
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Aniversário: " + cliente.getAniversario().format(dateFormatter));
            System.out.println("WhatsApp: " + cliente.getWhatsapp());
            System.out.println("Endereço Rua: " + cliente.getEnderecoRua());
            System.out.println("Número: " + cliente.getEnderecoNumero());
            System.out.println("Bairro: " + cliente.getEnderecoBairro());
            System.out.println("Complemento: " + cliente.getEnderecoComplemento());
            System.out.println("-----------------------------");
        }
    }

    private static void cadastrarPedido() {
        System.out.println("Cadastrar Pedido:");

        System.out.print("Nome do Cliente: ");
        String nomeCliente = scanner.nextLine();

        List<Cliente> clientesEncontrados = clienteDAO.buscarClientesPorNome(nomeCliente);
        Cliente cliente = null;

        if (clientesEncontrados.isEmpty()) {
            System.out.println("Cliente não encontrado!");
            return;
        } else if (clientesEncontrados.size() == 1) {
            cliente = clientesEncontrados.get(0);
            System.out.println("Cliente encontrado: " + cliente.getNome());
        } else {
            System.out.println("Clientes encontrados:");
            for (int i = 0; i < clientesEncontrados.size(); i++) {
                System.out.println((i + 1) + ". " + clientesEncontrados.get(i).getNome());
            }
            System.out.print("Escolha o número do cliente: ");
            int indiceCliente = scanner.nextInt();
            scanner.nextLine();

            if (indiceCliente < 1 || indiceCliente > clientesEncontrados.size()) {
                System.out.println("Número do cliente inválido.");
                return;
            }

            cliente = clientesEncontrados.get(indiceCliente - 1);
            System.out.println("Cliente selecionado: " + cliente.getNome());
        }

        List<Produto> produtos = new ArrayList<>();
        String escolha;
        do {
            System.out.print("Nome do Produto: ");
            String nomeProduto = scanner.nextLine();
            List<Produto> produtosEncontrados = produtoDAO.buscarProdutoPorNome(nomeProduto);

            if (produtosEncontrados.isEmpty()) {
                System.out.println("Produto não encontrado!");
            } else {
                System.out.println("Produtos encontrados:");
                for (int i = 0; i < produtosEncontrados.size(); i++) {
                    Produto produto = produtosEncontrados.get(i);
                    System.out.printf("%d - %s (ID: %d, Preço: %.2f)\n", i + 1, produto.getNome(), produto.getId(), produto.getPreco());
                }
                System.out.print("Escolha o número do produto desejado: ");
                int numeroProduto = scanner.nextInt();
                scanner.nextLine();

                if (numeroProduto > 0 && numeroProduto <= produtosEncontrados.size()) {
                    produtos.add(produtosEncontrados.get(numeroProduto - 1));
                    System.out.println("Produto adicionado ao pedido.");
                } else {
                    System.out.println("Número do produto inválido.");
                }
            }

            System.out.print("Deseja adicionar mais produtos? (s/n): ");
            escolha = scanner.nextLine();
        } while (escolha.equalsIgnoreCase("s"));

        System.out.print("Forma de Entrega (Digite 'Delivery' ou 'Retirada'): ");
        String formaEntrega = scanner.nextLine();

        System.out.print("Forma de Pagamento (Digite 'Pix', 'Dinheiro' ou 'Cartão'): ");
        String formaPagamento = scanner.nextLine();

        Pedido pedido = new Pedido();
        pedido.setDataHora(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setProdutos(produtos);
        pedido.setFormaEntrega(formaEntrega);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setEntregaConfirmada(false);

        pedidoDAO.adicionarPedido(pedido);
        System.out.println("Pedido cadastrado com sucesso!");
    }

    private static void cancelarPedido() {
        System.out.print("ID do Pedido a ser cancelado: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        pedidoDAO.cancelarPedido(pedidoId);
        System.out.println("Pedido cancelado com sucesso!");
    }

    private static void listarTodosPedidos() {
        System.out.println("Listando todos os pedidos:");
        List<Pedido> pedidos = pedidoDAO.listarPedidos();
        for (Pedido pedido : pedidos) {
            System.out.println("ID do Pedido: " + pedido.getId());
            System.out.println("Data e Hora: " + pedido.getDataHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            System.out.println("Cliente: " + pedido.getCliente().getNome());
            System.out.println("Produtos:");
            for (Produto produto : pedido.getProdutos()) {
                System.out.println("- " + produto.getNome());
            }
            System.out.println("Forma de Entrega: " + pedido.getFormaEntrega());
            System.out.println("Forma de Pagamento: " + pedido.getFormaPagamento());
            System.out.println("Entrega Confirmada: " + (pedido.isEntregaConfirmada() ? "Sim" : "Não"));
            System.out.println("-----------------------------");
        }
    }

    private static void confirmarEntregaPedido() {
        System.out.print("ID do Pedido a confirmar entrega: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = pedidoDAO.buscarPedido(pedidoId);
        if (pedido != null) {
            pedidoDAO.confirmarEntrega(pedidoId);
            System.out.println("Entrega do pedido confirmada com sucesso!");
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    private static void imprimirDadosMotoboy() {
        System.out.println("Imprimir Dados para o Motoboy:");
        System.out.print("ID do Pedido: ");
        int pedidoId = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = pedidoDAO.buscarPedido(pedidoId);
        if (pedido != null) {
            if ("Delivery".equalsIgnoreCase(pedido.getFormaEntrega())) {
                System.out.println("Dados do Pedido para Entrega:");
                System.out.println("Cliente: " + pedido.getCliente().getNome());
                System.out.println("Endereço: " + pedido.getCliente().getEnderecoRua() + ", " +
                                   pedido.getCliente().getEnderecoNumero() + " - " +
                                   pedido.getCliente().getEnderecoBairro() + ", " +
                                   pedido.getCliente().getEnderecoComplemento());
                System.out.println("WhatsApp: " + pedido.getCliente().getWhatsapp());
                System.out.println("Produtos:");
                for (Produto produto : pedido.getProdutos()) {
                    System.out.println("- " + produto.getNome());
                }
                System.out.println("Data e Hora do Pedido: " + pedido.getDataHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.println("Observação: Confirmar entrega no número de contato.");
            } else {
                System.out.println("Este pedido não é para entrega.");
            }
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }
}
