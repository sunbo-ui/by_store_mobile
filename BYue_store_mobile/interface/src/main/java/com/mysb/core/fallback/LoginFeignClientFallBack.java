package com.mysb.core.fallback;

import com.mysb.core.interfac.HomeFeignClient;
import com.mysb.core.interfac.LoginFeignClient;
import com.mysb.core.pojo.ad.Content;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.entry.Response;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.nav.NavCategory;
import com.mysb.core.pojo.user.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoginFeignClientFallBack implements FallbackFactory<LoginFeignClient> {

    @Override
    public LoginFeignClient create(Throwable e) {
        return new LoginFeignClient() {
            @Override
            public Customer findUserByUsername(String username) {
                return null;
            }

            @Override
            public void saveToken(String uuid, String name) {

            }

            @Override
            public void logout(String token){
                ;
            }

        };
    }
}
