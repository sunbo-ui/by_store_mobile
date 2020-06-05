package com.mysb.core.interfac;

import com.mysb.core.fallback.AddressFeignClientFallBack;
import com.mysb.core.pojo.entry.BuyCartEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-portal",fallbackFactory = AddressFeignClientFallBack.class)
public interface CartFeignClient {
    @RequestMapping("/cart/findCartList")
    List<BuyCartEntity> getCartListFromRedis(@RequestParam("username") String username);

    @RequestMapping("/cart/addGoodsToCartList")
    void setCartListFromRedis(@RequestParam("list")  String list, @RequestParam("username") String username);

    @RequestMapping("/cart/updateCartList")
    void updateCartListFromRedis(@RequestParam("username") String username, @RequestParam("num")Integer num, @RequestParam("goodsId")Long goodsId);

    @RequestMapping("/cart/removeAllCartList")
    void removeAllCartListFromRedis(@RequestParam("username")String username);

    @RequestMapping("/cart/removeCartList")
    void removeCartListFromRedis(@RequestParam("username")String username,@RequestParam("goodsId") Long goodsId);
    @RequestMapping("/allSelect")
    void allSelect(@RequestParam("username")String username);
    @RequestMapping("/singeSelect")
    void singeSelect(@RequestParam("username") String username,@RequestParam("goodsId") Long goodsId);
}
