public class TesteCRUD {
    public static void main(String[] args) {
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Listar todos os produtos
        System.out.println("Listando todos os produtos cadastrados:");
        for (Produto produto : produtoDAO.listarProdutos()) {
            System.out.println("ID: " + produto.getId());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("Tipo: " + produto.getTipo());
            System.out.println("-----------------------------");
        }
    }
}
