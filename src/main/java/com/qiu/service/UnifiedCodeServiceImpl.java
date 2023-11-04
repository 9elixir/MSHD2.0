package com.qiu.service;

import com.qiu.Mapper.unified_codeMapper;
import com.qiu.pojo.unified_code;
import com.qiu.pojo.unified_codeExample;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnifiedCodeServiceImpl implements UnifiedCodeService {

    @Autowired
    private unified_codeMapper mapper;

    @Override
    public int countByExample(unified_codeExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(unified_codeExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer codingId) {
        return mapper.deleteByPrimaryKey(codingId);
    }

    @Override
    public int insert(unified_code record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(unified_code record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<unified_code> selectByExample(unified_codeExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public unified_code selectByPrimaryKey(Integer codingId) {
        return mapper.selectByPrimaryKey(codingId);
    }

    @Override
    public int updateByExampleSelective(unified_code record, unified_codeExample example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(unified_code record, unified_codeExample example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(unified_code record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(unified_code record) {
        return mapper.updateByPrimaryKey(record);
    }

    public void setMapper(unified_codeMapper mapper) {
        this.mapper = mapper;
    }

    //@Override
    public List<unified_code> getAllUnifiedCodes() {
        // 在这里调用 Mapper 的方法来获取所有数据
        return mapper.selectByExample(null); // 使用 null 作为条件获取全部数据
    }
}
