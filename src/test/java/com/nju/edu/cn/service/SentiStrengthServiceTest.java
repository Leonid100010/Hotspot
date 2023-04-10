package com.nju.edu.cn.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SentiStrengthServiceTest {

    SentiStrengthService sentiStrengthService;

    TranslateService translateService;

    @Autowired
    public SentiStrengthServiceTest(SentiStrengthService sentiStrengthService, TranslateService translateService) {
        this.sentiStrengthService = sentiStrengthService;
        this.translateService = translateService;
    }

    @Test
    public void testCalculateTrinary(){
        String text = translateService.chineseToEng("男子一天狂刷7大景点 次日准时上班。");
        System.out.println(
                sentiStrengthService.calculateTrinary(text)
        );
    }
}
