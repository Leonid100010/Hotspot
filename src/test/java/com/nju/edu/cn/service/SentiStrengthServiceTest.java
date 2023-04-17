package com.nju.edu.cn.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SentiStrengthServiceTest {

    SentiStrengthServiceEnter sentiStrengthService;

    Translate translateService;

    @Autowired
    public SentiStrengthServiceTest(SentiStrengthServiceEnter sentiStrengthService, Translate translateService) {
        this.sentiStrengthService = sentiStrengthService;
        this.translateService = translateService;
    }

    @Test
    void testCalculateTrinary(){
        String text = translateService.chineseToEng("男子一天狂刷7大景点 次日准时上班。");
        System.out.println(text);
        System.out.println(
                sentiStrengthService.calculateTrinary(text)
        );
    }
}
