package com.sku.TravelF.service;

import com.sku.TravelF.domain.User;
import com.sku.TravelF.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // 중복 아이디가 있나 = 아이디
    @Override
    public User JoinCheck(User member) {
        return userRepository.findByUserid (member.getUserid ());
    }

    // 가입이 되어 있나 = 아이디랑 패스워드
    @Override
    public User LoginCheck(User member, HttpSession session) {
        User user = userRepository.findByUseridAndPassword(member.getUserid (), member.getPassword ());

        if (user != null) { // 세션 변수 저장
            session.setAttribute("userid", user.getUserid());
            session.setAttribute("name", user.getName ());
        }
        return user;
    }

    // 가입
    @Override
    public User Join(User member) {
/*        System.out.println (user.getUserid ());
        System.out.println (user.getPassword ());
        System.out.println (user.getName ());
        System.out.println (user.getEmail ());*/
        User savedUser = userRepository.save (member);
        return savedUser;
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate(); // 세션 초기화
    }

    @Override
    public User findUser(HttpSession session) {
        return userRepository.findByUserid ((String) session.getAttribute ("userid"));
    }
}
