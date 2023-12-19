<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <title>Line Chart</title>
</head>
<body>
<div id="app" data-v-app>
    <div class="header" data-v-f7e66bd1>
        <div class="logo" data-v-f7e66bd1>多源异构灾情管理系统</div>
    </div>
    <div class="subject">
        <div class="sider" data-v-5bdb2cb5 >
            <ul class="lift_border">
                <a href="${pageContext.request.contextPath}/showCodes" class="a1">灾情显示</a>
                <a href=" " class="a1">灾情可视化</a>
                <a href="${pageContext.request.contextPath}/upload" class="a1">灾情码上传</a>
                <a href="${pageContext.request.contextPath}/code_edit" class="a1" >代码编辑上传</a>
            </ul>
        </div>
        <div class="container">
            <div class="headline">
                时间分析表格
            </div>
            <p><%=request.getAttribute("city")%>在<%=request.getAttribute("year")%>年的灾情情况,总计是<%=request.getAttribute("total")%></p>
            <form action="<%=request.getContextPath()%>/lineChart/<%=request.getAttribute("i")%>" method="get">
                <input type="hidden" name="year" value="<%= (int)request.getAttribute("year")-1 %>" />
                <input class="export-button" type="submit" value="看看去年" />
            </form>
            <p><%=request.getAttribute("year")%>年</p>
            <form action="<%=request.getContextPath()%>/lineChart/<%=request.getAttribute("i")%>" method="get">
                <input type="hidden" name="year" value="<%= (int)request.getAttribute("year")+1 %>" />
                <input class="export-button" type="submit" value="看看下一年" />
            </form>
            <%-- Display the Line Chart using Base64 image --%>
            <img src="data:image/png;base64,${lineChartBase64}" alt="Line Chart">
        </div>
    </div>
</div>
</body>
</html>

