import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/SDS%20Project%201";
        String user = "root";
        String password = "opensesame";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected succesfully!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
