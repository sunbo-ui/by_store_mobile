package com.mysb.core.fallback;

import com.mysb.core.interfac.CategoryFeignClient;
import com.mysb.core.pojo.good.Goods;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryFeignClientFallBack implements FallbackFactory<CategoryFeignClient> {

    @Override
    public CategoryFeignClient create(Throwable e) {
        return new CategoryFeignClient() {
            @Override
            public List<Goods> changeCategory(Integer categoryId) {
                System.out.println(e);
                return null;
            }

        };
    }
}
