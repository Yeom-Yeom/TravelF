package com.sku.TravelF.service;

import com.sku.TravelF.domain.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

public interface JournalService {
    public Page<Journal> findJournalList(String journalType, String Search,Pageable pageable);
    public Page<Journal> findJournalType(String journalType, String Search, Pageable pageable, HttpSession session);
    public Page<Journal> findAreaCode(String journalType, String areaCode, String Search, Pageable pageable);
    public Page<Journal> findJournalTypeAndAreaCode(String journalType, String areaCode, String Search, Pageable pageable,HttpSession session);
    public List<Journal> findTop5();
    public Journal findJournalById(Long id);
    public void save(Journal journal, MultipartFile file) throws Exception;
    public void update(Journal journal);
    public void deleteById(Long id);
}
