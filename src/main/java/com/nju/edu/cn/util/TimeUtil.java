package com.nju.edu.cn.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 比较time1和time2时间先后
     * @param time1
     * @param time2
     * @return 1：time1时间更早; -1: time1时间更晚; 0: 时刻相等
     */
    public static int compare(String time1, String time2){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            if (d1.getTime() > d2.getTime()) {
                //d1更晚
                return -1;
            } else if (d1.getTime() < d2.getTime()) {
                //d1更早
                return 1;
            } else {
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }
}
