package com.nju.edu.cn.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: hwang
 * @date: 2023/8/4 22:01
 * @description: 日志测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogTest {

    @Test
    public void testLog(){
        log.info("log info");
        log.debug("log debug");
        log.error("log error");
    }
}
