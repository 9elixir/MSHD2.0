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
        cityTable = new ArrayList<>();
        firstGeoCode = new ArrayList<>();
        lastGeoCode = new ArrayList<>();
        cityTable.add("北京市");
        firstGeoCode.add(selectByPrimaryKey("110101001001"));
        lastGeoCode.add(selectByPrimaryKey("110119203214"));
        cityTable.add("天津市");
        firstGeoCode.add(selectByPrimaryKey("120101001001"));
        lastGeoCode.add(selectByPrimaryKey("120119401498"));
        cityTable.add("河北省");
        firstGeoCode.add(selectByPrimaryKey("130102001001"));
        lastGeoCode.add(selectByPrimaryKey("131182403498"));
        cityTable.add("山西省");
        firstGeoCode.add(selectByPrimaryKey("140105001001"));
        lastGeoCode.add(selectByPrimaryKey("141182110215"));
        cityTable.add("内蒙古自治区");
        firstGeoCode.add(selectByPrimaryKey("150102001001"));
        lastGeoCode.add(selectByPrimaryKey("152971100201"));
        cityTable.add("辽宁省");
        firstGeoCode.add(selectByPrimaryKey("210102001002"));
        lastGeoCode.add(selectByPrimaryKey("211481400498"));
        cityTable.add("吉林省");
        firstGeoCode.add(selectByPrimaryKey("220102001001"));
        lastGeoCode.add(selectByPrimaryKey("222426402507"));
        cityTable.add("黑龙江省");
        firstGeoCode.add(selectByPrimaryKey("230102001001"));
        lastGeoCode.add(selectByPrimaryKey("232764103001"));
        cityTable.add("上海市");
        firstGeoCode.add(selectByPrimaryKey("310101002001"));
        lastGeoCode.add(selectByPrimaryKey("310151501498"));
        cityTable.add("江苏省");
        firstGeoCode.add(selectByPrimaryKey("320102002001"));
        lastGeoCode.add(selectByPrimaryKey("321371400007"));
        cityTable.add("浙江省");
        firstGeoCode.add(selectByPrimaryKey("330102001051"));
        lastGeoCode.add(selectByPrimaryKey("331181207241"));
        cityTable.add("安徽省");
        firstGeoCode.add(selectByPrimaryKey("340102001001"));
        lastGeoCode.add(selectByPrimaryKey("341882400498"));
        cityTable.add("福建省");
        firstGeoCode.add(selectByPrimaryKey("350102001001"));
        lastGeoCode.add(selectByPrimaryKey("350982500206"));
        cityTable.add("江西省");
        firstGeoCode.add(selectByPrimaryKey("360102002001"));
        lastGeoCode.add(selectByPrimaryKey("361181205205"));
        cityTable.add("山东省");
        firstGeoCode.add(selectByPrimaryKey("370102001002"));
        lastGeoCode.add(selectByPrimaryKey("371772108276"));
        cityTable.add("河南省");
        firstGeoCode.add(selectByPrimaryKey("410102001002"));
        lastGeoCode.add(selectByPrimaryKey("419001110238"));
        cityTable.add("湖北省");
        firstGeoCode.add(selectByPrimaryKey("420102002003"));
        lastGeoCode.add(selectByPrimaryKey("429021203205"));
        cityTable.add("湖南省");
        firstGeoCode.add(selectByPrimaryKey("430102001001"));
        lastGeoCode.add(selectByPrimaryKey("433130237215"));
        cityTable.add("广西壮族自治区");
        firstGeoCode.add(selectByPrimaryKey("450102001003"));
        lastGeoCode.add(selectByPrimaryKey("451481400498"));
        cityTable.add("重庆市");
        firstGeoCode.add(selectByPrimaryKey("500101001001"));
        lastGeoCode.add(selectByPrimaryKey("500243228207"));
        cityTable.add("四川省");
        firstGeoCode.add(selectByPrimaryKey("510104017001"));
        lastGeoCode.add(selectByPrimaryKey("513437241205"));
        cityTable.add("贵州省");
        firstGeoCode.add(selectByPrimaryKey("520102016001"));
        lastGeoCode.add(selectByPrimaryKey("522732116285"));
        cityTable.add("云南省");
        firstGeoCode.add(selectByPrimaryKey("530102001001"));
        lastGeoCode.add(selectByPrimaryKey("533423207209"));
        cityTable.add("西藏自治区");
        firstGeoCode.add(selectByPrimaryKey("540102002001"));
        lastGeoCode.add(selectByPrimaryKey("542527203204"));
        cityTable.add("陕西省");
        firstGeoCode.add(selectByPrimaryKey("610102001001"));
        lastGeoCode.add(selectByPrimaryKey("611026110221"));
        cityTable.add("甘肃省");
        firstGeoCode.add(selectByPrimaryKey("620102001001"));
        lastGeoCode.add(selectByPrimaryKey("623027208202"));
        cityTable.add("青海省");
        firstGeoCode.add(selectByPrimaryKey("630102001001"));
        lastGeoCode.add(selectByPrimaryKey("632857101001"));
        cityTable.add("宁夏回族自治区");
        firstGeoCode.add(selectByPrimaryKey("640104001002"));
        lastGeoCode.add(selectByPrimaryKey("640522406498"));
        cityTable.add("新疆维吾尔自治区");
        firstGeoCode.add(selectByPrimaryKey("650102002002"));
        lastGeoCode.add(selectByPrimaryKey("659011502512"));

//         cityTable = getAllCities();
//         for (String city : cityTable) {
//            List<geo_code_info> list = selectByCity(city);
//            firstGeoCode.add(minGeo(list));
//            lastGeoCode.add(maxGeo(list));
//            System.out.println("cityTable.add(" +"\""+city+"\""+");");
//            System.out.println("firstGeoCode.add(selectByPrimaryKey(" +"\""+minGeo(list).getGeoCode()+"\""+"));");
//            System.out.println("lastGeoCode.add(selectByPrimaryKey(" +"\""+maxGeo(list).getGeoCode()+"\""+"));");
//        }

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
    public int getCities(){
        return cityTable.size();
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
