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
        // 1️⃣ Get email and password and trim spaces
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        try {
            // 2️⃣ Connect to database
            Connection con = dbconnection.getcon();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT userid, name FROM user WHERE email=? AND password=?");
            ps.setString(1, email);

            String hashedPassword = userpassword.hashPassword(password);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            // 3️⃣ Check if user exists
            if (rs.next()) {

                int userId = rs.getInt("userid");
                String name = rs.getString("name");

                HttpSession session = request.getSession();
                session.setAttribute("userid", userId);   // ⭐ IMPORTANT
                session.setAttribute("name", name);

                response.sendRedirect("mainpage.jsp");

            } else {
                // 7️⃣ Invalid login
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("index.html").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
