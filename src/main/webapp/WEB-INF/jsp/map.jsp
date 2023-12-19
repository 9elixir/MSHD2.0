<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>China Map</title>
    <!-- 引入 ECharts 库 -->
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.2.2/dist/echarts.min.js"></script>
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
            </ul>
        </div>
        <div class="container">
            <div class="headline">
                中国地图
            </div>
            <div id="city-display"></div>
            <div id="china-map" style="width: 800px; height: 600px;"></div>
        </div>
    </div>
</div>
</body>
<!-- 引入 JavaScript 文件 -->
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        var chinaMap = document.getElementById('china-map');
        var myChart = echarts.init(chinaMap);
        // 添加用于显示城市名的元素
        var cityDisplay = document.getElementById('city-display');

        // 使用你的 china.json
        fetch('${pageContext.request.contextPath}/static/json/china.json')
            .then(response => response.json())
            .then(geoJson => {
                echarts.registerMap('china', geoJson);

                // 获取动态数据
                fetch('${pageContext.request.contextPath}/api/chinaMapData')
                    .then(response => response.json())
                    .then(data => {
                        console.log('Fetched data:', data); // 添加这行
                        var option = {
                            series: [
                                {
                                    type: 'map',
                                    map: 'china',
                                    roam: true,
                                    label: {
                                        show: true,
                                        fontSize: 12,
                                        fontWeight: 'bold'
                                    },
                                    itemStyle: {
                                        emphasis: {
                                            areaColor: '#a6c84c'
                                        }
                                    },
                                    data: data.map(city => ({
                                        name: city.name,
                                        value: city.value,
                                        i:city.i
                                    }))
                                }
                            ]
                        };
                        // 注册点击事件
                        myChart.on('click', function (params) {
                            // params 中包含了点击的信息，这里你可以根据点击的城市名称获取对应的数据
                            var clickedCity = params.name;
                            var cityData = data.find(Object => (Object.name == clickedCity));
                            // 修改显示城市信息的元素内容
                            if (cityData) {
                                // 修改显示城市名的元素内容
                                cityDisplay.innerHTML = '<a href="${pageContext.request.contextPath}/lineChart/'+cityData.i+'">' + cityData.name + ': ' + cityData.value + '</a>';
                            } else {
                                cityDisplay.innerText = clickedCity + ': N/A';
                            }
                        });

                        myChart.setOption(option);
                    })
                    .catch(error => {
                        console.error('Error fetching map data:', error);
                    });
            })
            .catch(error => {
                console.error('Error fetching China map GeoJSON data:', error);
            });
    });
</script>
</html>
