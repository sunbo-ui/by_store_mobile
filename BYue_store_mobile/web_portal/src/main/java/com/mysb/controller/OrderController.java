package com.mysb.controller;

import com.mysb.core.interfac.OrderFeginClient;
import com.mysb.core.pojo.entry.Result;
import com.mysb.core.pojo.log.PayLog;
import com.mysb.core.pojo.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderFeginClient orderFeginClient;

    @RequestMapping("/orderSave")
    public Result orderSave(@RequestBody Order order) {
        try {
            orderFeginClient.orderSave(order);
            return new Result(true, "订单提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "订单提交失败");
        }
    }


}
