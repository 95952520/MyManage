/**
 * Created by Eileen.Y.Lai on 2018/4/10.
 */
layui.define(['layer', 'form'],function (exports) {
    var $ = layui.jquery;
    $(".event").on('click', function (data)  {

        var url = $(this).attr('data-url'),
            id = $(this).parent().find(".id").val(),
            name = $(this).parent().find(".name").val(),
            event = $(this).attr('lay-event'),
            width = $(this).attr('data-width'),
            height = $(this).attr('data-height'),
            title = $(this).attr('data-title') || '',
            level = $(this).attr('data-id') || '',
            msg='';
        if(event == 'del'){
            if(level == 'par'){
                msg = '确定要删除该菜单吗？其下子菜单将一并删除!';
            }else{
                msg = '确定要删除该菜单吗？';
            }
            layer.confirm(msg, {icon: 3, title: '警告'}, function (index) {
                $.post(url+"?menuId="+id, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功', {icon: 1});
                        layer.close(index);
                        window.location.reload();
                    } else {
                        layer.msg(result.msg, {icon: 2});
                    }
                });
            });
        }else if(event == 'add'){
            x_admin_show(title, url + '?parentId=' + id + '&name=' + name, width, height);
        }else if(event == 'edit'){
            x_admin_show(title, url + '?menuId=' + id, width, height);
        }

    });

    $('.icons').on('click',function () {
        if($(this).hasClass('fold-icon')){
            $(this).removeClass('fold-icon');
            $(this).addClass('unfold-icon');
            $(this).html('&#xe61a;');
            $(this).parent().parent().parent().siblings('tbody').hide();
        }else if($(this).hasClass('unfold-icon')){
            $(this).removeClass('unfold-icon');
            $(this).addClass('fold-icon');
            $(this).html('&#xe619;');
            $(this).parent().parent().parent().siblings('tbody').show();
        }
    })

    exports('xutils', {});
});
