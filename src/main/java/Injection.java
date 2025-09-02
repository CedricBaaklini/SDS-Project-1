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
        String dbPassword = "opensesame";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Scanner scan = new Scanner(System.in)) {
            System.out.println("Enter username: ");
            String username = scan.nextLine();

            System.out.println("Enter password: ");
            String password = scan.nextLine();

            /*
             * This input is extremely vulnerable to an injection attack.
             * The string, "sql", is a confusing cluster of concatenations.
             * This can and will, easily confuse the console.
             * It cannot tell the difference between a string and a legit SQL statement.
             * As a consequence, injections are very likely to be done with ease.
             */

            String sql = "SELECT * FROM `SDS Project 1`.Users WHERE Username='" + username + "' AND Password='" + password + "'";

            System.out.println("Executing: " + sql);

            //The variables are sent directly into the database without any kind of validation or cleanup.
            Statement stmt = conn.createStatement();

            /*
             * Here, the compiler gives me a warning that the parameter "sql" is unsafe.
             * A parameter being unsafe means that injections are prone to happen.
             * Once the query is executed, it will log me in regardless if the password is correct.
             */
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
