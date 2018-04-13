//layui xtable
layui.define(['layer', 'table', 'element', 'form','laydate'], function (exports) {

    var $ = layui.jquery,
        element = layui.element,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate;

    //弹窗
    $(".dialog").on('click', function () {
        var me = this;
        var url = $(this).attr('data-url');
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
            if (""==value){
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
                $.post(url,data, function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {
                            icon: 1,
                            time: 1000
                        });
                        layer.close(index);
                        table.reload('table');
                    } else {
                        layer.msg(result.msg, {icon: 2});
                    }
                });
            });
            //确认框
        } else if (obj.event === 'confirm'){
            layer.confirm(msg, {icon: 3, title: '警告'}, function (index) {
                $.post(url,data, function (result) {
                    if (result.code == 0) {
                        layer.msg(result.msg, {
                            icon: 1,
                            time: 1000
                        });
                        layer.close(index);
                        table.reload('table');
                    } else {
                        layer.msg(result.msg, {icon: 2});
                    }
                });
            });
            //编辑
        }else {
            x_admin_show(title, url + '?id=' + data[id], width, height);
        }
    });

    table.on('sort(table)', function (obj) { //注：tool是工具条事件名 lay-filter="对应的值"
        var data = $('.layui-form').serializeObject();
        data = JSON.stringify(data);
        table.reload('table', {
            initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            , where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                pageField: obj.field //排序字段
                , pageOrder: obj.type //排序方式
                , params: data
            }
        });
    });

    //监听单元格编辑
    table.on('edit(table)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        $.post($("#editText").val(), data, function (result) {
            if (result.code == 0) {
                parent.layer.msg(result.msg, {
                    icon: 1,
                    time: 1000
                });
            } else {
                table.reload('table');
                layer.msg(result.msg, {icon: 2});
            }
        });
    });

    //批量删除
    $(".del-all").on('click', function () {
        var url = $(this).attr('data-url');
        var id = $(this).attr('data-id') || "id";
        var checkStatus = table.checkStatus('table');
        var data = checkStatus.data;
        if (data.length == 0) {
            layer.msg("请选择要删除的记录",{icon: 0});
            return;
        }
        var _ids = [];
        $.each(data, function (i, n) {
            _ids.push(n[id]);
        });
        data = JSON.stringify(data);
        layer.confirm('确定删除?', {icon: 3, title: '警告'}, function (index) {
            $.post(url,{jsonObj:data}, function (result) {
                if (result.code == 0) {
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 1000
                    });
                    layer.close(index);
                    table.reload('table');
                } else {
                    layer.msg(result.msg, {icon: 2});
                }
            });
        });
    });

    //条件搜索
    form.on('submit(search)', function (data) {
        var values = data.field,
            fm = data.form;
        table.reload('table', {
            where: values
        });
        return false;
    });

    //更新checkbox里的值
    form.on('switch(checkbox)', function (obj) {
        var me = this;
        var o = new Object();
        o.id = $(me).attr('value');
        o.filed = $(me).attr('name');
        o.flag = obj.elem.checked;
        $.post($(me).attr('data-url'), o, function (result) {
            if (result.code == 0) {
                parent.layer.msg(result.msg, {
                    icon: 1,
                    time: 1000
                });
            } else {
                layer.msg(result.msg, {
                    icon: 2,
                });
            }
        });
    });

    //日期
    laydate.render({
        elem: '#date'
    });

    /**
     * 表单序列化
     * @returns {{}}
     */
    $.fn.serializeObject = function () {
        var o = new Object();
        var a = this.serializeArray();
        $.each(a, function () {
            o[this.name] = this.value;
        });
        return o;
    };


    exports('xtable', {});
});  
