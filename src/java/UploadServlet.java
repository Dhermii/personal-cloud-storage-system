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
        response.setContentType("text/html");

        try {

            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("userid") == null) {
                response.sendRedirect("index.html");
                return;
            }

            int userId = (int) session.getAttribute("userid");

            Part filePart = request.getPart("file");
            String originalFileName = filePart.getSubmittedFileName();

            if (originalFileName == null || originalFileName.isEmpty()) {
                response.getWriter().println("No file selected!");
                return;
            }

            Map uploadResult = CloudinaryConfig.cloudinary.uploader().upload(
                    filePart.getInputStream(),
                    ObjectUtils.asMap("resource_type", "auto")
            );

            String fileUrl = (String) uploadResult.get("secure_url");

            String fileType = "other";
            if (originalFileName.contains(".")) {
                fileType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
            }

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

            ps.close();
            con.close();

            response.sendRedirect("mainpage.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("UPLOAD ERROR: " + e.getMessage());
        }
    }
}
