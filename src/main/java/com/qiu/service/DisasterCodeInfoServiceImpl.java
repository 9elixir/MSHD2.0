package com.qiu.service;

import com.qiu.Mapper.disaster_code_infoMapper;
import com.qiu.pojo.disaster_code_info;
import com.qiu.pojo.disaster_code_infoExample;
import com.qiu.service.DisasterCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisasterCodeInfoServiceImpl implements DisasterCodeInfoService {

    @Autowired
    private disaster_code_infoMapper mapper;

    @Override
    public int countByExample(disaster_code_infoExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(disaster_code_infoExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String disasterCode) {
        return mapper.deleteByPrimaryKey(disasterCode);
    }

    @Override
    public int insert(disaster_code_info record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(disaster_code_info record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<disaster_code_info> selectByExample(disaster_code_infoExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public disaster_code_info selectByPrimaryKey(String disasterCode) {
        return mapper.selectByPrimaryKey(disasterCode);
    }

    @Override
    public int updateByExampleSelective(disaster_code_info record, disaster_code_infoExample example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(disaster_code_info record, disaster_code_infoExample example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(disaster_code_info record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(disaster_code_info record) {
        return mapper.updateByPrimaryKey(record);
    }

    public void setMapper(disaster_code_infoMapper mapper) {
        this.mapper = mapper;
    }
}
