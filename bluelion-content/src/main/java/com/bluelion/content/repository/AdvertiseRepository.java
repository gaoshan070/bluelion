package com.bluelion.content.repository;

import com.bluelion.content.mapper.IAdvertiseMapper;
import com.bluelion.shared.model.Advertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvertiseRepository {

    @Autowired
    IAdvertiseMapper advertiseMapper;

    public List<Advertise> getAds(int position) {
        return advertiseMapper.getAds(position);
    }
}
