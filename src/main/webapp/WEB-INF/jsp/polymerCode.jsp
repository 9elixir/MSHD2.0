<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.qiu.pojo.unified_code" %>
<%@ page import="com.qiu.pojo.carrier_code_info" %>
<%@ page import="com.qiu.pojo.disaster_code_info" %>
<%@ page import="com.qiu.pojo.geo_code_info" %>
<%@ page import="com.qiu.pojo.source_code_info" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Unified Codes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/css.css">
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&type=webgl&ak=Ef5a4qjXI9uzk9bfMmG0ZfGmrsWsnxgh">
    </script>

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
                <a href="${pageContext.request.contextPath}/testMap" class="a1">灾情可视化</a>
                <a href="${pageContext.request.contextPath}/upload" class="a1">灾情码上传</a>
                <a href="${pageContext.request.contextPath}/code_edit" class="a1" >代码编辑上传</a>
            </ul>
        </div>
    <div class="container">

        <h1>聚合码详情</h1>
        <h2>主聚合码为:${code}</h2>
        <table border="1" class="table">
            <tr>
                <th>全部灾情码</th>
                <th>地理码解析</th>
                <th>时间码解析</th>
            </tr>
            <tr>
                <th>${code}</th>
                <th>${geo}</th>
                <th>${Time}</th>
            </tr>
        </table>
        <table border="1" class="table">
            <tr>
                <th>辅助聚合码</th>
                <th>来源码解析</th>
                <th>载体码解析</th>
                <th>灾情情况</th>
            </tr>
            <!--循环展示-->
            <% List<unified_code> auxiliaryCodeList = (List<unified_code>) request.getAttribute("auxiliaryCodeList");%>
            <% List<String> SourceInfoList = (List<String>) request.getAttribute("SourceInfoList");%>
            <% List<String> CarrierInfoList = (List<String>) request.getAttribute("CarrierInfoList");%>
            <% List<String> DisasterInfoList = (List<String>) request.getAttribute("DisasterInfoList");%>
            <%    for (int i=0;i<auxiliaryCodeList.size();i++) { %>
            <tr>
                <%unified_code auxiliaryCode = auxiliaryCodeList.get(i);%>
                <%String SourceInfo = SourceInfoList.get(i);%>
                <%String CarrierInfo = CarrierInfoList.get(i);%>
                <%String DisasterInfo = DisasterInfoList.get(i);%>
                <td><%=auxiliaryCode.getGeoCode()+auxiliaryCode.getTimeCode()+auxiliaryCode.getSourceCode()+auxiliaryCode.getCarrierCode()+auxiliaryCode.getDisasterCode()%></td>
                <td><%=SourceInfo%></td>
                <td><%=CarrierInfo%></td>
                <td><%=DisasterInfo%></td>
            </tr>
            <%}%>
        </table>
        <div>
            <div><p style="font-weight: bold;">图片</p><a href="${pageContext.request.contextPath}/displayImage/${code_2.getCodingId()}" class="a2">显示code里面的图片</a></div>
            <div><p style="font-weight: bold;">视频</p><a href="${pageContext.request.contextPath}/displayVideo/${code_2.getCodingId()}" class="a2">显示code里面的视频</a></div>
            <div><p style="font-weight: bold;">地图</p><p>${code_2.getGeoCode().toString()}</p>
                <div id="Mapcontainer" style="margin-top:30px;width:400px;height:250px;overflow:hidden;"><div id="allmap"></div></div>
            </div>
            <div><p style="font-weight: bold;">所有文件</p></div>
        </div>
    </div>
    </div>
</div>

</body>
</html>

<script type="text/javascript">
    var map = new BMapGL.Map("allmap",{
            enableRotate: false,
            enableTilt: false
        }
    );
    var point = new BMapGL.Point(${Longtitude}, ${Latitude});
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);
    var marker = new BMapGL.Marker(point);
    map.addOverlay(marker);

</script>
