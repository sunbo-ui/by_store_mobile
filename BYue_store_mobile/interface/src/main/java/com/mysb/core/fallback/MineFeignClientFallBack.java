package com.mysb.core.fallback;

import com.mysb.core.interfac.MineFeignClient;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.log.PayLog;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class MineFeignClientFallBack implements FallbackFactory<MineFeignClient>{
    @Override
    public MineFeignClient create(Throwable throwable) {
        return new MineFeignClient() {

            @Override
            public Customer findUserByUsername(String username) {
                return null;
            }
            @Override
            public PayLog findOrderByUsername(String username) {
                return null;
            }
        };
    }
}
