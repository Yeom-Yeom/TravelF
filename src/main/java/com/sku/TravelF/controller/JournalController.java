package com.sku.TravelF.controller;

import com.sku.TravelF.domain.*;
import com.sku.TravelF.domain.enums.JournalType;
import com.sku.TravelF.service.CommentService;
import com.sku.TravelF.service.ImageService;
import com.sku.TravelF.service.JournalService;
import com.sku.TravelF.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
@RequestMapping("/journal/*")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;
    private final UserService memberService;
    private final CommentService commentService;
    private final ImageService imageService;
    private String Journal_message = "";
    private String comment_message = "";

    @GetMapping({"journal", "journal/"})
    public ModelAndView journal(@RequestParam(value = "JournalType", defaultValue = "0") String JournalType, @RequestParam(value = "AreaCode", defaultValue = "0") String AreaCode,
                                @RequestParam(value = "Search", defaultValue = "0") String Search, @PageableDefault Pageable pageable, HttpSession session, ModelAndView mav)
    {
        if((JournalType.equals ("0") || JournalType.equals ("모두의 일지")) && AreaCode.equals ("0")){
            mav.addObject ("journalList", journalService.findJournalList (JournalType, Search, pageable));
        } //done
        else if((JournalType.equals ("1") || JournalType.equals ("나만의 일지")) &&AreaCode.equals ("0")){
            mav.addObject ("journalList", journalService.findJournalType (JournalType, Search, pageable,session));
        } //done
        else if((JournalType.equals ("0") || JournalType.equals ("모두의 일지")) && !AreaCode.equals ("0")){
            mav.addObject ("journalList", journalService.findAreaCode (JournalType, AreaCode, Search, pageable));
        } //done
        else if((JournalType.equals ("1") || JournalType.equals ("나만의 일지")) && !AreaCode.equals ("0")){
            mav.addObject ("journalList", journalService.findJournalTypeAndAreaCode (JournalType, AreaCode, Search, pageable, session));
        } //done

        mav.addObject("JournalType", JournalType);
        mav.addObject("AreaCode", AreaCode);
        mav.addObject("Search", Search);
        mav.addObject("Journal_message", Journal_message);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.setViewName("journal/journal"); // 뷰의 이름
        Journal_message = "";
        return mav;
    }

    @RequestMapping(value = "saveJournal", method = RequestMethod.POST)
    public ModelAndView saveJournal(@RequestParam(value = "Search", defaultValue = "0") String Search, @ModelAttribute Journal journal, HttpSession session, ModelAndView mav, MultipartFile file) throws Exception {
        User user = memberService.findUser (session);
        if(user != null) {
            journal.setUser (user);
            journalService.save (journal, file);
            Journal_message = "save";
        }
        else {
            Journal_message = "fail";
        }
        if(journal.getJournalType () == JournalType.everyone){
            mav.setViewName("redirect:journal?JournalType=" + 0 + "&AreaCode=" + journal.getAreaCode () + "&Search=" + Search); // 뷰의 이름
        }
        else {
            mav.setViewName("redirect:journal?JournalType=" + 1 + "&AreaCode=" + journal.getAreaCode () + "&Search=" + Search); // 뷰의 이름
        }
        return mav;
    }

    @RequestMapping(value = "updateJournal", method = RequestMethod.POST)
    public ModelAndView updateJournal(@RequestParam(value = "FJournalType", defaultValue = "0") String JournalType, @RequestParam(value = "FAreaCode", defaultValue = "0") String AreaCode,
                                      @RequestParam(value = "Search", defaultValue = "0") String Search, @ModelAttribute Journal journal, HttpSession session, ModelAndView mav, MultipartFile file) throws Exception {
        User user = memberService.findUser (session);
        Journal findJournal = journalService.findJournalById (journal.getId ());

        if(user != null) {
            if(findJournal.getUser () == user) {
                Journal persistJournal = journalService.findJournalById (journal.getId ());
                persistJournal.update (journal);
                journalService.update (persistJournal); // save = insert + update
                Journal_message = "update";
            }
            else {
                Journal_message = "user";
            }
        }
        else {
            Journal_message = "fail";
        }
        mav.setViewName("redirect:journal?JournalType=" + JournalType + "&AreaCode=" + journal.getAreaCode () + "&Search=" + Search); // 뷰의 이름
        return mav;
    }

    @RequestMapping(value = "deleteJournal", method = RequestMethod.POST)
    public ModelAndView deleteJournal(@RequestParam(value = "FJournalType", defaultValue = "0") String JournalType, @RequestParam(value = "FAreaCode", defaultValue = "0") String AreaCode,
                                      @RequestParam(value = "Search", defaultValue = "0") String Search, @ModelAttribute Journal journal, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Journal findJournal = journalService.findJournalById (journal.getId ());

        if(user != null) {
            if(findJournal.getUser () == user) {
                journalService.deleteById (journal.getId ());
                Journal_message = "delete";
            }
            else{
                Journal_message = "user";
            }
        }
        else {
            Journal_message = "fail";
        }

        mav.setViewName("redirect:journal?JournalType=" + JournalType + "&AreaCode=" + journal.getAreaCode () + "&Search=" + Search); // 뷰의 이름
        return mav;
    }

    @GetMapping({"form", "form/"})
    public ModelAndView form(@RequestParam(value = "JournalType", defaultValue = "0") String JournalType, @RequestParam(value = "AreaCode", defaultValue = "0") String AreaCode,
                             @RequestParam(value = "Search", defaultValue = "0") String Search, @RequestParam(value = "id", defaultValue = "0") Long id, HttpSession session, ModelAndView mav){
        mav.addObject ("Journal", journalService.findJournalById (id));
        mav.addObject("JournalType", JournalType);
        mav.addObject("AreaCode", AreaCode);
        mav.addObject("Search", Search);
        mav.addObject("Journal_message", Journal_message);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.setViewName("journal/form"); // 뷰의 이름
        return mav;
    }

    @GetMapping({"read", "read/"})
    public ModelAndView read(@RequestParam(value = "JournalType", defaultValue = "0") String JournalType, @RequestParam(value = "AreaCode", defaultValue = "0") String AreaCode,
                             @RequestParam(value = "Search", defaultValue = "0") String Search, @RequestParam(value = "id", defaultValue = "0") Long id, HttpSession session, ModelAndView mav){
        ArrayList<Comment> commentList = commentService.findJCommentList (id);
        ArrayList<Image> imageList = imageService.findImageList(id);
        mav.addObject ("Journal", journalService.findJournalById (id));
        mav.addObject ("commentList", commentList);
        mav.addObject ("imageList",   imageList);
        mav.addObject("JournalType", JournalType);
        mav.addObject("AreaCode", AreaCode);
        mav.addObject("Search", Search);
        mav.addObject("userid", session.getAttribute ("userid"));
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.addObject("comment_message", comment_message);
        mav.setViewName("journal/read"); // 뷰의 이름
        comment_message = "";
        return mav;
    }

    @GetMapping({"modify", "modify/"})
    public ModelAndView modify(@RequestParam(value = "JournalType", defaultValue = "0") String JournalType, @RequestParam(value = "AreaCode", defaultValue = "0") String AreaCode,
                               @RequestParam(value = "Search", defaultValue = "0") String Search, @RequestParam Long journalID, @RequestParam Long commentID, HttpSession session, ModelAndView mav) {
        Comment findComment = commentService.findCommentById (commentID);

        mav.addObject("journalID", journalID);
        mav.addObject("comment", findComment);
        mav.addObject("JournalType", JournalType);
        mav.addObject("AreaCode", AreaCode);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.addObject("userid", session.getAttribute ("userid"));

        mav.setViewName("journal/modify"); // 뷰의 이름
        return mav;
    }


    @RequestMapping(value = "saveComment", method = RequestMethod.POST)
    public ModelAndView saveComment(@RequestParam(value = "FJournalType", defaultValue = "0") String JournalType, @RequestParam(value = "FAreaCode", defaultValue = "0") String AreaCode, @ModelAttribute Comment comment, @RequestParam(value = "id2", defaultValue = "0") Long id, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Journal journal = journalService.findJournalById (id);
        if(user != null) {
            comment.setUser (user);
            comment.setJournal (journal);
            commentService.save (comment);
            comment_message = "save";
        }
        else {
            comment_message = "fail";
        }
        mav.setViewName("redirect:read?id=" + id + "&JournalType=" + JournalType + "&AreaCode=" + AreaCode); // 뷰의 이름
        return mav;
    }

    @RequestMapping("updateComment")
    public ModelAndView updateComment(@RequestParam(value = "FJournalType", defaultValue = "0") String JournalType, @RequestParam(value = "FAreaCode", defaultValue = "0") String AreaCode, @RequestParam Long journalID, @RequestParam Long commentID, @ModelAttribute Comment comment, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Comment findComment = commentService.findCommentById (commentID);

        if(user != null) {
            if(findComment.getUser () == user) {
                Comment persistComment = commentService.findCommentById (commentID);
                persistComment.update (comment);
                commentService.save (persistComment);
                comment_message = "update";
            }
            else {
                comment_message = "fail";
            }
        }
        mav.setViewName("redirect:read?id=" + journalID + "&JournalType=" + JournalType + "&AreaCode=" + AreaCode); // 뷰의 이름
        return mav;
    }

    @GetMapping("deleteComment")
    public ModelAndView deleteComment(@RequestParam(value = "JournalType", defaultValue = "0") String JournalType, @RequestParam(value = "AreaCode", defaultValue = "0") String AreaCode, @RequestParam Long journalID, @RequestParam Long commentID, HttpSession session, ModelAndView mav) {
        User user = memberService.findUser (session);
        Comment findComment = commentService.findCommentById (commentID);

        if(user != null) {
            if(findComment.getUser () == user) {
                commentService.deleteById (commentID);
                comment_message = "delete";
            }
            else {
                comment_message = "fail";
            }
        }
        mav.setViewName("redirect:read?id=" + journalID + "&JournalType=" + JournalType + "&AreaCode=" + AreaCode); // 뷰의 이름
        return mav;
    }
}
