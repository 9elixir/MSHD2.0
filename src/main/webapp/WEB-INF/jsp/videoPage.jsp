<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>视频展示页面</title>
</head>
<body>
<h1>Video Display Page</h1>
<% List<String> base64List = (List<String>) request.getAttribute("videoData");%>
<%    for (int i=0;i<base64List.size();i++) { %>
<%String s = base64List.get(i);%>
<video width="520" height="360" controls>
    <source src="data:video/mp4;base64,<%=s%>" type="video/mp4">
    Your browser does not support the video tag.
</video>
<% } %>
</body>
</html>
