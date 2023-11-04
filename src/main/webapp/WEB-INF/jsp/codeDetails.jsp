<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.qiu.pojo.unified_code" %>
<%@ page import="com.qiu.pojo.carrier_code_info" %>
<%@ page import="com.qiu.pojo.disaster_code_info" %>
<%@ page import="com.qiu.pojo.geo_code_info" %>
<%@ page import="com.qiu.pojo.source_code_info" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Code Details</title>
</head>
<body>
<h1>Code Details</h1>

<%-- Display Code details --%>
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
</body>
</html>
