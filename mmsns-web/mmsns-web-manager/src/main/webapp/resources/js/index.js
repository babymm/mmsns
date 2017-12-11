layui.use('element', function () {
    var element = layui.element;
});

$(function () {
    $(".layui-nav-tree .layui-nav-item").click(function () {
        var that=this;
        $(".layui-nav-tree .layui-nav-item").removeClass("layui-nav-itemed");
        $(that).addClass("layui-nav-itemed");
    });
});