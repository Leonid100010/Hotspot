package com.nju.edu.cn.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TranslateTest {

    TranslateService translateService;

    @Autowired
    public TranslateTest(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Test
    public void testChineseToEng(){
        System.out.println(translateService.chineseToEng("男子一天狂刷7大景点 次日准时上班。"));
    }
}
