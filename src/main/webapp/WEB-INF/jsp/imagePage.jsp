<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Display Image</title>
</head>
<body>
<h1>Image Display Page</h1>
<% List<String> base64ImageList = (List<String>) request.getAttribute("base64ImageList");%>
<%    for (int i=0;i<base64ImageList.size();i++) { %>
<%String s = base64ImageList.get(i);%>
<img width="520" height="360" src="data:image/jpg;base64,<%=s%>" alt="Image" />
<% } %>
</body>
</html>
