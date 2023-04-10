package com.nju.edu.cn.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public class HotSpotUtil {

    public static final char[] flags = new char[]{'#'};

    public static String format(String text){
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<text.length();i++){
            if( hasChar(text.charAt(i))) continue;
            sb.append(text.charAt(i));
        }
        return sb.toString();
    }

    private static boolean hasChar(char c){
        for(char ch: flags){
            if(ch == c) return true;
        }
        return false;
    }
}
