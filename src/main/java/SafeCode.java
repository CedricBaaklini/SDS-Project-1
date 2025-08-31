import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SafeCode {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/SDS%20Project%201";
        String dbUser = "root";
        String dbPassword = "raqm777!";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Scanner scan = new Scanner(System.in)) {
            System.out.println("Enter username: ");
            String username = scan.nextLine();
            System.out.println("Enter password: ");

            String password = scan.nextLine();

            //User input is now protected from injections.
            String sql = "SELECT * FROM `SDS Project 1`.Users + WHERE Username=? AND password=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery(sql);

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
