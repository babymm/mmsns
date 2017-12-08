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