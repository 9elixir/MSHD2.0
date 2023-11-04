package com.qiu.service;

import com.qiu.pojo.geo_code_info;
import com.qiu.pojo.geo_code_infoExample;

import java.util.List;

public interface GeoCodeInfoService {
    int countByExample(geo_code_infoExample example);

    int deleteByExample(geo_code_infoExample example);

    int deleteByPrimaryKey(String geoCode);

    int insert(geo_code_info record);

    int insertSelective(geo_code_info record);

    List<geo_code_info> selectByExample(geo_code_infoExample example);

    geo_code_info selectByPrimaryKey(String geoCode);

    int updateByExampleSelective(geo_code_info record, geo_code_infoExample example);

    int updateByExample(geo_code_info record, geo_code_infoExample example);

    int updateByPrimaryKeySelective(geo_code_info record);

    int updateByPrimaryKey(geo_code_info record);
}
