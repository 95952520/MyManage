layui.define(['layer', 'upload'],function (exports) {
    var $ = layui.jquery,
        upload = layui.upload;

    upload.render({
        elem: '#uploadImg',
        accept: 'images',
        url: '',
        auto: false,
        choose: function(obj){
            obj.preview(function(index, file, result){
                $('#imgElement').attr('src', result);
                $('#imgDesc').html('<a class="layui-btn layui-btn-xs layui-btn-danger img-delete">删除</a>');

                $('#uploadImg').html('更改图片');
                $('#img').val(result);

                $('#imgDesc').find('.img-delete').on('click', function(){
                    $('#uploadImg').html('上传图片');
                    $('#imgElement').attr('src', "");
                    $('#img').val("");
                    $('#imgDesc').html("");
                });
            });
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
