package com.bluelion.content.mapper;

import com.bluelion.shared.model.PrintTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPrintTemplateMapper {

    List<PrintTemplate> getPrintTemplates();
}
