//layui模块的定义
layui.define(['layer', 'form','laydate'], function (exports) {

    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;



    $(".dayTime").each(function () {
        laydate.render({
            elem: this,
            type: 'datetime',
            max:0,
            format:'yyyy-MM-dd HH:mm:ss',
            value: new Date()
        });
    });

    $(".dayTimeNoValue").each(function () {
        laydate.render({
            elem: this,
            type: 'datetime',
            max:0,
            format:'yyyy-MM-dd HH:mm:ss'
        });
    });

    // 验证
    form.verify({
        eqPwd: function (value) {
            //获取密码
            var pwd = $("#password").val();
            if (pwd != value) {
                return '两次输入的密码不一致';
            }
        },
        checkStr: function (value) {
            var pwd = $("#password").val();
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '不能含有特殊字符';
            }
        },
        checkPwd: function (value) {
            if (value == "") {
                return;
            }
            var pwd = $("#password").val();
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '不能含有特殊字符';
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
        },
        checkPlusInt: function (value) {
            value = value.trim();
            if ("" == value) {
                return;
            }
            if (!new RegExp("\\d+$").test(value)) {
                return '需要为非负整数类型';
            }
        },
        checkDec: function (value) {
            value = value.trim();
            if ("" == value) {
                return;
            }
            var re = /^[0-9]+\.?[0-9]*$/;
            if (!re.test(value)) {
                return '需要为大于0的数(可为小数)';
            }
        },
        file: function (value, item) {
            if (value == '') {
                return "上传文件不能为空";
            }
        },
        checkDayTime: function (value, item) {
            value = value.trim();
            if ("" == value) {
                return;
            }
            if (!new RegExp("^[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]$").test(value)) {
                return '时间格式不正确';
            }
        }
    });

    // 监听提交
    form.on('submit(submit)', function (data) {
        var values = data.field, fm = data.form;
        //获取checkbox选中的值
        var $ch = $("input:checkbox:checked");
        var name = {};
        var chvs = [];
        if ($ch && $ch[0] && !$ch.attr('lay-skin')) {
            name = $ch[0].name;
            $ch.each(function () {
                chvs.push($(this).val());
            });
            values[name] = chvs;
        }
        var index = layer.load(1); // 换了种风格
        $.post($(fm).attr('action'), values, function (result) {
            layer.close(index);
            if (result.code == 0) {
                parent.layer.msg(result.msg, {
                    icon: 1,
                    time: 1000
                });
                if($(fm).attr("data-id") == 'reload'){
                    location.reload();//避免table中图片缓存
                    parent.window.location.reload();//菜单页刷新
                }
                // 刷新父table
                if (parent.layui.table){
                    parent.layui.table.reload('table');
                }
                // 获得frame索引
                var index2 = parent.layer.getFrameIndex(window.name);
                // 关闭当前frame
                parent.layer.close(index2);

            } else {
                layer.msg(result.msg, {
                    icon: 2,
                    time:5000
                });
            }

        });
        return false;
    });

    $("#deleteImg").on('click', function () {
        var id = $(this).attr('data-id'),
            url = $(this).attr('data-url');
        $.post(url, {id:id}, function (result) {
            if (result.code == 0) {
                layer.msg(result.msg, {
                    icon: 1,
                    time: 1000
                });
                window.parent.location.reload();
            } else {
                layer.msg(result.msg, {
                    icon: 2,
                    time: 5000
                });
            }
        });
        return false;
    });

    //监听开关
    form.on('switch(switch)', function (data) {
        layer.tips(data.elem.checked ? '是' : '否', data.othis);
    });

    exports('xform', {});
});  
