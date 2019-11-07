package com.bluelion.content.response;

import com.bluelion.shared.model.Advertise;
import com.bluelion.shared.model.PrintTemplate;
import com.bluelion.shared.utils.JsonUtil;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class InitResponse {

    @SerializedName("ad_list")
    private List<Advertise> adList = new ArrayList<Advertise>();

    @SerializedName("print_templates")
    private List<Map<String, Object>> templateList = new ArrayList<Map<String,Object>>();

    public void parsePrintTemplate(List<PrintTemplate> templates) {
        if(templates != null && templates.size() > 0) {
            for(PrintTemplate template : templates) {
                this.templateList.add(JsonUtil.jsonStr2Bean(template.getParams(), Map.class));
            }
        }
    }
}
