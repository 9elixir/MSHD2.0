package com.qiu.service;

import com.qiu.pojo.carrier_code_info;
import com.qiu.pojo.carrier_code_infoExample;

import com.qiu.pojo.disaster_code_info;
import com.qiu.pojo.disaster_code_infoExample;

import com.qiu.pojo.geo_code_info;
import com.qiu.pojo.geo_code_infoExample;

import com.qiu.pojo.source_code_info;
import com.qiu.pojo.source_code_infoExample;

import com.qiu.pojo.unified_code;
import com.qiu.pojo.unified_codeExample;

import java.util.List;

public interface CodeInfoService{
    carrier_code_info selectCarrierCodeByUnifiedCode(unified_code UnifiedCode);

    disaster_code_info selectDisasterCodeByUnifiedCode(unified_code UnifiedCode);

    geo_code_info selectGeoCodeByUnifiedCode(unified_code UnifiedCode);

    source_code_info selectSourceCodeByUnifiedCode(unified_code UnifiedCode);
}
