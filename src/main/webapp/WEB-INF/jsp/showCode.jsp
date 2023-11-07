<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.qiu.pojo.unified_code" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Unified Codes</title>
</head>
<body>
<h1>Show Unified Codes</h1>
<!-- Add your search form here -->
<form  method="post">
    <label for="Code">查询Code：</label>
    <input type="text" id="Code" name="Code" />
    <input type="submit" value="Code" />
</form>
<table border="1">
    <tr>
        <th>Coding ID</th>
        <th>Geo Code</th>
        <th>Time Code</th>
        <th>Source Code</th>
        <th>Carrier Code</th>
        <th>Disaster Code</th>
        <th>Description</th>
        <th>详情</th>
    </tr>

    <% List<unified_code> unifiedCodes = (List<unified_code>) request.getAttribute("unifiedCodes");
    for (unified_code code : unifiedCodes) { %>
    <tr>
        <td><%= code.getCodingId() %></td>
        <td><%= code.getGeoCode() %></td>
        <td><%= code.getTimeCode() %></td>
        <td><%= code.getSourceCode() %></td>
        <td><%= code.getCarrierCode() %></td>
        <td><%= code.getDisasterCode() %></td>
        <td><%= code.getDescription() %></td>
        <td><a href="${pageContext.request.contextPath}/code/<%=code.getCodingId()%>">查看详情</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>
