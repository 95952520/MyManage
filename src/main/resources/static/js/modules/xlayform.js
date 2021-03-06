//layui模块的定义
layui.define(['layer', 'form'], function (exports) {

    var $ = layui.jquery,
        form = layui.form;
    // 验证
    form.verify({
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
            if (!new RegExp( /^[1-9]\d*$/).test(value)) {
                return '需要为正整数';
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
                window.parent.location.reload();
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

    exports('xlayform', {});
});  
