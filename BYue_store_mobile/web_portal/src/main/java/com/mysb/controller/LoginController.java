package com.mysb.controller;

import com.mysb.core.interfac.LoginFeignClient;
import com.mysb.core.pojo.entry.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginFeignClient loginFeignClient;
    @RequestMapping("/logout")
    public Response logout(String token){
        System.out.println("Controller:"+token);
        try {
            loginFeignClient.logout(token);
            return new Response().ResponseSucess();
        }catch (Exception e){
            return new Response().ResponseError();
        }
    }
}
