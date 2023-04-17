package com.nju.edu.cn.service.feign;

import com.nju.edu.cn.entity.SingleText;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name="sentistrength", url="http://123.60.87.104:8085")
public interface SentiStrengthService {

    @PostMapping("/analyse/trinaryAnalyse")
    String trinaryAnalyse(@RequestBody SingleText text);
}
