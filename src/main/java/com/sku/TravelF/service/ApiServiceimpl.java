package com.sku.TravelF.service;

import com.sku.TravelF.domain.Tour;
//import com.sku.TourList.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ApiServiceimpl implements ApiService{
    //TourRepository tourRepository;
    // 지역 조회 코드, 지역 기반, 공통정보
    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        if(eElement.getElementsByTagName(tag).item(0) == null)
            return "없음";
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes(); // null

        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return "없음";
        return nValue.getNodeValue();
    }

    @Override
    public List<Tour> CallRegion(String number) {
        int page = 1;	// 페이지 초기값
        List<Tour> result = new ArrayList<> ();

        try {
            StringBuilder url = new StringBuilder ("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode");
            url.append ("?" + URLEncoder.encode ("ServiceKey", "UTF-8") + "=AYVQTVFCUQ0Wda6v9brkZWrDRd2GBdfajmkkf0CLCdJVAuU2N0gz2S%2BmQvPNBlItSekbZy4ek%2BI3n7JZ3AIVYA%3D%3D");
            url.append ("&" + URLEncoder.encode ("pageNo", "UTF-8") + "=" + URLEncoder.encode ("1", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("numOfRows", "UTF-8") + "=" + URLEncoder.encode ("10000", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("MobileApp", "UTF-8") + "=AppTest");
            url.append ("&" + URLEncoder.encode ("MobileOS", "UTF-8") + "=ETC");
            url.append ("&" + URLEncoder.encode ("areaCode", "UTF-8") + "=" + URLEncoder.encode (number, "UTF-8"));

            while(true){
                DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = DBFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(url.toString ()); // url 문서를 가지고옴

                // root tag
                //doc.getDocumentElement().normalize();
                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");
                //System.out.println("파싱할 리스트 수 : "+ nList.getLength());  // 파싱할 리스트 수 = 총 item 수

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
                        Tour tour = new Tour();
                        Element eElement = (Element) nNode;
                        //System.out.println(eElement.getTextContent());

                        tour.setAddr1 (getTagValue ("addr1", eElement));
                        tour.setAddr2 (getTagValue ("addr2", eElement));
                        tour.setAreacode (getTagValue ("areacode", eElement));
                        tour.setCat1 (getTagValue ("cat1", eElement));
                        tour.setCat2 (getTagValue ("cat2", eElement));
                        tour.setCat3 (getTagValue ("cat3", eElement));
                        tour.setContentid (Long.parseLong (getTagValue ("contentid", eElement)));
                        tour.setContenttypeid (getTagValue ("contenttypeid", eElement));
                        tour.setCreatedtime (getTagValue ("createdtime", eElement));
                        tour.setFirstimage (getTagValue ("firstimage", eElement));
                        tour.setFirstimage2 (getTagValue ("firstimage2", eElement));
                        tour.setMapx (getTagValue ("mapx", eElement));
                        tour.setMapy (getTagValue ("mapy", eElement));
                        tour.setMlevel (getTagValue ("mlevel", eElement));
                        tour.setModifiedtime (getTagValue ("modifiedtime", eElement));
                        tour.setReadcount (getTagValue ("readcount", eElement));
                        tour.setSigungucode (getTagValue ("sigungucode", eElement));
                        tour.setTitle (getTagValue ("title", eElement));
                        tour.setZipcode (getTagValue ("zipcode", eElement));

                        result.add (tour);
                    }	// if end
                }	// for end

                page += 1;
                System.out.println("page number : "+page);
                if(page > 1){
                    break;
                }
            }	// while end

        } catch (Exception e) {
            e.printStackTrace ();
        }
        return result;
    }

    @Override
    public List<Tour> CallApi(String areaCode, String sigunguCode) {
        int page = 1;	// 페이지 초기값
        List<Tour> result = new ArrayList<> ();
        // 오늘 날짜입력
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //Calendar c1 = Calendar.getInstance();
        //String strToday = sdf.format(c1.getTime());
        //System.out.println("Today=" + strToday);

        try {
            StringBuilder url = new StringBuilder ("http://apis.data.go.kr/B551011/KorService/areaBasedList");
            url.append ("?" + URLEncoder.encode ("ServiceKey", "UTF-8") + "=AYVQTVFCUQ0Wda6v9brkZWrDRd2GBdfajmkkf0CLCdJVAuU2N0gz2S%2BmQvPNBlItSekbZy4ek%2BI3n7JZ3AIVYA%3D%3D");
            url.append ("&" + URLEncoder.encode ("pageNo", "UTF-8") + "=" + URLEncoder.encode ("1", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("numOfRows", "UTF-8") + "=" + URLEncoder.encode ("10000", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("MobileApp", "UTF-8") + "=AppTest");
            url.append ("&" + URLEncoder.encode ("MobileOS", "UTF-8") + "=ETC");
            url.append ("&" + URLEncoder.encode ("arrange", "UTF-8") + "=A");
            url.append ("&" + URLEncoder.encode ("contentTypeId", "UTF-8")); // 12여행지 / 32음식점 / 39호텔
            url.append ("&" + URLEncoder.encode ("areaCode", "UTF-8") + "=" + URLEncoder.encode (areaCode, "UTF-8"));
            url.append ("&" + URLEncoder.encode ("sigunguCode", "UTF-8") + "=" + URLEncoder.encode (sigunguCode, "UTF-8"));
            url.append ("&" + URLEncoder.encode ("listYN", "UTF-8") + "=Y");

            while(true){
                DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = DBFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(url.toString ()); // url 문서를 가지고옴

                // root tag
                //doc.getDocumentElement().normalize();
                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");
                //System.out.println("파싱할 리스트 수 : "+ nList.getLength());  // 파싱할 리스트 수 = 총 item 수

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
                        Tour tour = new Tour();
                        Element eElement = (Element) nNode;
                        //System.out.println(eElement.getTextContent());

                        tour.setAddr1 (getTagValue ("addr1", eElement));
                        tour.setAddr2 (getTagValue ("addr2", eElement));
                        tour.setAreacode (getTagValue ("areacode", eElement));
                        tour.setCat1 (getTagValue ("cat1", eElement));
                        tour.setCat2 (getTagValue ("cat2", eElement));
                        tour.setCat3 (getTagValue ("cat3", eElement));
                        tour.setContentid (Long.parseLong (getTagValue ("contentid", eElement)));
                        tour.setContenttypeid (getTagValue ("contenttypeid", eElement));
                        tour.setCreatedtime (getTagValue ("createdtime", eElement));
                        tour.setFirstimage (getTagValue ("firstimage", eElement));
                        tour.setFirstimage2 (getTagValue ("firstimage2", eElement));
                        tour.setMapx (getTagValue ("mapx", eElement));
                        tour.setMapy (getTagValue ("mapy", eElement));
                        tour.setMlevel (getTagValue ("mlevel", eElement));
                        tour.setModifiedtime (getTagValue ("modifiedtime", eElement));
                        tour.setReadcount (getTagValue ("readcount", eElement));
                        tour.setSigungucode (getTagValue ("sigungucode", eElement));
                        tour.setTitle (getTagValue ("title", eElement));
                        tour.setZipcode (getTagValue ("zipcode", eElement));

                        result.add (tour);
                        //Tour savedTour = tourRepository.save (tour);
                    }	// if end
                }	// for end

                page += 1;
                if(page > 1){
                    break;
                }
            }	// while end

        } catch (Exception e) {
            e.printStackTrace ();
        }
        return result;
    }

    @Override
    public Tour CallDetail(String contentId){
        int page = 1;	// 페이지 초기값
        Tour result = new Tour ();

        try {
            StringBuilder url = new StringBuilder ("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon");
            url.append ("?" + URLEncoder.encode ("ServiceKey", "UTF-8") + "=AYVQTVFCUQ0Wda6v9brkZWrDRd2GBdfajmkkf0CLCdJVAuU2N0gz2S%2BmQvPNBlItSekbZy4ek%2BI3n7JZ3AIVYA%3D%3D");
            url.append ("&" + URLEncoder.encode ("numOfRows", "UTF-8") + "=" + URLEncoder.encode ("10000", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("pageNo", "UTF-8") + "=" + URLEncoder.encode ("1", "UTF-8"));
            url.append ("&" + URLEncoder.encode ("MobileOS", "UTF-8") + "=ETC");
            url.append ("&" + URLEncoder.encode ("MobileApp", "UTF-8") + "=AppTest");
            url.append ("&" + URLEncoder.encode ("contentId", "UTF-8") + "=" + URLEncoder.encode (contentId, "UTF-8"));
            url.append ("&" + URLEncoder.encode ("contentTypeId", "UTF-8")); // 12여행지 / 32음식점 / 39호텔
            url.append ("&" + URLEncoder.encode ("defaultYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("firstImageYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("areacodeYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("catcodeYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("addrinfoYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("mapinfoYN", "UTF-8") + "=Y");
            url.append ("&" + URLEncoder.encode ("overviewYN", "UTF-8") + "=Y");

            while(true){
                DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = DBFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(url.toString ()); // url 문서를 가지고옴

                // root tag
                //doc.getDocumentElement().normalize();
                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("item");
                //System.out.println("파싱할 리스트 수 : "+ nList.getLength());  // 파싱할 리스트 수 = 총 item 수

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
                        Element eElement = (Element) nNode;
                        //System.out.println(eElement.getTextContent());

                        result.setAddr1 (getTagValue ("addr1", eElement));
                        result.setAddr2 (getTagValue ("areacode", eElement));
                        result.setBooktour (getTagValue ("booktour", eElement));
                        result.setCat1 (getTagValue ("cat1", eElement));
                        result.setCat2 (getTagValue ("cat2", eElement));
                        result.setCat3 (getTagValue ("cat3", eElement));
                        result.setContentid (Long.parseLong (getTagValue ("contentid", eElement)));
                        result.setContenttypeid (getTagValue ("contenttypeid", eElement));
                        result.setCreatedtime (getTagValue ("createdtime", eElement));
                        result.setFirstimage (getTagValue ("firstimage", eElement));
                        result.setFirstimage2 (getTagValue ("firstimage2", eElement));
                        result.setHomepage (getTagValue ("homepage", eElement));
                        result.setMapx (getTagValue ("mapx", eElement));
                        result.setMapy (getTagValue ("mapy", eElement));
                        result.setMlevel (getTagValue ("mlevel", eElement));
                        result.setModifiedtime (getTagValue ("modifiedtime", eElement));
                        result.setOverview (getTagValue ("overview", eElement));
                        result.setSigungucode (getTagValue ("sigungucode", eElement));
                        result.setTitle (getTagValue ("title", eElement));
                        result.setZipcode (getTagValue ("zipcode", eElement));
                    }	// if end

                }	// for end

                page += 1;
                if(page > 1){
                    break;
                }
            }	// while end

        } catch (Exception e) {
            e.printStackTrace ();
        }
        return result;
    }
}

