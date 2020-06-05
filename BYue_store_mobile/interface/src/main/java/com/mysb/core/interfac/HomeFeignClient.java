package com.mysb.core.interfac;

import com.mysb.core.fallback.HomeFeignClientFallBack;
import com.mysb.core.pojo.ad.Content;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.nav.NavCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-portal",fallbackFactory = HomeFeignClientFallBack.class)
public interface HomeFeignClient {
    /*轮播图*/
    @RequestMapping("/home/findByCategoryId")
    public List<Content> findFormRedisByCategoryId(@RequestParam("categoryId") Long categoryId);

    @RequestMapping("/home/findAllNav")
    List<NavCategory> findAllNav();
    /*猜你喜欢和秒杀  根据param*/
    @RequestMapping("/home/flashSale")
    List<Goods> findFormRedisByFlashSale(@RequestParam("param")Integer param);

}
