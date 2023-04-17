package com.nju.edu.cn.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

public class HotSpotUtil {

    public static final char[] flags = new char[]{'#'};

    /**
     * 格式化待计算待文本，例如除去非法字符，给双引号加转义字符；
     * @param text
     * @return
     */
    public static String format(String text){
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<text.length();i++){
            if(text.charAt(i) == '\"'){
                sb.append('\\');
            }
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
