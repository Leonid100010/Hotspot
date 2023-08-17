package com.nju.edu.cn.common.enums;

import com.nju.edu.cn.common.enums.base.Titleable;
import org.apache.commons.lang.StringUtils;

public enum StationEnum implements Titleable {

    BAIDU("百度", "baidu" , "https://api.vvhan.com/api/hotlist?type=baiduRD"),

    ZHIHU("知乎", "zhihu", "https://api.vvhan.com/api/hotlist?type=zhihuHot"),

    WEIBO("微博", "weibo", "https://api.vvhan.com/api/hotlist?type=wbHot"),

    BILIBILI("哔哩哔哩", "bilibili", "https://api.vvhan.com/api/hotlist?type=bili")

    ;

    private String title;

    private String code;

    private String url;

    StationEnum(String title, String code, String url) {
        this.title = title;
        this.code = code;
        this.url = url;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public static StationEnum of(String station){
        if(StringUtils.isBlank(station)){
            return null;
        }
        for (StationEnum stationEnum: StationEnum.values()){
            if(StringUtils.equals(stationEnum.code, station)){
                return stationEnum;
            }
        }
        return null;
    }
}
