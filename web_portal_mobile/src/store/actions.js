import {USER_INFO,INIT_SHOP_CART} from "./mutations-type";
import StorageUtil from "../config/StorageUtil";
export default {
    syncUserInfo({commit}, userInfo) {
        commit(USER_INFO, {userInfo})
    },
    reqUserInfo({commit}) {
        let userInfo = StorageUtil.Local.getAndTime('userInfo');
        if (userInfo) {
            commit(USER_INFO,{userInfo})
        } else {
            //服务器
        }
    },

}
