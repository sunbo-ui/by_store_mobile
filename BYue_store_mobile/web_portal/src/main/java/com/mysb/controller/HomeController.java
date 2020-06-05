package com.mysb.controller;

import com.mysb.core.interfac.HomeFeignClient;
import com.mysb.core.pojo.ad.Content;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.nav.NavCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeFeignClient homeFeignClient;

    @RequestMapping("/findByCategoryId")
    public  List<Content> findFormRedisByCategoryId(Long categoryId){
        return homeFeignClient.findFormRedisByCategoryId(categoryId);
    }

    @RequestMapping("/findAllNav")
    public  List<NavCategory> findAllNav(){
        return homeFeignClient.findAllNav();
    }

    @RequestMapping("/flashSale")
    public List<Goods> findFormRedisByFlashSale(Integer param){
        return homeFeignClient.findFormRedisByFlashSale(param);
    }

}
