/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dbconnection;
import data.userpassword;

/**
 *
 * @author herme
 */
@WebServlet(urlPatterns = {"/registerservlet"})
public class registerservlet extends HttpServlet {
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmpassword");

    try {

       
        if (!password.equals(confirmPassword)) {
            response.getWriter().println("Passwords do not match!");
            return;
        }

        
        String hashedPassword = userpassword.hashPassword(password);

        
        Connection con = dbconnection.getcon();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO user(name, email, password) VALUES (?, ?, ?)"
        );

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, hashedPassword);

        ps.executeUpdate();

        con.close();

        
        response.sendRedirect("index.html");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
