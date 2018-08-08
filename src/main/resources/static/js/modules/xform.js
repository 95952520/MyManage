//layui模块的定义
layui.define(['layer', 'form','laydate'], function (exports) {

    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;



    $(".dayTime").each(function () {
        laydate.render({
            elem: this,
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss',
            value: new Date()
        });
    });

    $(".dayTimeNoValue").each(function () {
        laydate.render({
            elem: this,
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
    });

    $(".HHmmssTime").each(function () {
        laydate.render({
            elem: this,
            type: 'time'
        });
    });

    $(".yyyyMMddTime").each(function () {
        laydate.render({
            elem: this
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
        },
        requireCheck: function (value, item) {
            var childrenList = item.children;
            for(var i=0;i<childrenList.length;i++){
                if (childrenList[i].tagName !== 'INPUT'){
                    continue;
                }
                var o = childrenList[i];
                if (childrenList[i].checked){
                    return;
                }
            }
            return '多选框至少选择一个选项'
        }
    });

    // 监听提交
    form.on('submit(submit)', function (data) {
        let values = data.field, fm = data.form;
        //获取checkbox选中的值
        let $ch = $("input:checkbox:checked");
        if ($ch) {//复选框
            let namesObj = [];
            let namesList = [];
            for (let i = 0; i < $ch.length; i++) {
                if ($ch[i].hasAttribute("lay-skin")) {
                    continue;
                }
                let nameIndex = namesList.indexOf($ch[i].name);
                if (nameIndex === -1) {
                    let boxObj = new Object();
                    boxObj.key = $ch[i].name;
                    boxObj.value = [];
                    boxObj.value.push($ch[i].value);
                    namesObj.push(boxObj);
                    namesList.push($ch[i].name);
                } else {
                    let boxObj = namesObj[nameIndex];
                    boxObj.value.push($ch[i].value);
                }
            }
            for (let i = 0; i < namesObj.length; i++) {
                values[namesObj[i].key] = namesObj[i].value;
            }
        }
        var confirmMsg = $("#confirmMsg").val();
        if (confirmMsg) {
            layer.confirm(confirmMsg, {icon: 3, title: '警告'}, function (index) {
                let index = layer.load(1); // 换了种风格
                $.post($(fm).attr('action'), values, function (result) {
                    layer.close(index);
                    if (result.code == 0) {
                        parent.layer.msg(result.msg, {
                            icon: 1,
                            time: 1000
                        });
                        if ($(fm).attr("data-id") == 'reload') {
                            parent.window.location.reload();
                        }
                        // 刷新父table
                        if (parent.layui.table) {
                            parent.layui.table.reload('table');
                        }
                        // 获得frame索引
                        var index2 = parent.layer.getFrameIndex(window.name);
                        // 关闭当前frame
                        parent.layer.close(index2);

                    } else {
                        layer.msg(result.msg, {
                            icon: 2,
                        });
                    }
                });
            });
        } else {
            let index = layer.load(1); // 换了种风格
            $.post($(fm).attr('action'), values, function (result) {
                layer.close(index);
                if (result.code == 0) {
                    parent.layer.msg(result.msg, {
                        icon: 1,
                        time: 1000
                    });
                    if ($(fm).attr("data-id") == 'reload') {
                        parent.window.location.reload();
                    }
                    // 刷新父table
                    if (parent.layui.table) {
                        parent.layui.table.reload('table');
                    }
                    // 获得frame索引
                    var index2 = parent.layer.getFrameIndex(window.name);
                    // 关闭当前frame
                    parent.layer.close(index2);

                } else {
                    layer.msg(result.msg, {
                        icon: 2,
                    });
                }
            });
        }
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
