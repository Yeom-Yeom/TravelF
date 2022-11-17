package com.sku.TravelF.repository;

import com.sku.TravelF.domain.Journal;
import com.sku.TravelF.domain.User;
import com.sku.TravelF.domain.enums.JournalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    Journal findByUser(User user);
    Page<Journal> findAllByJournalTypeOrderByModifiedDateDesc(JournalType journalType, Pageable pageable);
    Page<Journal> findJournalByJournalTypeAndUserOrderByModifiedDateDesc(JournalType journalType, Pageable pageable, User user);
    Page<Journal> findJournalByJournalTypeAndAreaCodeOrderByModifiedDateDesc(JournalType journalType, String areaCode, Pageable pageable);
    Page<Journal> findJournalByJournalTypeAndUserAndAreaCodeOrderByModifiedDateDesc(JournalType journalType, User user, String areaCode, Pageable pageable);
    List<Journal> findTop5ByJournalTypeOrderByModifiedDateDesc(JournalType journalType);
}
