package com.bluelion.content.mapper;

import com.bluelion.shared.model.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IContentMapper {

    List<Content> getContentList(@Param("code") String code);
}
