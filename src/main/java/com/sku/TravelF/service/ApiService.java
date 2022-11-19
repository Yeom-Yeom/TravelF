package com.sku.TravelF.service;

import com.sku.TravelF.domain.Board;
import com.sku.TravelF.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApiService {
    public List<Tour> CallRegion(String number);
    public List<Tour> CallApi(String areaCode, String sigunguCode);
    public Tour CallDetail(String contentId);

    public Page<Tour> findTour(String areaCode, String sigunguCode, String search, Pageable pageable);
}
