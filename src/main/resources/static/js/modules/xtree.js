layui.define(['layer', 'form'], function (exports) {
    var $ = layui.jquery,
        form = layui.form,
        treeDom = {},
        treeIds = '',
        paramId = $("#paramId").val(),
        treeUrl = $("#treeUrl").val();
    if (paramId) {
        treeUrl += '?paramId=' + paramId
    };
    var myTree = new layuiXtree({
        elem: 'myTree'           //放xtree的容器（必填）
        , form: form                       //layui form对象 （必填）
        , data: treeUrl    //服务端地址（必填）
        , isopen: true                     //初次加载时全部展开，默认true （选填）
        , color: "#000"                    //图标颜色 （选填）
        , icon: {                          //图标样式 （选填）
            open: ""               //节点打开的图标
            , close: ""            //节点关闭的图标
            , end: ""              //末尾节点的图标
        }
    });

    form.on('submit(submit)', function (data) {
        treeDom = myTree.GetAllChecked();
        var action = $(data.form).attr('action');
        for (var index in treeDom) {
            var i = treeDom[index];
            treeIds += i.value + ',';
        }
        treeIds = treeIds.substring(0, treeIds.length - 1);
        $.post(action, {ids: treeIds, id: paramId}, function (result) {
            if (result.code == 0) {
                parent.layer.msg(result.msg, {
                    icon: 1,
                    time: 1000
                });
            } else {
                parent.layer.msg(result.msg, {icon: 2});
            }
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        });
        return false;
    });

    exports('xtree', {});
});
