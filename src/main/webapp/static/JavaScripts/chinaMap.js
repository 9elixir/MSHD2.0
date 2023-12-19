// chinaMap.js
document.addEventListener('DOMContentLoaded', function () {
    console.log('DOMContentLoaded event fired');
    var chinaMap = document.getElementById('china-map');
    var myChart = echarts.init(chinaMap);

    // 发送请求获取动态数据
    fetch('/api/chinaMapData')
        .then(response => response.json())
        .then(data => {
            // 数据处理
            var option = {
                series: [
                    {
                        type: 'map',
                        map: 'china',
                        roam: false,
                        data: data.map(city => ({ name: city.name, value: city.value })), // 调整数据格式
                        label: {
                            show: true,
                            fontSize: 12,
                            fontWeight: 'bold'
                        },
                        itemStyle: {
                            emphasis: {
                                areaColor: '#a6c84c'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
        })
        .catch(error => {
            console.error('Error fetching China map data:', error);
        });
});
