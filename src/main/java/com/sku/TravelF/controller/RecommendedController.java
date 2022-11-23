package com.sku.TravelF.controller;

import com.sku.TravelF.domain.Recommended;
import com.sku.TravelF.service.RecommendedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Controller
@RequestMapping("/recommended/*")
@CrossOrigin(origins = "http://ec2-3-34-52-170.ap-northeast-2.compute.amazonaws.com:5000")
@RequiredArgsConstructor
public class RecommendedController {
    private final RecommendedService recommendedService;

    List<String> Seoul = Arrays.asList ("강남구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "강동구", "영등포구", "용산구",
            "은평구", "종로구", "중구", "중랑구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구");
    List<String> Incheon = Arrays.asList ("강화군", "중구", "계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "옹진군");
    List<String> Daejeon = Arrays.asList ("대덕구", "동구", "서구", "유성구", "중구");
    List<String> Daegu = Arrays.asList ("남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구");
    List<String> Gwangju = Arrays.asList ("광산구", "남구", "동구", "북구", "서구");
    List<String> Busan = Arrays.asList ("강서구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구");
    List<String> Ulsan = Arrays.asList ("중구", "남구", "동구", "북구", "울주군");
    List<String> Sejong = Arrays.asList ("세종시");
    List<String> Gyeonggi = Arrays.asList ("가평군", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "고양시", "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시",
            "파주시", "평택시", "포천시", "과천시", "하남시", "화성시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시");
    List<String> Gangwon = Arrays.asList ("강릉시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "화천군", "횡성군", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시");
    List<String> Chungbuk = Arrays.asList ("괴산군", "청주시", "충주시", "증평군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "진천군");
    List<String> Chungnam = Arrays.asList ("공주시", "예산군", "천안시", "청양군", "태안군", "홍성군", "계룡시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시");
    List<String> Gyeongbuk = Arrays.asList ("경산시", "성주군", "안동시", "영덕군", "영양군", "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "경주시", "청도군", "청송군", "칠곡군", "포항시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시");
    List<String> Gyeongnam = Arrays.asList ("거제시", "양산시", "의령군", "진주시", "창녕군", "창원시", "통영시", "하동군", "함안군", "거창군", "함양군", "합천군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군");
    List<String> Jeonbuk = Arrays.asList ("고창군", "임실군", "장수군", "전주시", "정읍시", "진안군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시");
    List<String> Jeonnam = Arrays.asList ("강진군", "보성군", "순천시", "신안군", "여수시", "영광군", "영암군", "완도군", "장성군", "고흥군", "장흥군", "진도군", "함평군", "해남군", "화순군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군");
    List<String> Jeju = Arrays.asList ("서귀포시", "제주시");


    @GetMapping({"recommended", "recommended/"})
    public ModelAndView recommended(@RequestParam(value = "area", defaultValue = "ALL") String area, @RequestParam(value = "sigungu", defaultValue = "ALL") String sigungu,
                                    HttpSession session, ModelAndView mav) {
        if(area.equals ("ALL")){
            mav.addObject("sigunguList", null);
        }
        else{
            if(area.equals ("서울")) {
                System.out.println (Seoul);
                mav.addObject ("sigunguList", Seoul);
            }else if(area.equals ("인천")){
                mav.addObject("sigunguList", Incheon);
            }else if(area.equals ("대전")){
                mav.addObject("sigunguList", Daejeon);
            }else if(area.equals ("대구")){
                mav.addObject("sigunguList", Daegu);
            }else if(area.equals ("광주")){
                mav.addObject("sigunguList", Gwangju);
            }else if(area.equals ("부산")){
                mav.addObject("sigunguList", Busan);
            }else if(area.equals ("울산")){
                mav.addObject("sigunguList", Ulsan);
            }else if(area.equals ("세종")){
                mav.addObject("sigunguList", Sejong);
            }else if(area.equals ("경기")){
                mav.addObject("sigunguList", Gyeonggi);
            }else if(area.equals ("강원")){
                mav.addObject("sigunguList", Gangwon);
            }else if(area.equals ("충북")){
                mav.addObject("sigunguList", Chungbuk);
            }else if(area.equals ("충남")){
                mav.addObject("sigunguList", Chungnam);
            }else if(area.equals ("경북")){
                mav.addObject("sigunguList", Gyeongbuk);
            }else if(area.equals ("경남")){
                mav.addObject("sigunguList", Gyeongnam);
            }else if(area.equals ("전북")){
                mav.addObject("sigunguList", Jeonbuk);
            }else if(area.equals ("전남")){
                mav.addObject("sigunguList", Jeonnam);
            }else if(area.equals ("제주")){
                mav.addObject("sigunguList", Jeju);
            }
            else{
                mav.addObject("sigunguList", null);
            }
        }

        if(!area.equals ("ALL") && !sigungu.equals ("ALL")){
            String area_txt = area + " " + sigungu;
            mav.addObject("dog",recommendedService.RecommendedList("반려견", area_txt));
            mav.addObject("food",recommendedService.RecommendedList("음식", area_txt));
            mav.addObject("family",recommendedService.RecommendedList("가족", area_txt));
            mav.addObject("tour",recommendedService.RecommendedList("관광지", area_txt));
            mav.addObject("water",recommendedService.RecommendedList("계곡", area_txt));
            mav.addObject("date",recommendedService.RecommendedList("데이트", area_txt));
            mav.addObject("culture",recommendedService.RecommendedList("문화시설", area_txt));
            mav.addObject("walk",recommendedService.RecommendedList("산책", area_txt));
            mav.addObject("reports",recommendedService.RecommendedList("레포츠", area_txt));
            mav.addObject("sleep",recommendedService.RecommendedList("숙박", area_txt));
            mav.addObject("photo",recommendedService.RecommendedList("사진명소", area_txt));
        }
        mav.addObject("area", area);
        mav.addObject("sigungu", sigungu);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.setViewName("recommended/recommended");
        return mav;
    }



    @GetMapping({"rec_img", "rec_img/"})
    public ModelAndView rec_img(@RequestParam(value = "id") String id, HttpSession session, ModelAndView mav){
        List<Recommended> tmp = new ArrayList<>();
        String [] li = id.split("/");
        for(String Id : li){
            tmp.add(recommendedService.getRecommended(Long.parseLong(Id)));
        }

        mav.addObject("tmp",tmp);
        mav.addObject("login_message", session.getAttribute ("name"));
        mav.setViewName("recommended/rec_img");
        return mav;
    }

}