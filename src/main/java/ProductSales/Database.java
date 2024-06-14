package ProductSales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String url = "jdbc:mysql://localhost:3308/vendadeprodutos";
    private static String user = "root";
    private static String password = "root";

    public Database() {
    }

    public static Connection getConnection() {
        try {
            Connection connection = null;
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado com sucesso ao banco de dados!");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
