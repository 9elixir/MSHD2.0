<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.qiu.pojo.unified_code" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Unified Codes</title>
    <style>
        .header{
            background-color:#242f42 ;
            font-size: 18px;
            font-family: "微软雅黑";
            font-weight: bold;
            /*字体缩写：粗细 大小 样式*/
            font: bold 30px "微软雅黑";
            color:#eeeeee;
            height:50px;
        }

        .sider{
            background-color:#242f33 ;
            border: 1px solid black;
            display: block;
            float: left;
            padding-top:30px;

            line-height: 50px;
            height:700px;
            color: #ccccc1;
        }
        .container{
            display:flex;
            flex-direction: column;
            align-items:center;
            padding:0 0;
            height:732px;
            background-color: #eeecec;
        }
        .headline{
            margin-top:10px;
            margin-left: 0px;
            width:99%;
            font-size:20px;
            border: 1px solid #d3d3d3;

        }

        .search-container{
            width:500px;
            height:100px;
            line-height:90px;
        }

        #Code {
            flex: 1;
            height: 30px;
            box-sizing: border-box;
            outline: none;
            border: 1px solid #eadddd;
            padding: 0 20px;
            transition: all .3s;
            float:left;
            margin-top:26px ;
        }
        #button{
            height:30px;
            padding:0 30px;
            border:none;
            box-sizing:border-box;
            background-color:green;
            font-size:24px;
            color:#fff;
            cursor:pointer;
            border-top-right-radius: 18px;
            border-bottom-right-radius: 18px;


        }
        .search-container input:focus{
            box-shadow:0 0 5px #eee;
        }
        .table{
            border-collapse:collapse;
            width :100%;

            /*border: 1px solid #000; /* 设置表格边框样式和颜色 */
            @media (max-width: 600px) {
                table, thead, tbody, th, td, tr {
                    display: block;
                }
            }

            margin-bottom: 10px; /* 设置下边距 */
            padding-left: 10px;
            padding-right:10px;
        }
        .table td, th {
            text-align: center; /* 将单元格内容水平居中 */
            vertical-align: middle; /* 将单元格内容垂直居中 */
            border: 1px solid #000; /* 设置单元格边框样式和颜色*/
            height:40px;
        }

    </style>
</head>
<body>
<div id="app" data-v-app>
    <div class="header" data-v-f7e66bd1>
        <div class="logo" data-v-f7e66bd1>多源异构灾情管理系统</div>
    </div>
    <div class="sider" data-v-5bdb2cb5 >
        <ul>
            <li>灾情显示</li>
            <li>灾情可视化</li>
            <li>设置管理</li>
            <li>帮助</li>
        </ul>
    </div>
    <div class="container">
        <div class="headline">
            灾情显示
        </div>
        <div class="search-container">
             <form  method="post">

            <input type="text" id="Code" name="Code" />
            <input id="button" type="submit" value="搜索" />
        </form>
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
    </div>
</div>
</body>
</html>
