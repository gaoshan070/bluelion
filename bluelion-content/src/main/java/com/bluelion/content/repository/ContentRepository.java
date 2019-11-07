package com.bluelion.content.repository;

import com.bluelion.content.mapper.IContentMapper;
import com.bluelion.shared.model.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentRepository {

    @Autowired
    private IContentMapper contentMapper;

    public List<Content> getContentList(String code) {
        return contentMapper.getContentList(code);
    }
}
