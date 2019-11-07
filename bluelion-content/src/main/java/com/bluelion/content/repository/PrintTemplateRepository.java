package com.bluelion.content.repository;

import com.bluelion.content.mapper.IPrintTemplateMapper;
import com.bluelion.shared.model.PrintTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrintTemplateRepository {

    @Autowired
    private IPrintTemplateMapper printTemplateMapper;

    public List<PrintTemplate> getPrintTemplates() {
        return printTemplateMapper.getPrintTemplates();
    }
}
