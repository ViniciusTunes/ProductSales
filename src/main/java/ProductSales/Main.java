package ProductSales;

import ProductSales.guiManager.ProductForm;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import static ProductSales.Database.getConnection;

public class Main extends JPanel {

    private static final int PREF_W = 1200;
    private static final int PREF_H = 600;
    private JButton sairButton;
    private JButton salvarButton;
    private JPanel Main;
    private JTable table1;
    private JButton deletarButton;
    private JButton pesquisarButton;
    private JTextField txtProduto;
    private JTextField txtValor;
    private JTextField txtQuantidade;
    private JButton atualizarButton;
    private JLabel produtoLabel;
    private JLabel valorLabel;
    private JTextField txtPesquisar;
    private JLabel quantidadeLabel;
    private JScrollPane tbListagem;
    private final ProductForm productForm;
    private static final Connection conn = Database.getConnection();

    public Main(ProductImplementation productImplementation) {
        getConnection();
        table_load();

        productForm = new ProductForm(txtProduto, txtValor, txtQuantidade);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = productForm.createProductFromFields();
                if (product != null) {
                    productImplementation.salvar(product);
                    productForm.clearFields();
                    table_load(); // Recarrega a tabela após salvar.
                }
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código começando aqui - ATUALIZAR:
                try {
                    Integer id = Integer.valueOf(txtPesquisar.getText());
                    Product product = productImplementation.pesquisar(id);
                    if (product != null) {
                        getFields(product);
                        productImplementation.atualizar(product);
                        productForm.clearFields();
                        table_load(); // Recarrega a tabela após salvar.
                    } else {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor ou Quantidade inválidos!");
                }
            }
        });
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textPesquisar = txtPesquisar.getText();

                // Código começando aqui - PESQUISAR:
                if (textPesquisar.isEmpty()) {
                    table_load();
                    return;
                }

                try {
                    Integer id = Integer.valueOf(txtPesquisar.getText());
                    Product product = productImplementation.pesquisar(id);

                    if (product != null) {
                        DefaultTableModel model = (DefaultTableModel) table1.getModel();
                        model.setRowCount(0);
                        model.addRow(new Object[]{product.getId(), product.getProduto(),
                                product.getValor(), product.getQuantidade(), product.getTotal(),
                                product.getData()});
                        getFieldsProduct(product);
                    } else {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor inválido!");
                }

            }
        });
        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Integer id = Integer.valueOf(txtPesquisar.getText());
                    Product product = productImplementation.pesquisar(id);

                    if (product!= null) {
                        productImplementation.deletar(product);
                        productForm.clearFields();
                        table_load(); // Recarrega a tabela após salvar.
                    } else {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor inválido!");
                }
                Integer id = Integer.valueOf(txtPesquisar.getText());
            }
        });
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();

        JFrame frame = new JFrame("Main");
        JTable table1 = new JTable();
        frame.setContentPane(new Main(new ProductImplementation()).Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PREF_W, PREF_H); // Set to a larger size, e.g., 800x600
        frame.setVisible(true);
    }

    public void table_load() {
        try {
            String sql = "SELECT * FROM PRODUTOS";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            // Garante que table1 não é nula.
            if (table1 == null) {
                table1 = new JTable();
            }
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(columnNames);

            // Clear existing rows
            model.setRowCount(0);

            while (rs.next()) {
                String ID = rs.getString("ID");
                String produto = rs.getString("PRODUTO");
                double valor = rs.getDouble("VALOR");
                int quantidade = rs.getInt("QUANTIDADE");
                double total = rs.getDouble("TOTAL");
                String data = rs.getString("DATA");

                // Add row to the model:
                model.addRow(new Object[]{ID, produto, valor, quantidade, total, data});
            }
        } catch (SQLException e) {
            throw new Error("Erro ao inserir na tabela." + e);
        }
    }

    private void getFieldsProduct(Product product) {
        txtProduto.setText(product.getProduto());
        txtValor.setText(String.valueOf(product.getValor()));
        txtQuantidade.setText(String.valueOf(product.getQuantidade()));
    }

    private void getFields(Product product) {
        product.setProduto(txtProduto.getText());
        product.setValor(Double.parseDouble(txtValor.getText()));
        product.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
    }

    private void createUIComponents() {
        produtoLabel = new JLabel(new ImageIcon("produtos.png"));
        valorLabel = new JLabel(new ImageIcon("valor.png"));
        quantidadeLabel = new JLabel(new ImageIcon("quantidade.png"));
    }
}