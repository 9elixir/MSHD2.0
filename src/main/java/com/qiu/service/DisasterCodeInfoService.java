package com.qiu.service;

import com.qiu.pojo.disaster_code_info;
import com.qiu.pojo.disaster_code_infoExample;

import java.util.List;

public interface DisasterCodeInfoService {
    int countByExample(disaster_code_infoExample example);

    int deleteByExample(disaster_code_infoExample example);

    int deleteByPrimaryKey(String disasterCode);

    int insert(disaster_code_info record);

    int insertSelective(disaster_code_info record);

    List<disaster_code_info> selectByExample(disaster_code_infoExample example);

    disaster_code_info selectByPrimaryKey(String disasterCode);

    int updateByExampleSelective(disaster_code_info record, disaster_code_infoExample example);

    int updateByExample(disaster_code_info record, disaster_code_infoExample example);

    int updateByPrimaryKeySelective(disaster_code_info record);

    int updateByPrimaryKey(disaster_code_info record);
}
