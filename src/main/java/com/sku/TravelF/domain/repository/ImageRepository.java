package com.sku.TravelF.domain.repository;

import com.sku.TravelF.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    ArrayList<Image> findAllByJournal_IdOrderByModifiedDateDesc(Long id);
}
