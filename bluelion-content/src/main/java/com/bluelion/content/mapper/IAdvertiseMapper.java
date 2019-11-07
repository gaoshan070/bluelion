package com.bluelion.content.mapper;

import com.bluelion.shared.model.Advertise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAdvertiseMapper {

    List<Advertise> getAds(@Param("position") int position);
}
