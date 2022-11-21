package com.sku.TravelF;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
//public class TravelFApplicationTests {
//
////	@Test
////	void contextLoads() {
////	}
//
//    @Test
//    public void
//
//
//}

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TravelFApplicationTests{

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mainPage(){

    }
}