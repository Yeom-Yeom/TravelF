package com.sku.TravelF.service;

import com.sku.TravelF.domain.Recommended;
import com.sku.TravelF.repository.RecommendedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendedServiceImpl implements RecommendedService {
    private final RecommendedRepository recommendedRepository;

    @Override
    public List<Recommended> RecommendedList(String hashtag, String area) {
        return recommendedRepository.findTop5ByHashtagNotAndHashtagContainingAndAreaContaining (" ", hashtag, area);
    }
    @Override
    public Recommended getRecommended(Long id){
        return recommendedRepository.findRecommendedById(id);
    }

}
