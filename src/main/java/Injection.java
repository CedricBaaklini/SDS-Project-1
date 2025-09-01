//This class demonstrates a login that is vulnerable to SQL Injections
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Injection {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/SDS%20Project%201";
        String dbUser = "root";
        String dbPassword = "raqm777!";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Scanner scan = new Scanner(System.in)) {
            System.out.println("Enter username: ");
            String username = scan.nextLine();

            System.out.println("Enter password: ");
            String password = scan.nextLine();

            /*
             * This input is extremely vulnerable to an injection attack.
             * The string, "sql", is a confusing cluster of concatenations
             */

            String sql = "SELECT * FROM `SDS Project 1`.Users WHERE Username='" + username + "' AND Password='" + password + "'";

            System.out.println("Executing: " + sql);

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Login successful! Welcome " + rs.getString("username"));
            } else {
                System.out.println("Login failed.");
            }
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
