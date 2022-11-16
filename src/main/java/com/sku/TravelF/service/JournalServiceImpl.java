package com.sku.TravelF.service;

import com.sku.TravelF.domain.Journal;
import com.sku.TravelF.domain.enums.JournalType;
import com.sku.TravelF.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{
    private final JournalRepository journalRepository;
    private final ImageService imageService;
    private final UserService memberService;


    // 페이징 처리된 모든 게시물 반환
    public Page<Journal> findJournalList(String journalType,Pageable pageable) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        return journalRepository.findAllByJournalTypeOrderByModifiedDateDesc(Type, pageable);
    }

    // 페이징 처리된 특정 타입 게시물 반환
    public Page<Journal> findJournalType(String journalType, Pageable pageable, HttpSession session) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        return journalRepository.findJournalByJournalTypeAndUserOrderByModifiedDateDesc(Type, pageable,memberService.findUser(session));
    }

    // 페이징 처리된 특정 지역 게시물 반환
    public Page<Journal> findAreaCode(String areaCode, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        return journalRepository.findJournalByAreaCodeOrderByModifiedDateDesc (areaCode, pageable);
    }

    // 페이징 처리된 특정 타입과 특정 지역 게시물 반환
    public Page<Journal> findJournalTypeAndAreaCode(String journalType, String areaCode, Pageable pageable, HttpSession session) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        System.out.println("asdf : "+memberService.findUser(session));
        return journalRepository.findJournalByJournalTypeAndUserAndAreaCodeOrderByModifiedDateDesc(Type, areaCode, pageable, memberService.findUser(session));
    }

    // 탑5 조회
    public List<Journal> findTop5() {
        return journalRepository.findTop5ByJournalTypeOrderByModifiedDateDesc (JournalType.everyone);
    }

    // 게시글 ID로 조회
    public Journal findJournalById(Long id) {
        Journal journal = journalRepository.findById (id).orElse (new Journal());
        return journal;
    }

    // 게시글 저장
    public void save(Journal journal, MultipartFile file) throws Exception{
        String ProjectPath = System.getProperty ("user.dir") + "\\src\\main\\webapp\\image";

        UUID uuid = UUID.randomUUID ();

        String fileName = uuid + "_" + file.getOriginalFilename ();

        File saveFile = new File (ProjectPath, fileName);


        //Image image = new Image ();
        //image.setFileName (fileName);
        //image.setUploadPath ("/image/" + fileName);
        if(Objects.equals (file.getOriginalFilename (), "")) {
        }else{
            file.transferTo (saveFile);
            journal.setFileName (fileName);
            journal.setUploadPath ("/image/" + fileName);
        }
        Journal saved = journalRepository.save (journal);
        //image.setJournal (saved);
        //imageService.save(image);
        //return saved;
    }

    // 게시글 삭제
    public void deleteById(Long id){
        journalRepository.deleteById (id);
    }
}
