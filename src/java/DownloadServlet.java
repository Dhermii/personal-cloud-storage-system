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
       int fileId = Integer.parseInt(request.getParameter("id"));

        try {
            Connection con = dbconnection.getcon();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM files WHERE file_id=?"
            );
            ps.setInt(1, fileId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String filePath = rs.getString("file_path");
                String fileName = rs.getString("file_name"); 

                File file = new File(filePath);

                
                if (!file.exists()) {
                    response.getWriter().println("File not found on server!");
                    return;
                }

                
                response.setContentType("application/octet-stream");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + fileName + "\"");

              
                FileInputStream fis = new FileInputStream(file);
                OutputStream os = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                os.flush();
                fis.close();
                os.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
   
