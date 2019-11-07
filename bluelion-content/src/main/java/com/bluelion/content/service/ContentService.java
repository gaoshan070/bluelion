package com.bluelion.content.service;

import com.bluelion.content.repository.ContentRepository;
import com.bluelion.shared.model.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getQaList() {
        return contentRepository.getContentList("QA");
    }
}
