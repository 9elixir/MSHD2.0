package com.qiu.service;

import com.qiu.pojo.carrier_code_info;
import com.qiu.pojo.carrier_code_infoExample;
import com.qiu.pojo.unified_code;

import java.util.List;

public interface CarrierCodeInfoService {
    int countByExample(carrier_code_infoExample example);

    int deleteByExample(carrier_code_infoExample example);

    int deleteByPrimaryKey(String carrierCode);

    int insert(carrier_code_info record);

    int insertSelective(carrier_code_info record);

    List<carrier_code_info> selectByExample(carrier_code_infoExample example);

    carrier_code_info selectByPrimaryKey(String carrierCode);

    int updateByExampleSelective(carrier_code_info record, carrier_code_infoExample example);

    int updateByExample(carrier_code_info record, carrier_code_infoExample example);

    int updateByPrimaryKeySelective(carrier_code_info record);

    int updateByPrimaryKey(carrier_code_info record);
}
