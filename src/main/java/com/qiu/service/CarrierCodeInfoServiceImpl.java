package com.qiu.service;

import com.qiu.Mapper.carrier_code_infoMapper;
import com.qiu.pojo.carrier_code_info;
import com.qiu.pojo.carrier_code_infoExample;
import com.qiu.service.CarrierCodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrierCodeInfoServiceImpl implements CarrierCodeInfoService {

    @Autowired
    private carrier_code_infoMapper mapper;

    @Override
    public int countByExample(carrier_code_infoExample example) {
        return mapper.countByExample(example);
    }

    @Override
    public int deleteByExample(carrier_code_infoExample example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String carrierCode) {
        return mapper.deleteByPrimaryKey(carrierCode);
    }

    @Override
    public int insert(carrier_code_info record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(carrier_code_info record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<carrier_code_info> selectByExample(carrier_code_infoExample example) {
        return mapper.selectByExample(example);
    }

    @Override
    public carrier_code_info selectByPrimaryKey(String carrierCode) {
        return mapper.selectByPrimaryKey(carrierCode);
    }

    @Override
    public int updateByExampleSelective(carrier_code_info record, carrier_code_infoExample example) {
        return mapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(carrier_code_info record, carrier_code_infoExample example) {
        return mapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(carrier_code_info record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(carrier_code_info record) {
        return mapper.updateByPrimaryKey(record);
    }

    public void setMapper(carrier_code_infoMapper mapper) {
        this.mapper = mapper;
    }

}
