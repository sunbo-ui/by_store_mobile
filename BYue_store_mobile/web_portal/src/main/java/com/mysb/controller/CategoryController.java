package com.mysb.controller;

import com.mysb.core.interfac.CategoryFeignClient;
import com.mysb.core.pojo.good.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @RequestMapping("/changeCategory")
    public List<Goods> changeCategory(Integer categoryId){
        return categoryFeignClient.changeCategory(categoryId);
    }
}
