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
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/personal_cloud_storage_systemdb",
                "root","");
            return con;
        }
        catch(Exception e){
        System.out.println(e);
        return null;
        }
    }
}
