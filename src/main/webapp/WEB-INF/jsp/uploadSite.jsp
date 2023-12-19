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
    <style>
        ul {
            list-style: none;
            display: flex;
            padding: 0;
        }

        form {
            margin-right: 20px; /* 调整按钮之间的间距 */
        }
    </style>
</head>
<body>

<div>
    <ul>
        <form action="${pageContext.request.contextPath}/showCodes" method="get">
            <input class="export-button" type="submit" value="灾情显示" />
        </form>
        <form action="${pageContext.request.contextPath}/testMap" method="get">
            <input class="export-button" type="submit" value="灾情可视化" />
        </form>
        <form action="${pageContext.request.contextPath}/upload" method="get">
            <input class="export-button" type="submit" value="灾情码上传" />
        </form>
        <form action="${pageContext.request.contextPath}/code_edit" method="get">
            <input class="export-button" type="submit" value="代码编辑上传" />
        </form>
    </ul>

    <form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
        <p>编码</p>
        <input type="text" id="Code" name="Code" />
        <p>文字描述</p>
        <input type="text" id="Describe" name="Describe" />
        <input type="file" name="imageFiles" multiple>

        <!-- JSON输入/显示框 -->
        <textarea id="jsonTextarea" name="jsonTextarea" rows="10" cols="50" placeholder="JSON输入/显示框"></textarea>

        <!-- 转换按钮 -->
        <button type="button" class="uploadbutton" onclick="fillJsonTextArea()">转换为JSON</button>
        <button type="button" class="uploadbutton" onclick="fillCodeAndDescription()">转换为Code</button>

        <input class="uploadbutton" type="submit" value="插入" />
    </form>
</div>

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

</body>
<script src="${pageContext.request.contextPath}/static/JavaScripts/CodeJsonizer.js"></script>
</html>
