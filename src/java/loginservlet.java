/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.dbconnection;
import data.userpassword;

/**
 *
 * @author herme
 */
@WebServlet(urlPatterns = {"/loginservlet"})
public class loginservlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         response.setContentType("text/html");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            if (email == null || password == null) {
                response.getWriter().println("Missing login fields!");
                return;
            }

            Connection con = dbconnection.getcon();

            String sql = "SELECT userid, name, password FROM users WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String dbPassword = rs.getString("password");

                String hashedPassword = userpassword.hashPassword(password);

                if (!dbPassword.equals(hashedPassword)) {
                    response.getWriter().println("Wrong password!");
                    return;
                }

                int userId = rs.getInt("userid");
                String name = rs.getString("name");

                HttpSession session = request.getSession();
                session.setAttribute("userid", userId);
                session.setAttribute("name", name);

                response.sendRedirect(request.getContextPath() + "/mainpage.jsp");

            } else {
                response.getWriter().println("User not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("ERROR LOGIN: " + e.getMessage());
        }
    }
}