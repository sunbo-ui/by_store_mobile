package com.mysb.controller;

import com.alibaba.csp.ahas.shaded.com.alibaba.fastjson.JSON;
import com.mysb.core.interfac.CartFeignClient;
import com.mysb.core.pojo.entry.BuyCartEntity;
import com.mysb.core.pojo.entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartFeignClient cartFeignClient;

    @RequestMapping("/addGoodsToCartList")
    public Result addCartToCartList(@RequestBody BuyCartEntity cartEntity, String username) {
        List<BuyCartEntity> cartList = this.findCartList(username);
        if (cartList == null) {
            //没有购物车,创建一个购物车并保存到redis
            List<BuyCartEntity> list = new ArrayList<>();
            cartEntity.setNum(1);
            list.add(cartEntity);
            cartFeignClient.setCartListFromRedis(JSON.toJSONString(list), username);
        } else {
            //在购物车的基础上添加一个
            List<BuyCartEntity> buyCart = this.IsBuyCart(cartList, cartEntity);
            cartFeignClient.setCartListFromRedis(JSON.toJSONString(buyCart), username);
        }
        return new Result(true, "添加成功");
    }

    ;

    private List<BuyCartEntity> IsBuyCart(List<BuyCartEntity> cartList, BuyCartEntity cartEntity) {
        for (BuyCartEntity entity : cartList) {
            if (entity.getId().equals(cartEntity.getId())) {
                //存在
                entity.setNum(entity.getNum() + 1);
                return cartList;
            }

        }
        //不是一个商品
        cartList.add(cartEntity);
        return cartList;
    }

    @RequestMapping("/findCartList")
    public List<BuyCartEntity> findCartList(String username) {
        //从redis当中获取cartList]
        List<BuyCartEntity> list = cartFeignClient.getCartListFromRedis(username);
        if (list != null) {
            return list;
        }
        return null;
    }

    @RequestMapping("/removeAllCartList")
    public Result removeAllCartList(String username) {
        try {
            cartFeignClient.removeAllCartListFromRedis(username);
            return new Result(true,"清空成功");
        } catch (Exception e) {
            return new Result(false, "清空失败");
        }
    }
    @RequestMapping("/removeCartList")
    public Result removeCartList(String username,Long goodsId) {
        try {
            cartFeignClient.removeCartListFromRedis(username,goodsId);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }
    @RequestMapping("/updateCartList")
    public Result updateCartList(String username,Integer num,Long goodsId) {
        try {
            cartFeignClient.updateCartListFromRedis(username,num,goodsId);
            return new Result(true,"更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }
    @RequestMapping("/singeSelect")
    public Result singeSelect(String username,Long goodsId) {
        try {
            cartFeignClient.singeSelect(username,goodsId);
            return new Result(true,"更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "更新失败");
        }
    }
    @RequestMapping("/allSelect")
    public Result allSelect(String username) {
        try {
            cartFeignClient.allSelect(username);
            return new Result(true,"更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }
}
