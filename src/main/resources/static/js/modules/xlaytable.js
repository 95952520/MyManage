//layui xtable
layui.define(['layer', 'table', 'form'], function (exports) {

    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    //弹窗
    $(".dialog").on('click', function () {
        var me = this;
        var url = $(this).attr('data-url'),
            width = $(me).attr('data-width') || 800,
            height = $(me).attr('data-height') || 600,
            title = $(me).attr('data-title') || '';
        x_admin_show(title, url, width, height);
    });

    form.verify({
        myCheckDate: function (value) {
            if (value == "") {
                return;
            }
        },
        checkInt: function (value) {
            value = value.trim();
            if ("" == value) {
                return;
            }
            if (!new RegExp("^-?\\d+$").test(value)) {
                return '需要为数字类型';
            }
        }
    });

    //监听table的工具条
    table.on('tool(table)', function (obj) {
        var me = this;
        var url = $(me).attr('data-url'),
            width = $(me).attr('data-width'),
            height = $(me).attr('data-height'),
            id = $(me).attr('data-id') || 'id',
            title = $(me).attr('data-title') || '',
            msg = $(me).attr('data-msg') || '确定?';
        var data = obj.data;
        //删除
        if (obj.event === 'del') {
            layer.confirm('确定删除?', {icon: 3, title: '警告'}, function (index) {
                $.post(url, data, function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {
                            icon: 1,
                            time: 1000
                        });
                        location.reload();
                    } else {
                        layer.msg(result.msg, {
                            icon: 2,
                            time: 10000
                        });
                    }
                });
            });
            //确认框
        } else if (obj.event === 'confirm') {
            layer.confirm(msg, {icon: 3, title: '警告'}, function (index) {
                $.post(url, data, function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {
                            icon: 1,
                            time: 1000
                        });
                        location.reload();
                    } else {
                        layer.msg(result.msg, {
                            icon: 2,
                            time: 10000
                        });
                    }
                });
            });
            //编辑
        } else {
            x_admin_show(title, url + '?' + id + '=' + data[id], width, height);
        }
    });

    exports('xlaytable', {});
});  
