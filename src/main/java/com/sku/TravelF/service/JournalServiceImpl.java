package com.sku.TravelF.service;

import com.sku.TravelF.domain.Journal;
import com.sku.TravelF.domain.enums.JournalType;
import com.sku.TravelF.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{
    private final JournalRepository journalRepository;
    private final UserService userService;


    // 페이징 처리된 모든 게시물 반환
    public Page<Journal> findJournalList(String journalType, String Search, Pageable pageable) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        if(Search.equals ("0"))
            return journalRepository.findAllByJournalTypeOrderByModifiedDateDesc(Type, pageable);
        else
            return journalRepository.findAllByJournalTypeAndTitleContainingOrderByModifiedDateDesc(Type, Search, pageable);
    }

    // 페이징 처리된 특정 타입 게시물 반환
    public Page<Journal> findJournalType(String journalType, String Search, Pageable pageable, HttpSession session) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());

        if(Search.equals ("0"))
            return journalRepository.findJournalByJournalTypeAndUserOrderByModifiedDateDesc(Type, userService.findUser(session), pageable);
        else
            return journalRepository.findJournalByJournalTypeAndTitleContainingAndUserOrderByModifiedDateDesc(Type, Search, userService.findUser(session), pageable);
    }

    // 페이징 처리된 특정 지역 게시물 반환
    public Page<Journal> findAreaCode(String journalType, String areaCode, String Search, Pageable pageable) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());

        if(Search.equals ("0"))
            return journalRepository.findJournalByJournalTypeAndAreaCodeOrderByModifiedDateDesc(Type, areaCode, pageable);
        else
            return journalRepository.findJournalByJournalTypeAndAreaCodeAndTitleContainingOrderByModifiedDateDesc(Type, areaCode, Search, pageable);
    }

    // 페이징 처리된 특정 타입과 특정 지역 게시물 반환
    public Page<Journal> findJournalTypeAndAreaCode(String journalType, String areaCode, String Search, Pageable pageable, HttpSession session) {
        JournalType Type;
        if(journalType.equals ("1")){
            Type = JournalType.alone;
        }else if(journalType.equals ("0")){
            Type = JournalType.everyone;
        }else {
            Type = null;
        }
        pageable = PageRequest.of(pageable.getPageNumber () <= 0 ? 0 : pageable.getPageNumber ()-1, pageable.getPageSize ());
        if(Search.equals ("0"))
            return journalRepository.findJournalByJournalTypeAndAreaCodeAndUserOrderByModifiedDateDesc(Type, areaCode, userService.findUser(session), pageable);
        else
            return journalRepository.findJournalByJournalTypeAndAreaCodeAndTitleContainingAndUserOrderByModifiedDateDesc(Type, areaCode, Search, userService.findUser(session), pageable);

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

       // String ProjectPath = System.getProperty ("user.dir") + "~/app/step1/TravelF/src/main/webapp/image";
        String ProjectPath = "/home/ec2-user/app/step1/TravelF/src/main/resources/static/image";
        ///home/ec2-user/app/step1/TravelF/src/main/webapp/image;

        UUID uuid = UUID.randomUUID ();
        String fileName = uuid + "_" + file.getOriginalFilename ();
        File saveFile = new File (ProjectPath, fileName);
        log.info("file: {}", saveFile);

        //Image image = new Image ();
        //image.setFileName (fileName);
        //image.setUploadPath ("/image/" + fileName);
        if(Objects.equals (file.getOriginalFilename (), "")) {
        }else{
            file.transferTo (saveFile);
            journal.setFileName (fileName);
            journal.setUploadPath ("/home/ec2-user/app/step1/TravelF/src/main/resources/static/image/" + fileName);
        }
        Journal saved = journalRepository.save (journal);
        //image.setJournal (saved);
        //imageService.save(image);
        //return saved;
    }

    // 게시글 수정
    public void update(Journal journal) {
        Journal updated = journalRepository.save (journal);
    }

    // 게시글 삭제
    public void deleteById(Long id){
        Optional<Journal> find = journalRepository.findById (id);
        //파일 경로 지정String ProjectPath = System.getProperty ("user.dir") + "/src/main/webapp/image";
        String ProjectPath = "/home/ec2-user/app/step1/TravelF/src/main/resources/static/image";
        //현재 게시판에 존재하는 파일객체를 만듬
        File savedFile = new File (ProjectPath, find.get ().getFileName ());

        if(savedFile.exists()) { // 파일이 존재하면
            savedFile.delete(); // 파일 삭제
        }
        journalRepository.deleteById (id);
    }
}
