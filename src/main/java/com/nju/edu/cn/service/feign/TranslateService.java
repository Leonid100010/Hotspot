package com.nju.edu.cn.service.feign;


import com.nju.edu.cn.entity.SingleText;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name="translate", url="http://124.70.100.197:9100")
public interface TranslateService {

    @PostMapping("/translate/single")
    String translateSingle(@RequestBody SingleText request);


}
