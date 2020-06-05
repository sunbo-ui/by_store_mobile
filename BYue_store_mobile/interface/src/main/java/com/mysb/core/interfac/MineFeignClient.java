package com.mysb.core.interfac;

import com.mysb.core.fallback.LoginFeignClientFallBack;
import com.mysb.core.fallback.MineFeignClientFallBack;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.log.PayLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-portal",fallbackFactory = MineFeignClientFallBack.class)
public interface MineFeignClient {
    @RequestMapping("/mine/findUserByUserInfo")
    public Customer findUserByUsername(@RequestParam String username);
    @RequestMapping("/findOrderByUsername")
    PayLog findOrderByUsername(@RequestParam("username") String username);
}
