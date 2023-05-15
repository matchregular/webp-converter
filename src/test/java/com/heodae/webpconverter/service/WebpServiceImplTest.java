package com.heodae.webpconverter.service;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WebpServiceImplTest {

    @Test
    @DisplayName("file ext test")
    void test(){
        String fileName = "test.jpg".toLowerCase(Locale.ROOT);
        String[] imageExt = {"jpg", "jpeg", "png"};

        if(FilenameUtils.isExtension(fileName, imageExt)){
            System.out.println("if");
        }else{
            System.out.println("else");
        }
    }

    @Test
    @DisplayName("put 테스트")
    void test2(){
        Map<String, Integer> result = new HashMap<>();

        result.put("key", 1);
        System.out.println(result);
        result.put("key", result.get("key")+1);
        System.out.println(result);
        result.putIfAbsent("key2", 3);
        System.out.println(result);
        result.computeIfPresent("key", (s, integer) -> s.equals("key22") ? 4 : 5);
        System.out.println(result);

        List<Tony> orderList = Arrays.asList(
                new Tony("o1", "p1"),
                new Tony("o1", "p2"),
                new Tony("o1", "p3"),
                new Tony("o2", "p1"),
                new Tony("o2", "p2"),
                new Tony("o3", "p1"),
                new Tony("o3", "p2"),
                new Tony("o4", "p1")

        );
        Map<String, List<Tony>> listMap = new HashMap<>();

        for (Tony tony : orderList) {
//            listMap.computeIfAbsent(orderCd, val->new ArrayList<>()).add(2);
            listMap.computeIfAbsent(tony.orderCd, v->new ArrayList<>()).add(new Tony(tony.orderCd, tony.productCd));
        }

        System.out.println(listMap);

    }

}

class Tony{
    String orderCd;
    String productCd;

    public Tony(String orderCd, String productCd){
        this.orderCd = orderCd;
        this.productCd = productCd;
    }

    @Override
    public String toString(){
        return this.orderCd + ":" + this.productCd;
    }

}