package com.bluelion.content.service;

import com.bluelion.content.repository.PrintTemplateRepository;
import com.bluelion.shared.model.PrintTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintTemplateService {

    @Autowired
    private PrintTemplateRepository printTemplateRepository;

    public List<PrintTemplate> getPrintTemplates() {
        return printTemplateRepository.getPrintTemplates();
    }
}
