<%--
  Created by IntelliJ IDEA.
  User: 12224
  Date: 2023/11/25
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/StyleForuploadSite.css">
</head>
<body>
    <div>
       <form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
        <p>编码</p>
        <input type="text" id="Code" name="Code" />
        <p>文字描述</p>
        <input type="text" id="Describe" name="Describe"/>
        <input type="file" name="imageFiles" multiple>
        <!-- JSON输入/显示框 -->
        <textarea id="jsonTextarea" name="jsonTextarea" rows="10" cols="50" placeholder="JSON输入/显示框"></textarea>

        <!-- 转换按钮 -->
        <button onclick="convertToJson()">转换为JSON</button>
        <button onclick="convertToCode()">转换为Code</button>

        <input id="button" type="submit" value="插入" />

       </form>

       <ul class="bg-squares">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
</body>
</html>
