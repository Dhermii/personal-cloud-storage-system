/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import data.dbconnection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author herme
 */
@WebServlet(urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         int fileId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = dbconnection.getcon();

            
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT file_path FROM files WHERE file_id=?"
            );
            ps1.setInt(1, fileId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                String path = rs.getString("file_path");
                File file = new File(path);
                if (file.exists()) file.delete();
            }

            
            PreparedStatement ps2 = con.prepareStatement(
                "DELETE FROM files WHERE file_id=?"
            );
            ps2.setInt(1, fileId);
            ps2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("mainpage.jsp");
    }
}
    
