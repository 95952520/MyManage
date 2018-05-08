layui.define(['layer', 'upload'],function (exports) {
    var $ = layui.jquery,
        upload = layui.upload;

    // upload.render({
    //     elem: '#uploadImg',
    //     accept:'images',
    //     data: {
    //         uploadData: $('#uploadData').val(),
    //         remark:$('#uploadData').val()
    //     },
    //     auto:false,
    //     bindAction:"#submitButton",
    //     before: function(obj){
    //         var files = obj.pushFile();
    //         obj.preview(function(index, file, result){
    //             $('#imgElement').attr('src', result);
    //             $('#imgDesc').html('<a class="layui-btn layui-btn-xs layui-btn-danger img-delete">删除</a>');
    //
    //             $('#uploadImg').html('更改图片');
    //             $('#img').val(result);
    //
    //             $('#imgDesc').find('.img-delete').on('click', function(){
    //                 $('#uploadImg').html('上传图片');
    //                 $('#imgElement').attr('src', "");
    //                 $('#img').val("");
    //                 $('#imgDesc').html("");
    //             });
    //         });
    //     },

        upload.render({
        elem: '#uploadImg',
        accept:'images',
        data: {
            uploadData: $('#uploadData').val(),
            remark:$('#uploadData').val()
        },
        url: '/uploadFile/uploadImg',
        before: function(obj){

        },
        done: function(res){
            //如果上传失败
            if(res.code != 0){
                $('#imgElement').attr('src', "");
                $('#img').val("");
                layer.msg(res.msg, {
                    icon: 2,
                    time: 5000
                });
            }else{
                $('#imgElement').attr('src', res.data);
                $('#imgDesc').html('<a class="layui-btn layui-btn-xs layui-btn-danger img-delete">删除</a>');

                $('#uploadImg').html('更改图片');
                $('#img').val(res.data);

                $('#imgDesc').find('.img-delete').on('click', function(){
                    $('#uploadImg').html('上传图片');
                    $('#imgElement').attr('src', "");
                    $('#img').val("");
                    $('#imgDesc').html("");
                });
            }
            //上传成功
        },
        error: function(){
            //演示失败状态，并实现重传
            var imgDesc = $('#imgDesc');
            imgDesc.html('<span style="color: #FF5722;">上传失败</span>');
        }
    });

    $('#imgDesc').find('.img-delete').on('click', function(){
        $('#uploadImg').html('上传图片');
        $('#imgElement').attr('src', "");
        $('#img').val("");
        $('#imgDesc').html("");
    });
    
    exports('xupload', {});
});
