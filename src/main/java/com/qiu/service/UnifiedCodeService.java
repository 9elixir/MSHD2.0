package com.qiu.service;

import com.qiu.pojo.unified_code;
import com.qiu.pojo.unified_codeExample;

import java.util.List;

public interface UnifiedCodeService {
    int countByExample(unified_codeExample example);

    int deleteByExample(unified_codeExample example);

    int deleteByPrimaryKey(Integer codingId);

    int insert(unified_code record);

    int insertSelective(unified_code record);

    List<unified_code> selectByExample(unified_codeExample example);

    unified_code selectByPrimaryKey(Integer codingId);

    int updateByExampleSelective(unified_code record, unified_codeExample example);

    int updateByExample(unified_code record, unified_codeExample example);

    int updateByPrimaryKeySelective(unified_code record);

    int updateByPrimaryKey(unified_code record);

    List<unified_code> getAllUnifiedCodes();
}
