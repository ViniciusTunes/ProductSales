package ProductSales.guiManager;

import ProductSales.Product;

import javax.swing.*;

public class ProductForm {
    private JTextField txtProduto;
    private JTextField txtValor;
    private JTextField txtQuantidade;

    public ProductForm(JTextField txtProduto, JTextField txtValor, JTextField txtQuantidade) {
        this.txtProduto = txtProduto;
        this.txtValor = txtValor;
        this.txtQuantidade = txtQuantidade;
    }

    public Product createProductFromFields() {
        String produto;
        double valor;
        int quantidade;

        try {
            produto = txtProduto.getText();
            valor = Double.parseDouble(txtValor.getText());
            quantidade = Integer.parseInt(txtQuantidade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor ou Quantidade inválidos!");
            return null;
        }
        return new Product(produto, valor, quantidade);
    }

    public void clearFields() {
        txtProduto.setText("");
        txtValor.setText("");
        txtQuantidade.setText("");
        txtProduto.requestFocus();
    }
}
