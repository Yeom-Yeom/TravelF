package com.sku.TravelF.repository;

import com.sku.TravelF.domain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByAreacodeAndSigungucode(String areaCode, String sigunguCode, Pageable pageable);
    Page<Tour> findByAreacodeAndSigungucodeAndTitleContaining(String areaCode, String sigunguCode, String title, Pageable pageable);
}
