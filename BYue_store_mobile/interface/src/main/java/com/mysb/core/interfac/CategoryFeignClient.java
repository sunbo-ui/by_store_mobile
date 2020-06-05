package com.mysb.core.interfac;

import com.mysb.core.fallback.CategoryFeignClientFallBack;
import com.mysb.core.pojo.good.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-portal",fallbackFactory = CategoryFeignClientFallBack.class)
public interface CategoryFeignClient {

    @RequestMapping("/category/changeCategory")
    List<Goods> changeCategory(@RequestParam("categoryId") Integer categoryId);

}