package com.mysb.core.interfac;

import com.mysb.core.fallback.MineFeignClientFallBack;
import com.mysb.core.fallback.OrderFeginClientFallBack;
import com.mysb.core.pojo.log.PayLog;
import com.mysb.core.pojo.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-portal",fallbackFactory = OrderFeginClientFallBack.class)
public interface OrderFeginClient {
    @RequestMapping("/order/orderSave")
    void orderSave(@SpringQueryMap Order order);


}
