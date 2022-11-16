package com.sku.TravelF.service;

import com.sku.TravelF.domain.User;

import javax.servlet.http.HttpSession;

public interface UserService {
    public User JoinCheck(User member);
    public User LoginCheck(User member, HttpSession session);
    public User Join(User member);
    public void logout(HttpSession session);
    public User findUser(HttpSession session);
    //public void deleteById(String id); 탈퇴
}
