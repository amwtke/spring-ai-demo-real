package com.spring.ai.springaidemo.test;

import com.spring.ai.springaidemo.service.MyDataService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private MyDataService myDataService;

    @RequestMapping("/test/redis/get")
    public String getFromRedis(@RequestParam("key") String key) {
        return myDataService.getFromRedis(key);
    }

    @RequestMapping("/test/redis/set")
    public String setKeyValueToRedis(@RequestParam("key") String key, @RequestParam("value") String value) {
        myDataService.setKeyToRedis(key, value);
        return "ok";
    }
}
