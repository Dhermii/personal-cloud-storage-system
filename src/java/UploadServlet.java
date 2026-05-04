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
import javax.servlet.http.HttpSession;
import data.dbconnection;
import java.io.File;
import javax.servlet.http.Part;
import java.sql.*;
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author herme
 */
@WebServlet(urlPatterns = {"/UploadServlet"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();

        
        if (session.getAttribute("userid") == null) {
            response.sendRedirect("index.html");
            return;
        }

        int userId = (int) session.getAttribute("userid");

       
        Part filePart = request.getPart("file");
        String originalFileName = filePart.getSubmittedFileName();

        
        if (originalFileName == null || originalFileName.isEmpty()) {
            response.sendRedirect("mainpage.jsp");
            return;
        }

        
        String uploadPath = "C:\\Users\\herme\\Documents\\personal cloud storage system";

        
        String userFolderPath = uploadPath + File.separator + userId;
        File userDir = new File(userFolderPath);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        
        String fileName = System.currentTimeMillis() + "_" + originalFileName;

        String filePath = userFolderPath + File.separator + fileName;
        int fileSize = (int) filePart.getSize();

        
        String fileType = "other";
        int dotIndex = originalFileName.lastIndexOf(".");

        if (dotIndex > 0) {
            fileType = originalFileName.substring(dotIndex + 1).toLowerCase();
        }

        
        filePart.write(filePath);

        
        try {
            Connection con = dbconnection.getcon();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO files(userid, file_name, file_path, file_size, file_type) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setInt(1, userId);
            ps.setString(2, originalFileName);
            ps.setString(3, filePath);
            ps.setInt(4, fileSize);
            ps.setString(5, fileType); // ✅

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("mainpage.jsp");
    }
}