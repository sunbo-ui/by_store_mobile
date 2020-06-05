export const showBack = (callBack) => {
    let docB = document.documentElement || document.body;
    let oldScrollTo,requestFrame ;
    //监听滚动
    document.addEventListener('scroll',()=>{
        showBackFuc()
    },false)

    /*{passive:true}组织默认事件  提供性能  不能与prevent一起使用*/
    //监听触摸
    document.addEventListener('touchstart',()=>{
        showBackFuc()
    },{passive:true})

    document.addEventListener('touchmove',()=>{
        showBackFuc()
    },{passive:true})

    document.addEventListener('touchend',()=>{
        oldScrollTo=docB.scrollTop;
        moveEnd()
    },{passive:true})


    const moveEnd = ()=>{
        //根据系统屏幕的显示刷新率自动的进行异步刷新(函数节流)
        //就不会引起丢帧 卡顿现象
        requestFrame = requestAnimationFrame(()=>{
            if(docB.scrollTop !== oldScrollTo){
                oldScrollTo = docB.scrollTop;
                moveEnd()
            }else{
                //相当于定时器
                cancelAnimationFrame(requestFrame)
            }
            showBackFuc()
        })
    }

    //判断是否到达目标点
    const showBackFuc = () => {
        if(docB.scrollTop >=200){
            callBack(true)
        }else{
            callBack(false)
        }
    }
}