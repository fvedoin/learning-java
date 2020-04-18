package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trabalho02", "postgres", "28461511");
            System.out.println("Conectado com sucesso");
        } catch (SQLException e){
            System.out.println("Erro - conexão"+e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Erro - Driver"+e.getMessage());
        }
        return con;
    }
}
