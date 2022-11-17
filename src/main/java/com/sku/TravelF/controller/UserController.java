package com.sku.TravelF.controller;

import com.sku.TravelF.domain.User;
import com.sku.TravelF.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {

    final UserService memberService;
    private String Join_message = "";
    private String login_message = "";

    @RequestMapping("Join")
    public ModelAndView Join(ModelAndView mav) {
        mav.setViewName("user/Join");
        mav.addObject("Join_message", Join_message);
        return mav;
    }

    @RequestMapping("Join_check")
    public ModelAndView Join_check(@ModelAttribute User member, ModelAndView mav) {
        //String id = memberService.JoinCheck(dto);
        User id = memberService.JoinCheck(member);

        if (id != null) { // 가입 실패 시
            mav.setViewName("redirect:Join"); // 뷰의 이름
            Join_message = "fail";
        } else { // 가입 성공 시
            memberService.Join (member);//인서트
            mav.setViewName("redirect:login");
            Join_message = "success";
        }
        login_message = "";

        return mav;
    }

    @GetMapping("login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("user/login");
        mav.addObject("Join_message", Join_message);
        mav.addObject("login_message", login_message);
        return mav;
    }

    @RequestMapping("login_check")
    public ModelAndView login_check(@ModelAttribute User member, HttpSession session, ModelAndView mav) {
        //String name = memberService.LoginCheck(dto, session);
        User name = memberService.LoginCheck(member, session);

        if (name != null) { // 로그인 성공 시
            mav.setViewName("redirect:/travelf"); // 뷰의 이름
            Join_message = "";
            login_message = "";
        } else { // 로그인 실패 시
            mav.setViewName("redirect:login");
            Join_message = "";
            login_message = "error";
        }
        return mav;
    }

    @RequestMapping("logout")
    public ModelAndView logout(HttpSession session, ModelAndView mav) {
        Join_message = "";
        login_message = "";
        memberService.logout (session);
        mav.setViewName("redirect:/");
        //mav.addObject("logout_message", null);
        return mav;
    }
}
