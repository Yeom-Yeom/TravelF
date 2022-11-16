package com.sku.TravelF.controller;

import com.sku.TravelF.domain.Board;
import com.sku.TravelF.domain.Journal;
import com.sku.TravelF.service.BoardService;
import com.sku.TravelF.service.JournalService;
import com.sku.TravelF.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    final UserService memberService;
    final BoardService boardService;
    final JournalService journalService;
    @GetMapping("")
    public ModelAndView Home(HttpSession session, ModelAndView mav){
        if(session.getAttribute ("name") != null){
            memberService.logout (session);
        }
        List<Journal> result = journalService.findTop5 ();
        List<Board> result1 = boardService.findTop5 ("공지사항");
        List<Board> result2 = boardService.findTop5 ("자유게시판");
        mav.addObject("result", result);
        mav.addObject("result1", result1);
        mav.addObject("result2", result2);
        mav.setViewName("travelf"); // 뷰의 이름
        return mav;
    }

    @GetMapping("travelf")
    public ModelAndView TourList(HttpSession session, ModelAndView mav){
        List<Journal> result = journalService.findTop5 ();
        List<Board> result1 = boardService.findTop5 ("공지사항");
        List<Board> result2 = boardService.findTop5 ("자유게시판");
        mav.addObject("result", result);
        mav.addObject("result1", result1);
        mav.addObject("result2", result2);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.setViewName("travelf"); // 뷰의 이름
        return mav;
    }

}