/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author herme
 */
public class table {
     public static void main(String[] args){
         //Connection con=null;
         //Statement st=null;
     /*   Connection con=dbconnection.getcon();         
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Connection successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Connection failed!");
        }
        
        try{
            Statement st=con.createStatement();
           // st.executeUpdate("CREATE TABLE users (userid INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(255),email VARCHAR(255), password VARCHAR(255))");
          //  st.executeUpdate("CREATE TABLE files (file_id INT AUTO_INCREMENT PRIMARY KEY, userid INT,file_name VARCHAR(255), file_path VARCHAR(255),file_size int, upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,file_type VARCHAR(255), FOREIGN KEY (userid) REFERENCES user(userid))");
           JOptionPane.showMessageDialog(null, "table created successfully");
         }
        catch(Exception e){
              
              System.out.println(e);
        }
        
       */
       
      /* Connection con = null;
        PreparedStatement ps = null;
        try{
        
              con=dbconnection.getcon();
            if (con != null) {
                
                JOptionPane.showMessageDialog(null, "Connection successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Connection failed!");
                return;
            }
*/
            // -------------------------------
            // CREATE SUPERADMIN WITH HASHED PASSWORD
            // -------------------------------

         /*  String plainPassword = "admin";
String hashedPassword = userpassword.hashPassword(plainPassword);
            

            ps = con.prepareStatement(
                "INSERT INTO user(name,email,password) VALUES(?,?,?)"
            );

            ps.setString(1, "superadmin");                       
            ps.setString(2, "superadmin@gmail.com");
            ps.setString(3, hashedPassword); // ✅ hashed password
            

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Superadmin created successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      */
        }
     }
     
    

