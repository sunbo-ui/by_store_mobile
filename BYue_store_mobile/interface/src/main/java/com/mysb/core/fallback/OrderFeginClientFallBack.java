package com.mysb.core.fallback;

import com.mysb.core.interfac.OrderFeginClient;
import com.mysb.core.pojo.log.PayLog;
import com.mysb.core.pojo.order.Order;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderFeginClientFallBack implements FallbackFactory<OrderFeginClient> {
    @Override
    public OrderFeginClient create(Throwable throwable) {
        return new OrderFeginClient() {
            @Override
            public void orderSave(Order order) {

            }


        };
    }
}
