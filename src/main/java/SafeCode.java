//This class demonstrates a login that has robust protection against injections.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SafeCode {
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
             * User input is now protected from injections.
             * The variable "sql" is now one organized and unified string instead of a concatenated mess.
             * It even has a placeholder for the data!
             * They are represented by a "?".
             */
            String sql = "SELECT * FROM `SDS Project 1`.Users WHERE Username=? AND Password=?";

            System.out.println("Executing: " + sql);

            //The interface "PreparedStatement" validates, normalizes, sanitizes, and parse the entry.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            /*
             * The inputs are then indexed to parameters via the "setString" method.
             * This packages the inputs into secure and isolated variables.
             * Each index corresponds to the order of the "?".
             * For instance, the first "?" will be represented by the first index.
             * The second index will be represented by the second "?".
             * So on and so forth.
             */
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            //Protection ends here.

            //Parameter is not required for the PrepareStatement version of executeQuery().
            ResultSet rs = pstmt.executeQuery();

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
