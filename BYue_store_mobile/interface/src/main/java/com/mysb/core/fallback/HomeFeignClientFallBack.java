package com.mysb.core.fallback;

import com.mysb.core.interfac.HomeFeignClient;
import com.mysb.core.pojo.ad.Content;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.nav.NavCategory;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class HomeFeignClientFallBack implements FallbackFactory<HomeFeignClient> {

    @Override
    public HomeFeignClient create(Throwable e) {
        return new HomeFeignClient() {
            @Override
            public List<Content> findFormRedisByCategoryId(Long categoryId) {
                System.out.println(e.getMessage());
                return null;
            }

            @Override
            public List<NavCategory> findAllNav() {
                System.out.println(e.getMessage());
                return null;
            }

            @Override
            public List<Goods> findFormRedisByFlashSale(Integer param) {
                return null;
            }

        };
    }
}
