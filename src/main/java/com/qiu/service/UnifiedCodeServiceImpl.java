package com.qiu.service;

import com.qiu.Mapper.unified_codeMapper;
import com.qiu.pojo.unified_code;
import com.qiu.pojo.unified_codeExample;
import com.qiu.service.UnifiedCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<unified_code> queryUnifiedCodes(String queryCode) {
        unified_codeExample example = new unified_codeExample();
        unified_codeExample.Criteria criteria = example.createCriteria();

        if (queryCode != null) {
            String geoCode = queryCode.substring(0, Math.min(queryCode.length(), 12));
            String timeCode = queryCode.length() >= 26 ? queryCode.substring(12, Math.min(queryCode.length(), 26)) : null;
            String sourceCode = queryCode.length() >= 29 ? queryCode.substring(26, Math.min(queryCode.length(), 29)) : null;
            String carrierCode = queryCode.length() >= 30 ? queryCode.substring(29, Math.min(queryCode.length(), 30)) : null;
            String disasterCode = queryCode.length() >= 36 ? queryCode.substring(30, Math.min(queryCode.length(), 36)) : null;

            if (geoCode != null) {
                criteria.andGeoCodeLike("%" + geoCode + "%");
            }
            if (timeCode != null) {
                criteria.andTimeCodeLike("%" + timeCode + "%");
            }
            if (sourceCode != null) {
                criteria.andSourceCodeLike("%" + sourceCode + "%");
            }
            if (carrierCode != null) {
                criteria.andCarrierCodeLike("%" + carrierCode + "%");
            }
            if (disasterCode != null) {
                criteria.andDisasterCodeLike("%" + disasterCode + "%");
            }

            return mapper.selectByExample(example);
        } else {
            // 处理错误情况
            System.err.println("queryCode 为空");
            return getAllUnifiedCodes(); // 或者返回适当的空结果
        }
    }


}
