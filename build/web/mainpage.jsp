<%-- 
    Document   : mainpage
    Created on : Apr 14, 2026, 12:24:59 AM
    Author     : herme
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, data.dbconnection" %>
<%
    
    Integer userIdObj = (Integer) session.getAttribute("userid");

    if (userIdObj == null) {
        response.sendRedirect("index.html");
        return;
    }

    int userId = userIdObj;
    String filterType = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="mainpage.css">
    <title>My Drive</title>
</head>

<body>

<div class="topbar">
    <div class="logo">MyDrive</div>

    <a href="LogoutServlet" class="logout">
        Logout <i class="fa-solid fa-arrow-right-from-bracket"></i>
    </a>
</div>

<div class="container">

    <div class="sidebar">
        <ul>
            <li><a href="mainpage.jsp">My Files</a></li>
            <li><a href="mainpage.jsp?type=photo">Photos</a></li>
            <li><a href="mainpage.jsp?type=video">Videos</a></li>
            <li><a href="mainpage.jsp?type=document">Documents</a></li>
        </ul>
    </div>

    <div class="main">

        <div class="header">
            <h2>My Files</h2>

            <form action="UploadServlet" method="post" enctype="multipart/form-data">
                <input type="file" name="file" required>
                <button type="submit" class="upload">+ Upload File</button>
            </form>
        </div>

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
                try {
                    Connection con = dbconnection.getcon();

                    PreparedStatement ps;

                    
                    String sql = "SELECT * FROM files WHERE userid=?";

                    if (filterType != null) {

                        if (filterType.equals("photo")) {
                            sql += " AND (file_type='jpg' OR file_type='png')";
                        } 
                        else if (filterType.equals("video")) {
                            sql += " AND (file_type='mp4' OR file_type='avi')";
                        } 
                        else if (filterType.equals("document")) {
                            sql += " AND (file_type='pdf' OR file_type='doc' OR file_type='docx' OR file_type='ppt' OR file_type='pptx')";
                        }
                    }

                    ps = con.prepareStatement(sql);
                    ps.setInt(1, userId);

                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
            %>

            <tr>

                <td>
                    <%
                        String type = rs.getString("file_type");

                        if (type != null && (type.equals("jpg") || type.equals("png"))) {
                    %>
                        <i class="fa-solid fa-file-image"></i>
                    <%
                        } else if ("pdf".equals(type)) {
                    %>
                        <i class="fa-solid fa-file-pdf"></i>
                    <%
                        } else {
                    %>
                        <i class="fa-solid fa-file"></i>
                    <%
                        }
                    %>
                </td>

                <td><%= rs.getString("file_name") %></td>
                <td><%= rs.getString("file_size") %></td>

                <td>
                    <a href="DownloadServlet?id=<%= rs.getInt("file_id") %>">
                        <i class="fa-solid fa-download"></i>
                    </a>

                    <a href="DeleteServlet?id=<%= rs.getInt("file_id") %>">
                        <i class="fa-solid fa-trash"></i>
                    </a>
                </td>

            </tr>

            <%
                    }

                    con.close();

                } catch (Exception e) {
                    out.println("<p style='color:red;'>ERROR: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            %>

            </tbody>
        </table>

    </div>
</div>

</body>
</html>
