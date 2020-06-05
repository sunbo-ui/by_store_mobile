package com.mysb.core.service;

import com.mysb.core.dao.ad.ContentCategoryDao;
import com.mysb.core.dao.ad.ContentDao;
import com.mysb.core.dao.good.GoodsDao;
import com.mysb.core.dao.item.ItemDao;
import com.mysb.core.dao.nav.NavCategoryDao;
import com.mysb.core.interfac.HomeFeignClient;
import com.mysb.core.pojo.ad.Content;
import com.mysb.core.pojo.ad.ContentQuery;
import com.mysb.core.pojo.good.Goods;
import com.mysb.core.pojo.good.GoodsQuery;
import com.mysb.core.pojo.nav.NavCategory;
import com.mysb.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
public class HomeServiceImpl implements HomeFeignClient {
    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ContentCategoryDao categoryDao;

    @Autowired
    private NavCategoryDao navCategoryDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private RedisTemplate redisTemplate;
    /*轮播图*/
    public List<Content> findFormRedisByCategoryId(Long categoryId) {
        System.out.println(categoryId);
        if(categoryId != null){
            List<Content> contentList =
                    (List<Content>) redisTemplate.boundHashOps(Constants.BY_CONTENT_LIST_REDIS).
                            get(categoryId);
            //判断redis当中是否有
            if (contentList == null) {
                //没有,查出 放入redis一份
                contentList = findByCategoryId(categoryId);
                redisTemplate.boundHashOps(Constants.BY_CONTENT_LIST_REDIS).put(categoryId, contentList);
            }
            return contentList;
        }
        return null;
    }

    public List<Content> findByCategoryId(Long categoryId) {
       if(categoryId != null){
           ContentQuery query = new ContentQuery();
           ContentQuery.Criteria criteria = query.createCriteria();
           criteria.andCategoryIdEqualTo(categoryId);
           List<Content> list = contentDao.selectByExample(query);
           return list;
       }
       return null;
    }

    public List<NavCategory> findAllNav() {
        return navCategoryDao.selectByExample(null);
    }

    public List<Goods> findFormRedisByFlashSale(Integer param) {
        if(param != null){
            List<Goods> list;
            String flag;
            String key;
            if (param == 0) {
                flag = "flash";
                key=Constants.BY_FLASH_LIST_REDIS;
                list = (List<Goods>) redisTemplate.boundHashOps(key).get(flag);
            } else {
                flag="youLike";
                key=Constants.BY_YOULIKE_LIST_REDIS;
                list = (List<Goods>) redisTemplate.boundHashOps(key).get(flag);;
            }
            //判断redis当中是否有
            if (list == null) {
                //没有,查出 放入redis一份
                list = findFlash(param);
                redisTemplate.boundHashOps(key).put(flag, list);
            }
            return list;
        }
        return null;
    }

    public List<Goods> findFlash(Integer param) {
        if(param != null){
            GoodsQuery query = new GoodsQuery();
            GoodsQuery.Criteria criteria = query.createCriteria();
            if (param == 0) {
                criteria.andIsFlashSaleEqualTo("1");
            } else {
                criteria.andIsYouLikeEqualTo("1");
            }
            return goodsDao.selectByExample(query);
        }
        return null;
    }
}
