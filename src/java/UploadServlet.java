/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.cloudinary.utils.ObjectUtils;
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
import java.util.Map;
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

        try {

            
            Map uploadResult = CloudinaryConfig.cloudinary.uploader().upload(
                    filePart.getInputStream(),
                    ObjectUtils.asMap("resource_type", "auto")
            );

            String fileUrl = (String) uploadResult.get("secure_url");

            String fileType = originalFileName.contains(".")
                    ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                    : "other";

            long fileSize = filePart.getSize();

           
            Connection con = dbconnection.getcon();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO files(userid, file_name, file_path, file_size, file_type) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setInt(1, userId);
            ps.setString(2, originalFileName);
            ps.setString(3, fileUrl); 
            ps.setLong(4, fileSize);
            ps.setString(5, fileType);

            ps.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("mainpage.jsp");
    }
}