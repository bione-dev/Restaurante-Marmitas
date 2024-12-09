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
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida! Por favor, insira um número entre 0 e 9.");
                scanner.next(); // descarta a entrada inválida
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

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
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida.");
                    break;
            }
        } while (opcao != 0);
    }

    /**
     * Método auxiliar para buscar clientes por nome utilizando listarClientes()
     */
    private static List<Cliente> buscarClientesPorNome(String nome) {
        List<Cliente> todosClientes = clienteDAO.listarClientes();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente cliente : todosClientes) {
            if (cliente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }

    private static void cadastrarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Data de Aniversário (DD-MM-AAAA): ");
            String aniversarioInput = scanner.nextLine();
            LocalDate aniversario = LocalDate.parse(aniversarioInput, dateFormatter);

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
            String endereco_rua = scanner.nextLine();
            System.out.print("Endereço - Número: ");
            String endereco_numero = scanner.nextLine();
            System.out.print("Endereço - Bairro: ");
            String endereco_bairro = scanner.nextLine();
            System.out.print("Endereço - Complemento: ");
            String endereco_complemento = scanner.nextLine();

            Cliente cliente = new Cliente(0, nome, aniversario, whatsapp, endereco_rua, endereco_numero, endereco_bairro, endereco_complemento);
            clienteDAO.adicionarCliente(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void editarCliente() {
        try {
            System.out.print("ID do Cliente a ser editado: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Cliente cliente = clienteDAO.buscarCliente(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            System.out.print("Novo Nome (" + cliente.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                cliente.setNome(nome);
            }

            System.out.print("Nova Data de Aniversário (" + cliente.getAniversario().format(dateFormatter) + " - DD-MM-AAAA): ");
            String aniversarioInput = scanner.nextLine();
            if (!aniversarioInput.trim().isEmpty()) {
                LocalDate aniversario = LocalDate.parse(aniversarioInput, dateFormatter);
                cliente.setAniversario(aniversario);
            }

            String whatsapp;
            while (true) {
                System.out.print("Novo WhatsApp (" + cliente.getWhatsapp() + " - Formato: (xx) xxxxx-xxxx): ");
                whatsapp = scanner.nextLine();
                if (whatsapp.trim().isEmpty()) {
                    break; // Mantém o valor atual
                }
                if (whatsapp.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
                    cliente.setWhatsapp(whatsapp);
                    break;
                } else {
                    System.out.println("Formato inválido! Insira no formato (xx) xxxxx-xxxx.");
                }
            }

            System.out.print("Novo Endereço - Rua (" + cliente.getEndereco_rua() + "): ");
            String endereco_rua = scanner.nextLine();
            if (!endereco_rua.trim().isEmpty()) {
                cliente.setEndereco_rua(endereco_rua);
            }

            System.out.print("Novo Endereço - Número (" + cliente.getEndereco_numero() + "): ");
            String endereco_numero = scanner.nextLine();
            if (!endereco_numero.trim().isEmpty()) {
                cliente.setEndereco_numero(endereco_numero);
            }

            System.out.print("Novo Endereço - Bairro (" + cliente.getEndereco_bairro() + "): ");
            String endereco_bairro = scanner.nextLine();
            if (!endereco_bairro.trim().isEmpty()) {
                cliente.setEndereco_bairro(endereco_bairro);
            }

            System.out.print("Novo Endereço - Complemento (" + cliente.getEndereco_complemento() + "): ");
            String endereco_complemento = scanner.nextLine();
            if (!endereco_complemento.trim().isEmpty()) {
                cliente.setEndereco_complemento(endereco_complemento);
            }

            clienteDAO.atualizarCliente(cliente);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }

    private static void excluirCliente() {
        try {
            System.out.print("ID do Cliente a ser excluído: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Cliente cliente = clienteDAO.buscarCliente(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            clienteDAO.excluirCliente(id);
            System.out.println("Cliente excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    private static void listarTodosClientes() {
        try {
            System.out.println("Listando todos os clientes cadastrados:");
            List<Cliente> clientes = clienteDAO.listarClientes();
            for (Cliente cliente : clientes) {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Aniversário: " + cliente.getAniversario().format(dateFormatter));
                System.out.println("WhatsApp: " + cliente.getWhatsapp());
                System.out.println("Endereço Rua: " + cliente.getEndereco_rua());
                System.out.println("Número: " + cliente.getEndereco_numero());
                System.out.println("Bairro: " + cliente.getEndereco_bairro());
                System.out.println("Complemento: " + cliente.getEndereco_complemento());
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private static void cadastrarPedido() {
        try {
            System.out.println("Cadastrar Pedido:");

            System.out.print("Nome do Cliente: ");
            String nomeCliente = scanner.nextLine();

            // Usando método auxiliar para buscar clientes por nome
            List<Cliente> clientesEncontrados = buscarClientesPorNome(nomeCliente);
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
                scanner.nextLine(); // Consumir a quebra de linha

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
                Produto produtoEncontrado = produtoDAO.buscarProdutoPorNome(nomeProduto); 

                if (produtoEncontrado == null) {
                    System.out.println("Produto não encontrado!");
                } else {
                    produtos.add(produtoEncontrado); 
                    System.out.println("Produto adicionado ao pedido.");
                }

                System.out.print("Deseja adicionar mais produtos? (s/n): ");
                escolha = scanner.nextLine();
            } while (escolha.equalsIgnoreCase("s"));

            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto adicionado ao pedido. Pedido não criado.");
                return;
            }

            String formaEntrega;
            while (true) {
                System.out.print("Forma de Entrega (Digite 'Delivery' ou 'Retirada'): ");
                formaEntrega = scanner.nextLine();
                if (formaEntrega.equalsIgnoreCase("Delivery") || formaEntrega.equalsIgnoreCase("Retirada")) {
                    break;
                } else {
                    System.out.println("Forma de entrega inválida! Por favor, digite 'Delivery' ou 'Retirada'.");
                }
            }

            String formaPagamento;
            while (true) {
                System.out.print("Forma de Pagamento (Digite 'Pix', 'Dinheiro' ou 'Cartão'): ");
                formaPagamento = scanner.nextLine();
                if (formaPagamento.equalsIgnoreCase("Pix") || formaPagamento.equalsIgnoreCase("Dinheiro") || formaPagamento.equalsIgnoreCase("Cartão")) {
                    break;
                } else {
                    System.out.println("Forma de pagamento inválida! Por favor, digite 'Pix', 'Dinheiro' ou 'Cartão'.");
                }
            }

            Pedido pedido = new Pedido();
            pedido.setDataHora(LocalDateTime.now());
            pedido.setCliente(cliente);
            pedido.setProdutos(produtos); 
            pedido.setFormaEntrega(formaEntrega);
            pedido.setFormaPagamento(formaPagamento);
            pedido.setEntregaConfirmada(false);

            pedidoDAO.adicionarPedido(pedido);
            System.out.println("Pedido cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar pedido: " + e.getMessage());
        }
    }

    private static void cancelarPedido() {
        try {
            System.out.print("ID do Pedido a ser cancelado: ");
            int pedidoId = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
            if (pedido == null) {
                System.out.println("Pedido não encontrado!");
                return;
            }

            pedidoDAO.cancelarPedido(pedidoId);
            System.out.println("Pedido cancelado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    private static void listarTodosPedidos() {
        try {
            System.out.println("Listando todos os pedidos:");
            List<Pedido> pedidos = pedidoDAO.listarPedidos();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            for (Pedido pedido : pedidos) {
                System.out.println("ID do Pedido: " + pedido.getId());
                System.out.println("Data e Hora: " + pedido.getDataHora().format(dtf));
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
        } catch (Exception e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    private static void confirmarEntregaPedido() {
        try {
            System.out.print("ID do Pedido a confirmar entrega: ");
            int pedidoId = scanner.nextInt();
            scanner.nextLine(); 

            Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
            if (pedido != null) {
                if (!pedido.isEntregaConfirmada()) {
                    pedidoDAO.confirmarEntrega(pedidoId);
                    System.out.println("Entrega do pedido confirmada com sucesso!");
                } else {
                    System.out.println("Entrega já estava confirmada para este pedido.");
                }
            } else {
                System.out.println("Pedido não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao confirmar entrega do pedido: " + e.getMessage());
        }
    }

    private static void imprimirDadosMotoboy() {
        try {
            System.out.println("Imprimir Dados para o Motoboy:");
            System.out.print("ID do Pedido: ");
            int pedidoId = scanner.nextInt();
            scanner.nextLine(); 

            Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
            if (pedido != null) {
                if ("Delivery".equalsIgnoreCase(pedido.getFormaEntrega())) {
                    System.out.println("Dados do Pedido para Entrega:");
                    System.out.println("Cliente: " + pedido.getCliente().getNome());
                    System.out.println("Endereço: " + pedido.getCliente().getEndereco_rua() + ", " +
                                       pedido.getCliente().getEndereco_numero() + " - " +
                                       pedido.getCliente().getEndereco_bairro() +
                                       (pedido.getCliente().getEndereco_complemento() != null && !pedido.getCliente().getEndereco_complemento().isEmpty()
                                               ? " (" + pedido.getCliente().getEndereco_complemento() + ")" : ""));
                    System.out.println("WhatsApp: " + pedido.getCliente().getWhatsapp());
                    System.out.println("Produtos:");
                    for (Produto produto : pedido.getProdutos()) {
                        System.out.println("- " + produto.getNome());
                    }
                    System.out.println("Data e Hora do Pedido: " + pedido.getDataHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                    System.out.println("Observação: Confirmar entrega no número de contato.");
                } else {
                    System.out.println("Este pedido não é para entrega (Delivery).");
                }
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao imprimir dados para o motoboy: " + e.getMessage());
        }
    }
}
