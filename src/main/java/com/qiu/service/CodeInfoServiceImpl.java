package com.qiu.service;

import com.qiu.Mapper.*;
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
    @Autowired
    private imageTableMapper ImageMapper;
    @Autowired
    private unified_code_Image_RelationMapper ImageRelationMapper;

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
    public void setImageMapper(imageTableMapper ImageMapper){this.ImageMapper = ImageMapper;}
    public void setImageRelationMapper(unified_code_Image_RelationMapper ImageRelationMapper){this.ImageRelationMapper=ImageRelationMapper;}

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

    @Override
    public List<unified_code_Image_Relation> getAllImageRelationByUnifiedCode(unified_code UnifiedCode){
        // 创建关联关系的查询条件
        unified_code_Image_RelationExample example = new unified_code_Image_RelationExample();
        unified_code_Image_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(UnifiedCode.getCodingId());

        // 使用Mapper查询关联列表
        List<unified_code_Image_Relation> relations = ImageRelationMapper.selectByExample(example);
        return relations;
    }
    @Override
    public byte[] selectImageByImageRelation(unified_code_Image_Relation ImageRelation){
        return ImageMapper.selectByPrimaryKey(ImageRelation.getImageId()).getImageData();
    }

    @Override
    public int countImageOfUnifiedCode(unified_code UnifiedCode) {
        // 创建关联关系的查询条件
        unified_code_Image_RelationExample example = new unified_code_Image_RelationExample();
        unified_code_Image_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(UnifiedCode.getCodingId());

        // 使用Mapper查询关联数量
        int relationCount = ImageRelationMapper.countByExample(example);
        return relationCount;

    }





}
