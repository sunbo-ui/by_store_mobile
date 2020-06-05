var lStroage = window.localStorage;
var sStroage = window.sessionStorage;
export default{
    Local:{
        get:function(key){
            return JSON.parse(lStroage.getItem(key) || null)
        },
        getAndTime:function(key){
            let data = JSON.parse(lStroage.getItem(key));
            if (data !== null) {
                if (data.expirse != null && data.expirse < new Date().getHours()) {
                    lStroage.removeItem(key);
                } else {
                    return data.value;
                }
            }
            return null;
        },
        setAndTime: function (key, value, time) {
            let data = { value: value, expirse: time};
            lStroage.setItem(key, JSON.stringify(data));
        },
        set:function(key,val){
            lStroage.setItem(key,JSON.stringify(val))
        },
        remove:function(key){
            lStroage.removeItem(key)
        },
        clear:function(){
            lStroage.clear()
        },
    },
    Session:{
        get:function(key){
            return JSON.parse(sStroage.getItem(key) || null)
        },
        set:function(key,val){
            sStroage.setItem(key,JSON.stringify(val))
        },
        remove:function(key){
            sStroage.removeItem(key)
        },
        clear:function(){
            sStroage.clear()
        }
    }
}