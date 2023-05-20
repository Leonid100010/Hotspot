package com.nju.edu.cn.util;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void testTimeCompare(){
        System.out.println(TimeUtil.compare("2023-05-20 01:56:01", "2023-05-20 05:56:02" ));
    }
}
