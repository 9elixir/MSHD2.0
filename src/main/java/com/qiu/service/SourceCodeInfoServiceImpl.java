package com.qiu.service;

import com.qiu.Mapper.source_code_infoMapper;
import com.qiu.pojo.source_code_info;
import com.qiu.pojo.source_code_infoExample;
import com.qiu.service.SourceCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceCodeInfoServiceImpl implements SourceCodeInfoService {

    @Autowired
    private source_code_infoMapper mapper;

    @Override
    public int countByExample(source_code_infoExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(source_code_infoExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String sourceCode) {
        return mapper.deleteByPrimaryKey(sourceCode);
    }

    @Override
    public int insert(source_code_info record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(source_code_info record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<source_code_info> selectByExample(source_code_infoExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public source_code_info selectByPrimaryKey(String sourceCode) {
        return mapper.selectByPrimaryKey(sourceCode);
    }

    @Override
    public int updateByExampleSelective(source_code_info record, source_code_infoExample example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(source_code_info record, source_code_infoExample example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(source_code_info record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(source_code_info record) {
        return mapper.updateByPrimaryKey(record);
    }

    public void setMapper(source_code_infoMapper mapper) {
        this.mapper = mapper;
    }
}
