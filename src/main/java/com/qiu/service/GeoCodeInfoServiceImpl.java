package com.qiu.service;

import com.qiu.Mapper.geo_code_infoMapper;
import com.qiu.pojo.geo_code_info;
import com.qiu.pojo.geo_code_infoExample;
import com.qiu.service.GeoCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeoCodeInfoServiceImpl implements GeoCodeInfoService {

    @Autowired
    private geo_code_infoMapper mapper;

    @Override
    public int countByExample(geo_code_infoExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(geo_code_infoExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String geoCode) {
        return mapper.deleteByPrimaryKey(geoCode);
    }

    @Override
    public int insert(geo_code_info record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(geo_code_info record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<geo_code_info> selectByExample(geo_code_infoExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public geo_code_info selectByPrimaryKey(String geoCode) {
        return mapper.selectByPrimaryKey(geoCode);
    }

    @Override
    public int updateByExampleSelective(geo_code_info record, geo_code_infoExample example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(geo_code_info record, geo_code_infoExample example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(geo_code_info record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(geo_code_info record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public List<geo_code_info> getAllGeoCodeInfo() {
        return selectByExample(new geo_code_infoExample());
    }

    @Override
    // 获取所有不重复的城市列表
    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        // 获取所有geo_code_info记录
        List<geo_code_info> geoCodeInfoList = getAllGeoCodeInfo();
        // 遍历记录，将城市添加到List中
        for (geo_code_info geoCodeInfo : geoCodeInfoList) {
            String city = geoCodeInfo.getProvince();
            if (city != null && !city.isEmpty() && !cities.contains(city)) {
                cities.add(city);
            }
        }
        return cities;
    }

    @Override
    public List<geo_code_info> selectByCity(String city){
        geo_code_infoExample example = new geo_code_infoExample();
        geo_code_infoExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceEqualTo(city);
        return selectByExample(example);
    }

    @Override
    public geo_code_info minGeo(List<geo_code_info> list){
        geo_code_info code = list.get(0);
        for (geo_code_info B : list) {
            if (B.getGeoCode().compareTo(code.getGeoCode()) <  0)
                code = B;
        }
        return code;
    }

    @Override
    public geo_code_info maxGeo(List<geo_code_info> list){
        geo_code_info code = list.get(0);
        for (geo_code_info B : list) {
            if (B.getGeoCode().compareTo(code.getGeoCode()) >  0)
                code = B;
        }
        return code;
    }

    private static List<String> cityTable = null;//映射编号和地区
    private static List<geo_code_info> firstGeoCode = null;//映射第一个编号
    private static List<geo_code_info> lastGeoCode = null;//映射最后一个编号
    private static Boolean flag=false;

    @Override
    public void init() {
        if (flag) return ;
        flag=true;
        firstGeoCode = new ArrayList<>();
        lastGeoCode = new ArrayList<>();

        cityTable = getAllCities();
        for (String city : cityTable) {
            List<geo_code_info> list = selectByCity(city);
            firstGeoCode.add(minGeo(list));
            lastGeoCode.add(maxGeo(list));
        }
        // 可以继续添加其他映射关系
    }

    @Override
    public void printOut(){
        for (int i=0;i<cityTable.size();i++){
            System.out.println(cityTable.get(i));
            System.out.println(firstGeoCode.get(i));
            System.out.println(lastGeoCode.get(i));
        }
    }

    @Override
    public String getCity(int i){
        return cityTable.get(i);
    }
    @Override
    public geo_code_info getFirstGeoCode(int i){
        return firstGeoCode.get(i);
    }
    @Override
    public geo_code_info getLastGeoCode(int i){
        return lastGeoCode.get(i);
    }

    public void setMapper(geo_code_infoMapper mapper) {
        this.mapper = mapper;
    }


}
