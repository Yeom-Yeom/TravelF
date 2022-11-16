package com.sku.TravelF.service;

import com.sku.TravelF.domain.Recommended;
import java.util.List;

public interface RecommendedService {
    public List<Recommended> RecommendedList(String hashtag, String area);
    public Recommended getRecommended(Long id);
}
