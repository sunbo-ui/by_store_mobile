package com.mysb.controller;

import com.mysb.core.interfac.MineFeignClient;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.log.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mine")
public class MineController {
    @Autowired
    private MineFeignClient mineFeignClient;
    @RequestMapping("/findUserByUsername")
    public Customer findUserByUsername(String username){
        return mineFeignClient.findUserByUsername(username);
    }

    @RequestMapping("/findOrderByUsername")
    public PayLog orderSave(String username) {
        return mineFeignClient.findOrderByUsername(username);
    }
}
