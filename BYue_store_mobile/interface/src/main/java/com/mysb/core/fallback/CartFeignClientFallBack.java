package com.mysb.core.fallback;

import com.mysb.core.interfac.CartFeignClient;
import com.mysb.core.pojo.entry.BuyCartEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFeignClientFallBack implements FallbackFactory<CartFeignClient> {
    @Override
    public CartFeignClient create(Throwable throwable) {
        return new CartFeignClient() {
            @Override
            public List<BuyCartEntity> getCartListFromRedis(String username) {
                return null;
            }

            @Override
            public void setCartListFromRedis(String list, String username) {
                System.out.println("error:"+list);
                System.out.println(throwable.getMessage());
            }

            @Override
            public void updateCartListFromRedis(String username, Integer num, Long goodsId) {

            }

            @Override
            public void removeAllCartListFromRedis(String username) {

            }

            @Override
            public void removeCartListFromRedis(String username, Long goodsId) {

            }

            @Override
            public void allSelect(String username) {

            }

            @Override
            public void singeSelect(String username, Long goodsId) {

            }
        };
    }
}
