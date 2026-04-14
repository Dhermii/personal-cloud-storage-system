<%-- 
    Document   : mainpage
    Created on : Apr 14, 2026, 12:24:59 AM
    Author     : herme
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, data.dbconnection" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="mainpage.css">
        <title>My Drive</title>
    </head>
    <body>
        <!-- TOP BAR -->
        <div class="topbar">
            <div class="logo">MyDrive</div>
            <a href="LogoutServlet" class="logout">
    Logout <i class="fa-solid fa-arrow-right-from-bracket"></i>
</a>
        </div>

        <!-- CONTAINER -->
        <div class="container">

            <!-- SIDEBAR -->
            <div class="sidebar">
                <ul>
                    <li><a href="mainpage.jsp"><i class="fa-solid fa-folder"></i> My Files</a></li>
                    <li><a href="mainpage.jsp?type=photo">Photos</a></li>
                    <li><a href="mainpage.jsp?type=video">Videos</a></li>
                    <li><a href="mainpage.jsp?type=document">Documents</a></li>
                </ul>
            </div>

            <!-- MAIN -->
            <div class="main">

                <div class="header">
                    <h2>My Files</h2>
                    <form action="UploadServlet" method="post" enctype="multipart/form-data">
                        <input type="file" name="file" required>
                        <button type="submit" class="upload">+ Upload File</button>
                    </form>
                </div>

                <%
                    String filterType = request.getParameter("type");
                %>

                <!-- TABLE -->
                <table>
                    <thead>
                        <tr>
                            <th>Type</th>
                            <th>Name</th>
                            <th>Size</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%
                            int userId = (int) session.getAttribute("userid");

                            try {
                                Connection con = dbconnection.getcon();
                                PreparedStatement ps;

                                if (filterType == null) {
                                    // 🔹 My Files → show all
                                    ps = con.prepareStatement("SELECT * FROM files WHERE userid=?");
                                    ps.setInt(1, userId);

                                } else if (filterType.equals("photo")) {
                                    ps = con.prepareStatement(
                                            "SELECT * FROM files WHERE userid=? AND (file_type='jpg' OR file_type='png')"
                                    );
                                    ps.setInt(1, userId);

                                } else if (filterType.equals("video")) {
                                    ps = con.prepareStatement(
                                            "SELECT * FROM files WHERE userid=? AND (file_type='mp4' OR file_type='avi')"
                                    );
                                    ps.setInt(1, userId);

                                } else if (filterType.equals("document")) {
                                    ps = con.prepareStatement(
                                            "SELECT * FROM files WHERE userid=? AND (file_type='pdf' OR file_type='doc' OR file_type='docx' OR file_type='ppt' OR file_type='pptx')"
                                    );
                                    ps.setInt(1, userId);

                                } else {
                                    ps = con.prepareStatement("SELECT * FROM files WHERE userid=?");
                                    ps.setInt(1, userId);
                                }

                                ResultSet rs = ps.executeQuery();
                                while (rs.next()) {
                        %>
                        <tr>
                            <td>
                                <%
                                    String type = rs.getString("file_type");

                                    if (type != null && (type.equals("jpg") || type.equals("png"))) {
                                %>
                                <i class="fa-solid fa-file-image file-icon image"></i>
                                <%
                                } else if (type.equals("pdf")) {
                                %>
                                <i class="fa-solid fa-file-pdf file-icon pdf"></i>
                                <%
                                } else {
                                %>
                                <i class="fa-solid fa-file file-icon"></i>
                                <%
                                    }
                                %>
                            </td>
                            <td><%= rs.getString("file_name")%></td>
                            <td><%= rs.getString("file_size")%></td>
                            <td>
                                <a href="DownloadServlet?id=<%= rs.getInt("file_id")%>">
                                    <i class="fa-solid fa-download download"></i>
                                </a>

                                <a href="DeleteServlet?id=<%= rs.getInt("file_id")%>">
                                    <i class="fa-solid fa-trash delete"></i>
                                </a>
                            </td>
                        </tr>
                        <%
                            }  
                            }
                            catch (Exception e

                            
                                ) {
                                e.printStackTrace();
                            }
                        %>
                    </tbody>
                </table>

            </div>
        </div>

    </body>
</html>
