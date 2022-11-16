package com.sku.TravelF.service;

import com.sku.TravelF.domain.Tour;

import java.util.List;

public interface ApiService {
    public List<Tour> CallRegion(String number);
    public List<Tour> CallApi(String areaCode, String sigunguCode);
    public Tour CallDetail(String contentId);
}
