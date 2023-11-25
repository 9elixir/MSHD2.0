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
    private unified_codeMapper UnifiedCodeMapper;
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

    public  void setUnifiedCodeMapper(unified_codeMapper UnifiedCodeMapper){this.UnifiedCodeMapper=UnifiedCodeMapper;}
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

    @Override
    public List<unified_code> getAuxiliaryCodeListByMainCode(unified_code MainCode){
        unified_codeExample example = new unified_codeExample();
        unified_codeExample.Criteria criteria = example.createCriteria();
        //时间地点相同
        criteria.andGeoCodeEqualTo(MainCode.getGeoCode());
        criteria.andTimeCodeEqualTo(MainCode.getTimeCode());

        List<unified_code> auxiliaryCode = UnifiedCodeMapper.selectByExample(example);
        auxiliaryCode.removeIf(code -> code.equals(MainCode));
        return auxiliaryCode;
    }

    @Override
    public int insertCode(unified_code code){
        unified_codeExample example = new unified_codeExample();
        unified_codeExample.Criteria criteria = example.createCriteria();
        //添加编码值
        criteria.andGeoCodeEqualTo(code.getGeoCode());
        criteria.andTimeCodeEqualTo(code.getTimeCode());
        criteria.andCarrierCodeEqualTo(code.getCarrierCode());
        criteria.andDisasterCodeEqualTo(code.getDisasterCode());
        criteria.andSourceCodeEqualTo(code.getSourceCode());

        List<unified_code> Code = UnifiedCodeMapper.selectByExample(example);
        if (!Code.isEmpty()) //已经有值了
            return 0;
        return UnifiedCodeMapper.insertSelective(code);
    }

    @Override
    public int insertPic(unified_code code, byte[] imageBytes){
        unified_codeExample example = new unified_codeExample();
        unified_codeExample.Criteria criteria = example.createCriteria();
        //添加编码值
        criteria.andGeoCodeEqualTo(code.getGeoCode());
        criteria.andTimeCodeEqualTo(code.getTimeCode());
        criteria.andCarrierCodeEqualTo(code.getCarrierCode());
        criteria.andDisasterCodeEqualTo(code.getDisasterCode());
        criteria.andSourceCodeEqualTo(code.getSourceCode());

        List<unified_code> Code = UnifiedCodeMapper.selectByExample(example);
        if (Code.isEmpty()) //还没有插入,则先进行插入
            UnifiedCodeMapper.insertSelective(code);
        //为了获取主键
        List<unified_code> codeList = UnifiedCodeMapper.selectByExample(example);
        unified_code code1 = codeList.get(0);

        //插入图片
        imageTable m_image = new imageTable();
        m_image.setImageData(imageBytes);
        ImageMapper.insertSelective(m_image);

        //插入关系
        unified_code_Image_Relation m_relation = new unified_code_Image_Relation();
        m_relation.setCodingId(code1.getCodingId()); //注意写的是code1
        m_relation.setImageId(m_image.getImageId()); //之前插入时，写回了id值
        System.out.println(m_image.getImageId());
        ImageRelationMapper.insert(m_relation);

        return 1;
    }


}
