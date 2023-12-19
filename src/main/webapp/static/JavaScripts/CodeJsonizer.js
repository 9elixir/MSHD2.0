function fillJsonTextArea(){
    var jsontextarea = document.getElementById("jsonTextarea");
    var code=document.getElementById("Code").value;
    var description=document.getElementById("Describe").value;
    jsontextarea.value=codeToJson(code,description);
}

function fillCodeAndDescription(){
    var descriptionInput=document.getElementById("Describe");
    var codeInput=document.getElementById("Code");
    var json= document.getElementById("jsonTextarea").value;
    descriptionInput.value=jsonToDescription(json);
    codeInput.value=jsonToCode(json);
}

function codeToJson(code, description) {
    if (code.length !== 36) return "Wrong code format!";
    if (description==null)description=" ";

    var codeJson = {
        "GeographyCode": code.substring(0, 12),
        "TimeCode": code.substring(12, 26),
        "SourceCode": code.substring(26, 29),
        "CarrierCode": code.substring(29, 30),
        "DisasterCode": code.substring(30, 36),
        "Description": description
    };

    return JSON.stringify(codeJson,null,2);
}

function jsonToCode(json) {
    if (!ifJson(json)) return "Json format error.";

    var codeJson = JSON.parse(json);
    return codeJson.GeographyCode + codeJson.TimeCode + codeJson.SourceCode + codeJson.CarrierCode + codeJson.DisasterCode;
}

function jsonToDescription(json) {
    if (!ifJson(json)) return "Json format error.";

    var codeJson = JSON.parse(json);
    return codeJson.Description;
}

function ifJson(input) {
    try {
        JSON.parse(input);
        return true;
    } catch (error) {
        return false;
    }
}