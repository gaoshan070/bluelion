package com.bluelion.content.service;

import com.bluelion.content.repository.AdvertiseRepository;
import com.bluelion.shared.constants.AdvertiseType;
import com.bluelion.shared.model.Advertise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiseService {

    @Autowired
    private AdvertiseRepository advertiseRepository;

    public List<Advertise> getStartAds() {
        return advertiseRepository.getAds(AdvertiseType.START.getIndex());
    }

    public List<Advertise> getInteralAds() {
        return advertiseRepository.getAds(AdvertiseType.INNER.getIndex());
    }
}
