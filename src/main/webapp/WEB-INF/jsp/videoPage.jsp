<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.qiu.pojo.unified_code_Video_Relation" %>
<!DOCTYPE html>
<html>
<head>
    <title>视频展示页面</title>
</head>
<body>
<h1>Video Display Page</h1>
<% List<String> base64List = (List<String>) request.getAttribute("videoData");
    List<unified_code_Video_Relation> Relations=(List<unified_code_Video_Relation>) request.getAttribute("VideoRelations");
%>
<div class="horizontal-container">
    <%    for (int i=0;i<base64List.size();i++) { %>
        <%String s = base64List.get(i);%>
        <div class="video-container">
            <video width="520" height="360" controls>
                <source src="data:video/mp4;base64,<%=s%>" type="video/mp4">
                Your browser does not support the video tag.
            </video>
            <a href="<%= request.getContextPath() %>/video?VideoId=<%= Relations.get(i).getVideoId() %>&CodingId=<%=Relations.get(i).getCodingId()%>" class="download-link" download="video.mp4">
                Download Video
            </a>
        </div>
    <% } %>
</div>
</body>
</html>
