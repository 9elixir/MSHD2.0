package com.qiu.service;

import com.qiu.Mapper.carrier_code_infoMapper;
import com.qiu.Mapper.disaster_code_infoMapper;
import com.qiu.Mapper.geo_code_infoMapper;
import com.qiu.Mapper.source_code_infoMapper;
import com.qiu.pojo.*;

import com.qiu.service.CodeInfoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeInfoServiceImpl implements CodeInfoService{
    @Autowired
    private carrier_code_infoMapper CarrierMapper;
    @Autowired
    private disaster_code_infoMapper DisasterMapper;
    @Autowired
    private geo_code_infoMapper GeoMapper;
    @Autowired
    private source_code_infoMapper SourceMapper;
    public void setCarrierMapper(carrier_code_infoMapper CarrierMapper) {
        this.CarrierMapper = CarrierMapper;
    }
    public void setDisasterMapper(disaster_code_infoMapper DisasterMapper) {
        this.DisasterMapper = DisasterMapper;
    }
    public void setGeoMapper(geo_code_infoMapper GeoMapper) {
        this.GeoMapper = GeoMapper;
    }
    public void setSourceMapper(source_code_infoMapper SourceMapper) {
        this.SourceMapper = SourceMapper;
    }

    @Override
    public carrier_code_info selectCarrierCodeByUnifiedCode(unified_code UnifiedCode){
        return CarrierMapper.selectByPrimaryKey(UnifiedCode.getCarrierCode());
    }

    @Override
    public disaster_code_info selectDisasterCodeByUnifiedCode(unified_code UnifiedCode){
        return DisasterMapper.selectByPrimaryKey(UnifiedCode.getDisasterCode());
    }

    @Override
    public geo_code_info selectGeoCodeByUnifiedCode(unified_code UnifiedCode){
        return GeoMapper.selectByPrimaryKey(UnifiedCode.getGeoCode());
    }

    @Override
    public source_code_info selectSourceCodeByUnifiedCode(unified_code UnifiedCode) {
        return SourceMapper.selectByPrimaryKey(UnifiedCode.getSourceCode());
    }


}
