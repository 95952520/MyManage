layui.define(['layer', 'laydate','upload'],function (exports) {
    var $ = layui.jquery,
        laydate = layui.laydate,
        upload = layui.upload;

    //日期时间选择器
    $(".commonTime").each(function () {
        laydate.render({
            elem: this,
            type: 'datetime',
            formate:'yyyy-MM-dd HH:mm:ss'
        });
    });
    
    upload.render({
        elem: '#uploadImg'
        ,data: {
            uploadData: $('#uploadData').val()
        }
        ,size: 500
        ,url: '/uploadFile/uploadImg'
        ,before: function(obj){

        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                $('#imgElement').attr('src', "");
                $('#img').val("");
                $('#imgDesc').html("<span style='color: #FF5722;'>" + res.msg + "</span>");
                return layer.msg(res.msg);
            }else{
                $('#imgElement').attr('src', res.data.data);
                $('#imgDesc').html('<a class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</a>');

                $('#uploadImg').html('更改图片');
                $('#img').val(res.data.data);

                $('#imgDesc').find('.demo-delete').on('click', function(){
                    $('#uploadImg').html('上传图片');
                    $('#imgElement').attr('src', "");
                    $('#img').val("");
                    $('#imgDesc').html("");
                });
            }
            //上传成功
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var imgDesc = $('#imgDesc');
            imgDesc.html('<span style="color: #FF5722;">上传失败</span>');
        }
    });

    $('#imgDesc').find('.demo-delete').on('click', function(){
        $('#uploadImg').html('上传图片');
        $('#imgElement').attr('src', "");
        $('#img').val("");
        $('#imgDesc').html("");
    });
    
    exports('xupload', {});
});
