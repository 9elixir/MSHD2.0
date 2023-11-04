package com.qiu.service;

import com.qiu.Mapper.geo_code_infoMapper;
import com.qiu.pojo.geo_code_info;
import com.qiu.pojo.geo_code_infoExample;
import com.qiu.service.GeoCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void setMapper(geo_code_infoMapper mapper) {
        this.mapper = mapper;
    }
}
