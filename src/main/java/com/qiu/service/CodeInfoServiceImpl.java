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
    @Autowired
    private audioTableMapper AudioMapper;
    @Autowired
    private unified_code_Audio_RelationMapper AudioRelationMapper;
    @Autowired
    private videoTableMapper VideoMapper;
    @Autowired
    private unified_code_Video_RelationMapper VideoRelationMapper;

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
    public void setAudioMapper(audioTableMapper AudioMapper){this.AudioMapper = AudioMapper;}
    public void setAudioRelationMapper(unified_code_Audio_RelationMapper AudioRelationMapper){this.AudioRelationMapper=AudioRelationMapper;}
    public void setVideoMapper(videoTableMapper VideoMapper){this.VideoMapper = VideoMapper;}
    public void setVideoRelationMapper(unified_code_Video_RelationMapper VideoRelationMapper){this.VideoRelationMapper = VideoRelationMapper;}

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
    public List<unified_code_Audio_Relation> getAllAudioRelationByUnifiedCode(unified_code UnifiedCode){
        // 创建关联关系的查询条件
        unified_code_Audio_RelationExample example = new unified_code_Audio_RelationExample();
        unified_code_Audio_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(UnifiedCode.getCodingId());

        // 使用Mapper查询关联列表
        List<unified_code_Audio_Relation> relations = AudioRelationMapper.selectByExample(example);
        return relations;
    }
    @Override
    public List<unified_code_Video_Relation> getAllVideoRelationByUnifiedCode(unified_code UnifiedCode){
        /* 创建关联关系的查询条件 */
        unified_code_Video_RelationExample example = new unified_code_Video_RelationExample();
        unified_code_Video_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(UnifiedCode.getCodingId());
        /* 使用Mapper查询关联列表 */
        List<unified_code_Video_Relation> relations = VideoRelationMapper.selectByExample(example);
        return relations;
    }
    @Override
    public byte[] selectImageByImageRelation(unified_code_Image_Relation ImageRelation){
        return ImageMapper.selectByPrimaryKey(ImageRelation.getImageId()).getImageData();
    }
    @Override
    public byte[] selectAudioByAudioRelation(unified_code_Audio_Relation AudioRelation){
        return AudioMapper.selectByPrimaryKey(AudioRelation.getAudioId()).getAudioData();
    }
    @Override
    public byte[] selectVideoByVideoRelation(unified_code_Video_Relation VideoRelation){
        return VideoMapper.selectByPrimaryKey(VideoRelation.getVideoId()).getVideoData();
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

    @Override
    public int insertAudio(unified_code code,byte[] audioBytes){
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

        //插入音频
        audioTable m_audio = new audioTable();
        m_audio.setAudioData(audioBytes);
        AudioMapper.insertSelective(m_audio);

        //插入关系
        unified_code_Audio_Relation m_relation = new unified_code_Audio_Relation();
        m_relation.setCodingId(code1.getCodingId()); //注意写的是code1
        m_relation.setAudioId(m_audio.getAudioId()); //之前插入时，写回了id值
        System.out.println(m_audio.getAudioId());
        AudioRelationMapper.insert(m_relation);

        return 1;
    }

    @Override
    public int insertVideo(unified_code code,byte[] videoBytes){
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

        //插入视频
        videoTable m_video = new videoTable();
        m_video.setVideoData(videoBytes);
        VideoMapper.insertSelective(m_video);

        //插入关系
        unified_code_Video_Relation m_relation = new unified_code_Video_Relation();
        m_relation.setCodingId(code1.getCodingId()); //注意写的是code1
        m_relation.setVideoId(m_video.getVideoId()); //之前插入时，写回了id值
        System.out.println(m_video.getVideoId());
        VideoRelationMapper.insert(m_relation);
        return 1;
    }

    @Override
    public int DropImageRelation(unified_code_Image_Relation imageRelation){
        Integer id = ImageMapper.selectByPrimaryKey(imageRelation.getImageId()).getImageId();

        unified_code_Image_RelationExample example = new unified_code_Image_RelationExample();
        unified_code_Image_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(imageRelation.getCodingId());
        criteria.andImageIdEqualTo(imageRelation.getImageId());
        ImageRelationMapper.deleteByExample(example);

        ImageMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public int DropVideoRelation(unified_code_Video_Relation videoRelation){
        Integer id = VideoMapper.selectByPrimaryKey(videoRelation.getVideoId()).getVideoId();

        unified_code_Video_RelationExample example = new unified_code_Video_RelationExample();
        unified_code_Video_RelationExample.Criteria criteria = example.createCriteria();
        criteria.andCodingIdEqualTo(videoRelation.getCodingId());
        criteria.andVideoIdEqualTo(videoRelation.getVideoId());
        VideoRelationMapper.deleteByExample(example);

        VideoMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public List<unified_code> getCodeListByCityAndTime(String firstCode,String lastCode,String curTime,String nextTime){
        unified_codeExample example = new unified_codeExample();
        unified_codeExample.Criteria criteria = example.createCriteria();
        criteria.andGeoCodeGreaterThanOrEqualTo(firstCode);
        criteria.andGeoCodeLessThanOrEqualTo(lastCode);
        criteria.andTimeCodeGreaterThanOrEqualTo(curTime);
        criteria.andTimeCodeLessThan(nextTime);
        return UnifiedCodeMapper.selectByExample(example);
    }
}
