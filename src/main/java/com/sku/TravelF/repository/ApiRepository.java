package com.sku.TravelF.repository;

import com.sku.TravelF.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<Tour, String> {
}
