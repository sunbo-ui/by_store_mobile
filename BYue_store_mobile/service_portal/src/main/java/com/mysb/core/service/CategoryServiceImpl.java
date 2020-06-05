package com.mysb.core.service;

import com.mysb.core.dao.good.GoodsDao;
import com.mysb.core.dao.item.ItemDao;
import com.mysb.core.interfac.CategoryFeignClient;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.good.GoodsQuery;
import com.mysb.core.pojo.item.Item;
import com.mysb.core.pojo.item.ItemQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
public class CategoryServiceImpl implements CategoryFeignClient {
    @Autowired
    private GoodsDao goodsDao;

    public List<Goods> changeCategory(Integer categoryId){
       if(categoryId != null){
           GoodsQuery query = new GoodsQuery();
           GoodsQuery.Criteria criteria = query.createCriteria();
           criteria.andCategory1IdEqualTo(Long.parseLong(String.valueOf(categoryId)));
           //根据一级分类查出对应item
           return goodsDao.selectByExample(query);
       }
       return null;
    }
}
