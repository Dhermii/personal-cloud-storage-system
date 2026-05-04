/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import data.dbconnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 *
 * @author herme
 */
@WebServlet(urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int fileId = Integer.parseInt(request.getParameter("id"));

            Connection con = dbconnection.getcon();

            PreparedStatement ps = con.prepareStatement(
                "SELECT file_name, file_path FROM files WHERE file_id=?"
            );

            ps.setInt(1, fileId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String fileUrl = rs.getString("file_path");

               
                response.sendRedirect(fileUrl);

            } else {
                response.getWriter().println("File not found!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("DOWNLOAD ERROR: " + e.getMessage());
        }
    }
}