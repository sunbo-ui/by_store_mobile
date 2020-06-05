package com.mysb.core.interfac;

import com.mysb.core.fallback.HomeFeignClientFallBack;
import com.mysb.core.fallback.LoginFeignClientFallBack;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.entry.Response;
import com.mysb.core.pojo.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "service-portal",fallbackFactory = LoginFeignClientFallBack.class)
public interface LoginFeignClient {
    @RequestMapping("/portal/login")
    public Customer findUserByUsername(@RequestParam("username")String username);

    @RequestMapping("/portal/saveToken")
    void saveToken(@RequestParam("token")String token,@RequestParam("name") String name);

    @RequestMapping("/login/logout")
    void logout(@RequestParam("token") String token);

}
