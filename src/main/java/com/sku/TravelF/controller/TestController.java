package com.sku.TravelF.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/test/*")
public class TestController {
    private Connection conn = null; //DB 커넥션 연결 객체
    Statement stmt = null;
    ResultSet rs = null;
    private static final String USERNAME = "diting9813@skuniv.ac.kr";//DBMS접속 시 아이디
    private static final String PASSWORD = "1234";//DBMS접속 시 비밀번호
    private static final String URL = "jdbc:mysql://54.193.207.124:3306/user";//DBMS접속할 db명

    @GetMapping("pytest")
    public double test() {
        return cosineSimilarity(tfidf_matrix(),tfidf_matrix());
    }

    public List<List<String>> getDB(){
        List<List<String>> matrix = new ArrayList<List<String>>();
        try{
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM recommended where area_txt like '%서울%'");
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while(rs.next()){
                List<String>tmp = new ArrayList<String>();
                String hashtag = rs.getString(4);
                if(hashtag.equals(""))
                    continue;
                tmp = List.of(hashtag.split("#"));
                tmp = tmp.subList(1,tmp.toArray().length);
                matrix.add(tmp);
            }
            //System.out.println("matrix"+matrix);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        return matrix;
    }

    public double tf(List<String> list, String word){
        double result = 0;
        for(String targetWord : list)
            if(word.equalsIgnoreCase(targetWord)) result++;
        return result / list.size();
    }

    public double idf(List<List<String>> lists, String word){
        double n =0;
        for(List<String> list : lists){
            for(String targetWord : list){
                if(word.equalsIgnoreCase(targetWord)){
                    n++;
                    break;
                }
            }
        }
        return Math.log(lists.size() / n);
    }

    public double tfidf(List<String> list, List<List<String>> lists, String word){
        return tf(list,word) * idf(lists,word);
    }
    public double[] tfidf_matrix(){
        List<List<String>> data = getDB();
        List<Double> tmp = new ArrayList<Double>();

        for(List<String> list : data){
            for(String word : list){
                tmp.add(tfidf(list,data,word));
            }
        }
        double[] res;
        res = tmp.stream().mapToDouble(Double::doubleValue).toArray();

        return res;
    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normB = 0.0;
        double normA = 0.0;
        List<Double> tmp = new ArrayList<Double>();
        for (int i = 0; i < vectorA.length; i++) {
            for(int j=0; j<vectorB.length; j++){
                dotProduct += vectorA[i] * vectorB[j];
                normA += Math.pow(vectorA[i], 2);
                normB += Math.pow(vectorB[j], 2);
                tmp.add((dotProduct / (Math.sqrt(normA) * Math.sqrt(normB))));
            }
        }
        System.out.println(tmp);
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
