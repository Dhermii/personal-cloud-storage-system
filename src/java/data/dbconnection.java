/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;
import java.sql.*;

/**
 *
 * @author herme
 */
public class dbconnection {
    public static Connection getcon(){
         Connection con = null;        

        try {
            String url = "jdbc:mysql://switchyard.proxy.rlwy.net:35589/railway";

            String user = "root";
            String password = "rKGbDQeYiUPakTnYuZoIDBbmqOGkmRMw";

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }

        return con;
    }
}
