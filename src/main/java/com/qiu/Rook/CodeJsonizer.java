package com.qiu.Rook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.var;

public class CodeJsonizer {
    static public String codeToJson(String code,String description){
        if(code.length()!=36)return "wrong code format";
        JsonObject codeJson=new JsonObject();
        codeJson.addProperty("GeographyCode",code.substring(0,12));
        codeJson.addProperty("TimeCode",code.substring(12,26));
        codeJson.addProperty("SourceCode",code.substring(26,29));
        codeJson.addProperty("CarrierCode",code.substring(29,30));
        codeJson.addProperty("DisasterCode",code.substring(30,36));
        codeJson.addProperty("Description",description);
        return codeJson.toString();
    }

    static public String jsonToCode(String json){
        if(!ifJson(json))return "Json format error.";
        JsonObject codeJson= JsonParser.parseString(json).getAsJsonObject();
        return codeJson.get("GeographyCode").toString()+codeJson.get("TimeCode").toString()+codeJson.get("SourceCode").toString()+codeJson.get("CarrierCode").toString()+codeJson.get("DisasterCode").toString();
    }

    static public String jsonToDescription(String json){
        if(!ifJson(json))return "Json format error.";
        JsonObject codeJson= JsonParser.parseString(json).getAsJsonObject();
        return codeJson.get("Description").toString();
    }

    static private Boolean ifJson(String input){
        try {
            JsonParser parser = new JsonParser();
            JsonParser.parseString(input);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
