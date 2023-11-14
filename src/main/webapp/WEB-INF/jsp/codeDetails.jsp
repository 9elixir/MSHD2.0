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
  <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&type=webgl&ak=Ef5a4qjXI9uzk9bfMmG0ZfGmrsWsnxgh">
  </script>
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
      justify-content:flex-start;
      align-items:flex-start;
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
      height:160px;
      line-height:200px;
    }
    .search-container input{
      flex:1;
      height:50px;
      line-height:50px;
      box-sizing:border-box;
      outline:none;
      border:#000000;
      border-top-lift-radius: 25px;
      border-botton-lift-radius: 25px;
      padding:0 20px;
      transition:all .3s;
    }
    .search-container button{
      height:50px;
      line-height: 50px;
      padding:0 30px;
      border:none;
      box-sizing:border-box;
      background-color:green;
      font-size:24px;
      color:#fff;
      cursor:pointer;
      border-top-right-radius: 25px;
      border-botton-right-radius: 25px;

    }
    .search-container input:focus{
      box-shadow:0 0 5px #eee;
    }
    .table{
      border-collapse:collapse;
      width :100%;
      height :200%;
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
    }
    body, html,#allmap {
      width: 100%;
      height: 100%;
      overflow: hidden;
      margin: 0;
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
    <form method="post">
      <label for="Code">查询Code：</label>
      <input type="text" id="Code" name="Code" />
      <input type="submit" value="Code" />
    </form>

    <h1>Code Details</h1>
    <table border="1">
      <tr>
        <th>全部灾情码</th>
        <th>地理码解析</th>
        <th>时间码解析</th>
        <th>来源码解析</th>
        <th>载体码解析</th>
        <th>灾情情况</th>
      </tr>
      <tr>
        <td>${code.getGeoCode()}${code.getTimeCode()}${code.getSourceCode()}${code.getCarrierCode()}${code.getDisasterCode()}</td>
        <td>${GeoCode.getProvince()}${GeoCode.getCity()}${GeoCode.getCounty()}${GeoCode.getTown()}${GeoCode.getVillage()}</td>
        <td>${Time}</td>
        <td>来自:${SourceCode.getSubCategory()}，属于${SourceCode.getMainCategory()}</td>
        <td>${CarrierCode.getCarrierForm()}</td>
        <td>${DisasterCode.getSubCategory()}:${DisasterCode.getDisasterIndicators()}的情况是${code.getDescription()}</td>
      </tr>
    </table>

    <div>
      <div><p style="font-weight: bold;">图片</p><a href="${pageContext.request.contextPath}/displayImage/${code.getCodingId()}">显示code里面的图片</a></div>
      <div><p style="font-weight: bold;">视频</p><a href="${pageContext.request.contextPath}/displayVideo/${code.getCodingId()}">显示code里面的视频</a></div>
      <div><p style="font-weight: bold;">地图</p><p>${code.getGeoCode().toString()}</p>
        <div id="Mapcontainer" style="margin-top:30px;width:400px;height:250px;overflow:hidden;"><div id="allmap"></div></div>
      </div>
      <div><p style="font-weight: bold;">所有文件</p></div>
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
