package com.qiu.service;

import com.qiu.pojo.source_code_info;
import com.qiu.pojo.source_code_infoExample;

import java.util.List;

public interface SourceCodeInfoService {
    int countByExample(source_code_infoExample example);

    int deleteByExample(source_code_infoExample example);

    int deleteByPrimaryKey(String sourceCode);

    int insert(source_code_info record);

    int insertSelective(source_code_info record);

    List<source_code_info> selectByExample(source_code_infoExample example);

    source_code_info selectByPrimaryKey(String sourceCode);

    int updateByExampleSelective(source_code_info record, source_code_infoExample example);

    int updateByExample(source_code_info record, source_code_infoExample example);

    int updateByPrimaryKeySelective(source_code_info record);

    int updateByPrimaryKey(source_code_info record);
}
