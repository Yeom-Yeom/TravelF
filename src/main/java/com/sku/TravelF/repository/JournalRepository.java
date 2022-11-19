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
    Page<Journal> findAllByJournalTypeOrderByModifiedDateDesc(JournalType journalType, Pageable pageable);
    Page<Journal> findAllByJournalTypeAndTitleContainingOrderByModifiedDateDesc(JournalType journalType, String title, Pageable pageable);

    Page<Journal> findJournalByJournalTypeAndUserOrderByModifiedDateDesc(JournalType journalType, User user, Pageable pageable);
    Page<Journal> findJournalByJournalTypeAndTitleContainingAndUserOrderByModifiedDateDesc(JournalType journalType, String title, User user, Pageable pageable);

    Page<Journal> findJournalByJournalTypeAndAreaCodeOrderByModifiedDateDesc(JournalType journalType, String areaCode, Pageable pageable);
    Page<Journal> findJournalByJournalTypeAndAreaCodeAndTitleContainingOrderByModifiedDateDesc(JournalType journalType, String areaCode, String title, Pageable pageable);

    Page<Journal> findJournalByJournalTypeAndAreaCodeAndUserOrderByModifiedDateDesc(JournalType journalType, String areaCode, User user, Pageable pageable);
    Page<Journal> findJournalByJournalTypeAndAreaCodeAndTitleContainingAndUserOrderByModifiedDateDesc(JournalType journalType, String areaCode, String title, User user, Pageable pageable);

    Journal findByUser(User user);
    List<Journal> findTop5ByJournalTypeOrderByModifiedDateDesc(JournalType journalType);
}