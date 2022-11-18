package com.sku.TravelF.domain.repository;

import com.sku.TravelF.domain.Recommended;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedRepository extends JpaRepository<Recommended, Long> {

    //    @Query("select Top(5) r from Recommended r where r.hashtag <> ?1 and r.hashtag like concat('%', ?2, '%')")
    List<Recommended> findTop5ByHashtagNotAndHashtagContainingAndAreaContaining(@Param("hashtag") String Hashtag, @Param("hashtag2") String Hashtag2, @Param ("area") String area);

    Recommended findRecommendedById(Long id);
}