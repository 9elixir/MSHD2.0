<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.qiu.pojo.unified_code" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Unified Codes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
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
                <a href="${pageContext.request.contextPath}/code_edit" class="a1" target="_blank">代码编辑上传</a>
            </ul>
        </div>
        <div class="container">
            <div class="headline">
                灾情显示
            </div>
            <div class="search_refresh">
                <div class="search-container">
                    <form  method="post">
                        <input type="text" id="Code" name="Code" />
                        <input id="button" type="submit" value="搜索" />
                    </form>
                </div>
                <div class="export-button">
                    <form action="<%=request.getContextPath()%>/exportCSV" method="get">
                        <input class="export-button" type="submit" value="导出" />
                    </form>
                    <button id="mybutton">
                        <span>按时间排序</span>
                    </button>
                </div>
            </div>

            <table class="table" >
                <tr>
                    <th>Coding ID</th>
                    <th>Geo Code</th>
                    <th>Time Code</th>
                    <th>Source Code</th>
                    <th>Carrier Code</th>
                    <th>Disaster Code</th>
                    <th>Description</th>
                    <th>操作</th>
                    <th>详情</th>
                    <th>聚合详情</th>
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
                    <td><a href="${pageContext.request.contextPath}/DropCode/<%=code.getCodingId()%>" class="a2">删除</a></td>
                    <td><a href="${pageContext.request.contextPath}/code/<%=code.getCodingId()%>" class="a2">查看详情</a></td>
                    <td><a href="${pageContext.request.contextPath}/polymerCode/<%=code.getCodingId()%>" class="a2">聚合详情</a></td>
                </tr>
                <% } %>
            </table>
        </div>

    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/css/javascript.js"></script>
</html>