import {
    REMOVE_ALL_CART,ADD_GOODS,
    INIT_SHOP_CART,
    REDUCE_CART,
    SELECT_CHECKED_CART,
    SELECT_CHECKED_ALL_CART,
    USER_INFO,
    DELETE_USER_INFO,
    } from "./mutations-type";
import StorageUtil from "../config/StorageUtil";
import Vue from "vue";
export default {

    //往购物车中添加数据   这样就不会涉及拷贝问题
    [ADD_GOODS](state, {goodsId, goodsName, smallImage, goodsPrice}) {
        let shopCart = state.shopCart;//隔离两个对象  防止修改的是同一个对象
        //判断商品是否存在
        if (shopCart[goodsId]) {
            //存在
            shopCart[goodsId]['num']++;
        } else {
            shopCart[goodsId] = {
                'num': 1,
                'id': goodsId,
                'name': goodsName,
                'small_price': smallImage,
                'price': goodsPrice,
                "checked": true,
            }
        }
        //产生新对象
        state.shopCart = {...shopCart};
        //存入本地
        StorageUtil.Local.set("shopCart", state.shopCart);
    },
    //页面初始化,获取购物车的数据
    [INIT_SHOP_CART](state) {
        let initCart  = StorageUtil.Local.get("shopCart");
        if(initCart){
            state.shopCart = initCart;
        }
    },
    //删除购物车
    [REDUCE_CART](state,{goodsId}){
        let shopCart = state.shopCart;
        let goods = shopCart[goodsId];
        if(goods){
            if(goods['num'] >= 0){
                goods['num']--;
                if(goods['num'] === -1){
                    delete shopCart[goodsId]
                    console.log(shopCart)
                }
            }else{
                goods = null;
            }
            //同步数据
            state.shopCart = {...shopCart};
            StorageUtil.Local.set("shopCart", state.shopCart);
        }
    },
    [SELECT_CHECKED_CART](state,{goodsId}){
        let shopCart = state.shopCart;
        let goods = shopCart[goodsId];
        if(goods){
            //vant 的checkbox 已经帮你实现!goods.checked 只是没有同步到state
            if(goods.checked){
                Vue.set(goods,"checked",true);
            }
        }
        //同步数据
        state.shopCart = {...shopCart};
        StorageUtil.Local.set("shopCart", state.shopCart);
    },
    [SELECT_CHECKED_ALL_CART](state){
        let shopCart = state.shopCart;
        //判断shop是不是全部选中
        if(Object.values(shopCart).every(item => item.checked)){
            //全部选中
            Object.values(shopCart).forEach(goods =>{
                goods.checked = false;
            })
        }else{
            Object.values(shopCart).forEach(goods =>{
                goods.checked = true;
            })
        }
        //同步数据
        state.shopCart = {...shopCart};
        StorageUtil.Local.set("shopCart", state.shopCart);
    },

    [REMOVE_ALL_CART](state){
        state.shopCart = null;
        //同步数据
        state.shopCart = {...state.shopCart};
        StorageUtil.Local.set("shopCart", state.shopCart);
    },

    [USER_INFO](state,{userInfo}){
        state.userInfo = userInfo;
        let date = new Date().getHours();
        StorageUtil.Local.setAndTime("userInfo", state.userInfo,date+2);
    },

    [DELETE_USER_INFO](state){
        //同步数据
        StorageUtil.Local.remove("userInfo");
    }
}