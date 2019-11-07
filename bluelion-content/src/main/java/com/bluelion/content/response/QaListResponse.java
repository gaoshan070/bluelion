package com.bluelion.content.response;

import com.bluelion.shared.model.Content;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QaListResponse {

    @SerializedName("qa_list")
    private List<Content> qaList = new ArrayList<Content>();;
}
