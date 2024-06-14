package ProductSales;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImplementation {
    private static final Connection conn = Database.getConnection();

    public void salvar(Product product) {
        try {
            String sql = "INSERT INTO PRODUTOS (PRODUTO, VALOR, QUANTIDADE, TOTAL, DATA) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, product.getProduto());
            ps.setDouble(2, product.getValor());
            ps.setInt(3, product.getQuantidade());
            ps.setDouble(4, product.getTotal());
            ps.setString(5, product.getData());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Adicionado!");
        } catch (SQLException e) {
            throw new Error("Erro ao tentar salvar no Banco de Dados." + e);
        }
    }

    public Product pesquisar(Integer id) {
        try {
            String sql = "SELECT * FROM PRODUTOS WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                Integer ID = Integer.valueOf(rs.getString("ID"));
                String PRODUTO = rs.getString("PRODUTO");
                Double VALOR = Double.valueOf(rs.getString("VALOR"));
                Integer QUANTIDADE = Integer.valueOf(rs.getString("QUANTIDADE"));
                Double TOTAL = Double.valueOf(rs.getString("TOTAL"));

                return new Product(ID, PRODUTO, VALOR, QUANTIDADE, TOTAL);
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                return null;
            }
        } catch (SQLException e) {
            throw new Error("Erro ao pesquisar no Banco de Dados: " + e);
        }
    }

    public void atualizar(Product product) {
        try {
            String sql = "UPDATE PRODUTOS SET PRODUTO = ?, VALOR = ?, QUANTIDADE = ? WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, product.getProduto());
            ps.setDouble(2, product.getValor());
            ps.setInt(3, product.getQuantidade());
            ps.setInt(4, product.getId());

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
        } catch (SQLException e) {
            throw new Error("Erro ao atualizar o Banco de Dados: " + e);
        }
    }

    public void deletar(Product product) {
        try {
            String sql = "DELETE FROM PRODUTOS WHERE ID =?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, product.getId());

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Produto deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
            }
        } catch (SQLException e) {
            throw new Error("Erro ao deletar no Banco de Dados: " + e);
        }
    }
}
