package com.qiu.service;

import com.qiu.pojo.*;

import java.util.List;

public interface CodeInfoService{
    carrier_code_info selectCarrierCodeByUnifiedCode(unified_code UnifiedCode);

    disaster_code_info selectDisasterCodeByUnifiedCode(unified_code UnifiedCode);

    geo_code_info selectGeoCodeByUnifiedCode(unified_code UnifiedCode);

    source_code_info selectSourceCodeByUnifiedCode(unified_code UnifiedCode);

    List<unified_code_Image_Relation> getAllImageRelationByUnifiedCode(unified_code UnifiedCode);

    byte[] selectImageByImageRelation(unified_code_Image_Relation ImageRelation);

    //有多少图片
    int countImageOfUnifiedCode(unified_code UnifiedCode);

    List<unified_code> getAuxiliaryCodeListByMainCode(unified_code MainCode);

    int insertCode(unified_code code);

    int insertPic(unified_code code, byte[] imageBytes);

}
