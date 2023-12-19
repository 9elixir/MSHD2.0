<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>XML Code Editor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <style>
        /* 设置代码编辑器的样式 */
        #code-editor {
            width: 100%;
            height:360px;
            min-height: 100px;
            font-family: monospace;
        }
    </style>
</head>
<body>

   <h1>XML Code Editor</h1>

   <form id="xml-form" method="post" action="${pageContext.request.contextPath}/code_edit">
    <textarea id="code-editor" name="xmlData"></textarea>
    <button type="submit">解析上传</button>
   </form>
   <div id="messageDiv" th:text="${message}"></div>
   <input type="file" id="xml-file" accept=".xml">
   <a href="${pageContext.request.contextPath}/showCodes" class="a2">返回</a>


</body>
<script>
    window.addEventListener('DOMContentLoaded', function () {
        var fileInput = document.getElementById('xml-file');
        fileInput.click();
    });

    document.getElementById('xml-file').addEventListener('change', function (e) {
        var file = e.target.files[0];
        var reader = new FileReader();
        reader.onload = function (event) {
            var xmlData = event.target.result;
            document.getElementById('code-editor').value = xmlData;
        };
        reader.readAsText(file);
    });
</script>
</html>