package com.nju.edu.cn.service.schedule;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;


@Slf4j
@EnableScheduling
@Configuration
public class CacheSchedule {

    @Resource
    CacheRefresh cacheRefresh;

    /**
     * 每20分钟检测一次缓存
     */
    @Scheduled(fixedRate = 1000 * 60 * 20)
    public void refreshCacheTask(){

        String[] stations = new String[]{
                "baidu", "bilibili", "zhihu", "weibo"
        };
        log.info("定时任务开始");
        for(String station: stations){
            cacheRefresh.refreshStationCache(station);
        }
        log.info("定时任务结束");

    }



}
