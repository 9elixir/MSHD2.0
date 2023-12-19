package com.qiu.service;

import com.qiu.pojo.*;

import java.util.List;

public interface CodeInfoService{
    carrier_code_info selectCarrierCodeByUnifiedCode(unified_code UnifiedCode);

    disaster_code_info selectDisasterCodeByUnifiedCode(unified_code UnifiedCode);

    geo_code_info selectGeoCodeByUnifiedCode(unified_code UnifiedCode);

    source_code_info selectSourceCodeByUnifiedCode(unified_code UnifiedCode);

    List<unified_code_Image_Relation> getAllImageRelationByUnifiedCode(unified_code UnifiedCode);
    List<unified_code_Audio_Relation> getAllAudioRelationByUnifiedCode(unified_code UnifiedCode);
    List<unified_code_Video_Relation> getAllVideoRelationByUnifiedCode(unified_code UnifiedCode);
    byte[] selectImageByImageRelation(unified_code_Image_Relation ImageRelation);
    byte[] selectAudioByAudioRelation(unified_code_Audio_Relation AudioRelation);
    byte[] selectVideoByVideoRelation(unified_code_Video_Relation VideoRelation);

    //有多少图片
    int countImageOfUnifiedCode(unified_code UnifiedCode);

    List<unified_code> getAuxiliaryCodeListByMainCode(unified_code MainCode);

    int insertCode(unified_code code);

    int insertPic(unified_code code,byte[] imageBytes);
    int insertAudio(unified_code code,byte[] audioBytes);

    int insertVideo(unified_code code,byte[] videoBytes);

    int DropImageRelation(unified_code_Image_Relation imageRelation);

    int DropVideoRelation(unified_code_Video_Relation videoRelation);

    public List<unified_code> getCodeListByCityAndTime(String firstCode,String lastCode,String curTime,String nextTime);
}
