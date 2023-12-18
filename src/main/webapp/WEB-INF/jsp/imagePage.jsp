<%@ page import="java.util.List" %>
<%@ page import="com.qiu.pojo.unified_code_Image_Relation" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Display Image</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
</head>
<body>
<h1>Image Display Page</h1>
<div class="horizontal-container">
    <% List<String> base64ImageList = (List<String>) request.getAttribute("base64ImageList");
        List<unified_code_Image_Relation> Relations = (List<unified_code_Image_Relation>)request.getAttribute("ImageRelation");
    %>
    <% for (int i = 0; i < base64ImageList.size(); i++) { %>
    <% String s = base64ImageList.get(i); %>
    <div class="image-container">
        <img class="image" src="data:image/jpg;base64,<%= s %>" alt="Image" />
        <a href="<%= request.getContextPath() %>/image?ImageId=<%= Relations.get(i).getImageId() %>&CodingId=<%= Relations.get(i).getCodingId() %>" download="image.jpg" class="download-link">
            Download Image
        </a>
    </div>
    <% } %>
</div>
</body>
</html>
