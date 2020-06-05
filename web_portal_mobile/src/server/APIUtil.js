let serverBase='http://localhost:9010';
export default {
    BASE_SERVER_URL: serverBase,
    CONTENT_FINDBYCATEGORYID_URL:serverBase + "/home/findByCategoryId",//获取轮播图
    LOGIN_URL:serverBase + "/login",//获取轮播图
    LOGGOUT_URL:serverBase + "/login/logout",//获取轮播图
    HOME_NAV_FINDALL_URL:serverBase + "/home/findAllNav",//获取nav
    HOME_FLASHSALE_URL:serverBase + "/home/flashSale",//获取秒杀 0 或者猜你喜欢  1
    CHANGE_CATEGORY_URL:serverBase+"/category/changeCategory",//根据categoryId查询分类
    ADDRESSS_LOAD_URL:serverBase+"/address/findAddressByUserId",//根据用户名查询收货地址
    ADDRESS_ADD_URL:serverBase+"/address/saveAddress", //保存地址
    ADDRESS_FINDONE_URL:serverBase+"/address/findOneAddress", //查询一个地址
    ADDRESS_UPDATE_URL:serverBase+"/address/editAddress", //更新地址
    ADDRESS_REMOVE_URL:serverBase+"/address/removeAddress", //删除地址
    ADDRESS_UPDATEDEFAULT_URL:serverBase+"/address/updateDefault", //删除地址

    MINE_USERINFO_URL:serverBase+"/mine/findUserByUsername",//查询用户信息
    MINE_ORDER_URL:serverBase+"/mine/findOrderByUsername",//查询用户信息
    CART_ADDGOODSTOCARTLIST_URL:serverBase+"/cart/addGoodsToCartList",//添加购物车
    CART_REMOVEALL_URL:serverBase+"/cart/removeAllCartList",//删除全部购物车
    CART_REMOVE_URL:serverBase+"/cart/removeCartList",//删除购物车
    CART_UPDATENUM_URL:serverBase+"/cart/updateCartList",//更新购物车
    CART_FINDCARTLIST_URL:serverBase+"/cart/findCartList",//查询购物车
    CART_SELECTSINGE_URL:serverBase+"/cart/singeSelect",//更新选中
    CART_SELECTALL_URL:serverBase+"/cart/allSelect",//更新选中

    ORDER_ADD_URL:serverBase+"/order/orderSave",//添加订单
}