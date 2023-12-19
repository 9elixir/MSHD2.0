<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // 构建重定向URL
    String redirectURL = request.getContextPath() + "/showCodes";
    response.sendRedirect(redirectURL);
%>
<html>
<body>
<h2>Hello World!</h2>

<a href="${pageContext.request.contextPath}/showCodes">查看Codes</a>
</body>
</html>
