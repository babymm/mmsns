function changeFrameHeight(){
    var ifm= document.getElementById("iframepage");
    if(ifm){
        ifm.height=document.documentElement.clientHeight-60-44-10;
    }
}

/**
 * 修改url路径
 * @param url url
 * @param modular 模块
 * @param withUrl 是否附带url路径
 */
function  changeFrameSrc(url,modular,withUrl) {
    var ifm= document.getElementById("iframepage");
    if(ifm){
        ifm.src=url;
    }else{
        window.location.href=url;
    }
    //修改瀏覽器路徑
    var href=top.window.location.href;
    if(href.indexOf("#")>0){
        var splits=href.split("#");
        href=splits[0];
    }
    var locationUrl=url;
    var indexOf=locationUrl.indexOf("/");
    if(indexOf>=0){
        locationUrl=locationUrl.substr(indexOf+1);
    }
    if(withUrl){
        top.window.location.href=href+"#"+modular+"#"+locationUrl;
    }else{
        top.window.location.href=href+"#"+modular;
    }
}
function niceScrollOnload() {
    $("html").niceScroll({
        cursorcolor: "#424242", // 改变滚动条颜色，使用16进制颜色值
        cursoropacitymin: 0, // 当滚动条是隐藏状态时改变透明度, 值范围 1 到 0
        cursoropacitymax: 1, // 当滚动条是显示状态时改变透明度, 值范围 1 到 0
        cursorwidth: "2px", // 滚动条的宽度，单位：便素
        cursorborder: "1px solid #fff", // CSS方式定义滚动条边框
        cursorborderradius: "5px", // 滚动条圆角（像素）
        zindex: "auto" | 100, // 改变滚动条的DIV的z-index值
        scrollspeed: 60, // 滚动速度
        mousescrollstep: 40, // 鼠标滚轮的滚动速度 (像素)
        touchbehavior: false, // 激活拖拽滚动
        hwacceleration: true, // 激活硬件加速
        boxzoom: false, // 激活放大box的内容
    });
}