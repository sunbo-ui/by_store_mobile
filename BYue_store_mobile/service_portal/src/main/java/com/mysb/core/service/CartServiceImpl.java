package com.mysb.core.service;

import com.alibaba.fastjson.JSON;
import com.mysb.core.interfac.CartFeignClient;
import com.mysb.core.pojo.entry.BuyCartEntity;
import com.mysb.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class CartServiceImpl implements CartFeignClient {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<BuyCartEntity> getCartListFromRedis(String username) {
        List<BuyCartEntity> list = (List<BuyCartEntity>)redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(username);
        if(list == null){
            return new ArrayList<>();
        }else{
            return list;
        }
    }

    @Override
    public void setCartListFromRedis(String list, String username) {
        //用户名做key
        List<BuyCartEntity> array = JSON.parseArray(list, BuyCartEntity.class);
        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).put(username, array);
    }

    @Override
    public void removeAllCartListFromRedis(String username) {
        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).delete(username);
    }

    @Override
    public void removeCartListFromRedis(String username, Long goodsId) {
        ArrayList<BuyCartEntity> newList = new ArrayList<>();
        List<BuyCartEntity> list = (List<BuyCartEntity>)redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(username);
       for(int i=0;i<list.size();i++){
            if(list.get(i).getId().equals(goodsId)){
                continue;
            }
           newList.add(list.get(i));
        }
        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).put(username, newList);
    }

    @Override
    public void allSelect(String username) {
        int flag = 1;
        List<BuyCartEntity> list = (List<BuyCartEntity>)redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(username);
        for (BuyCartEntity entity : list) {
           if(entity.getChecked()){
               //判断是否全部为true  是就全部设置为false
               flag = 1;
           }else {
               //有false , 全部设置true
               flag = 0;
               break;
           }
        }

        if(flag == 1){
            for (BuyCartEntity entity : list) {
                entity.setChecked(false);
            }
        }else {
            for (BuyCartEntity entity : list) {
                entity.setChecked(true);
            }
        }
        //重新设置
        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).put(username, list);

    }

    @Override
    public void singeSelect(String username, Long goodsId) {
        List<BuyCartEntity> list = (List<BuyCartEntity>)redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(username);
        for (BuyCartEntity entity : list) {
            if(entity.getId().equals(goodsId)){
                entity.setChecked(!entity.getChecked());
                break;
            }
        }

        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).put(username, list);
    }

    @Override
    public void updateCartListFromRedis(String username, Integer num, Long goodsId) {
        List<BuyCartEntity> list = (List<BuyCartEntity>)redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).get(username);
        for (BuyCartEntity cartEntity : list) {
            if(cartEntity.getId().equals(goodsId)){
                //修改num
                cartEntity.setNum(num);
                break;
            }
        }
        redisTemplate.boundHashOps(Constants.BY_MOBILE_CART_LIST_REDIS).put(username, list);
    }
}
