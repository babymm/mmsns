layui.use(['element', 'form'], function () {
    var element = layui.element, form = layui.form;
    //添加文章分类
    form.on('submit(createArticleCategory)', function (data) {
        layer.msg(JSON.stringify(data.field));
        $.post("/$individuation/article/classify/create", data.field, function (respData) {
            layer.msg(respData);
        })
        return false;
    });
});