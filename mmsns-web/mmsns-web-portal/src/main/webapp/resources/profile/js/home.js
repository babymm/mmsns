layui.use(['element', 'layer'], function () {
    var element = layui.element, layer = layui.layer;
});
//修改用户头像
function changeUserAvator(individuation) {
    layer.open({
        type: 2,
        title: '选择用户头像',
        maxmin: true,
        scrollbar: false,
        content: ['/profile/'+individuation+'/avator', 'no'],
        area: ['800px', '600px'],
        end:function () {
            window.location.reload();
        }
    });
}