//动弹点赞
function actionVote(actionId) {
    $.post("/action/vote",{actionId:actionId},function (respData) {
        if(respData.code==200){
            layer.msg(respData.data,{icon:1});
        }else if(respData.code==444){
            layer.msg(respData.data,{icon:5,time:2000},function () {
                window.location.href="/login";
            });
        }else{
            layer.alert(respData.data,{icon:2});
        }
    });
}
//动弹收藏
function actionCollect(actionId) {
    $.post("/action/collect",{actionId:actionId},function (respData) {
        if(respData.code==200){
            layer.msg(respData.data,{icon:1});
        }else if(respData.code==444){
            layer.msg(respData.data,{icon:5,time:2000},function () {
                window.location.href="/login";
            });
        }else{
            layer.confirm(respData.data,{icon:1});
        }
    });
}
//动弹转发
function actionReprint(actionId) {
    layer.open({
        type: 2,
        closeBtn:true,
        shadeClose:true,
        scrollbar:true,
        title: false,
        closeBtn: 1,
        area: ['650px', '400px'],
        skin: 'layui-layer-nobg',
        shadeClose: false,
        content:['/action/reprint/'+actionId, 'no']
    });
}