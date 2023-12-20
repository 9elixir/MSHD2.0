package com.qiu.controller.lyq;

import com.google.gson.Gson;
import com.qiu.Rook.CityMap;
import com.qiu.pojo.geo_code_info;
import com.qiu.pojo.unified_code;
import com.qiu.service.CodeInfoService;
import com.qiu.service.GeoCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MapDataController {
    @Autowired
    @Qualifier("CodeInfoServiceImpl")
    private CodeInfoService codeInfoService;

    @Autowired
    @Qualifier("GeoCodeInfoServiceImpl")
    private GeoCodeInfoService geoCodeInfoService;

    @GetMapping("/chinaMapData")
    public List<CityMap> getChinaMapData() {
        geoCodeInfoService.init();
        List<CityMap> cityMap = new ArrayList<>();
        for (int i = 0; i < geoCodeInfoService.getCities(); i++) {
            String city = geoCodeInfoService.getCity(i).substring(0,3);
            if (i != 4) city = city.substring(0,2);
            geo_code_info firstCode = geoCodeInfoService.getFirstGeoCode(i);
            geo_code_info lastCode = geoCodeInfoService.getLastGeoCode(i);
            List<unified_code> list = codeInfoService.getCodeListByCity(firstCode.getGeoCode(), lastCode.getGeoCode());
            cityMap.add(new CityMap(city, list.size(),i));
            System.out.println(city+" "+list.size());
        }
        return cityMap;
    }

    // 另外添加一个方法用于获取地图数据
    @GetMapping("/mapData")
    public String getMapData() {
        List<CityMap> cityMap = getChinaMapData();
        Gson gson = new Gson();
        return gson.toJson(cityMap);
    }
}

