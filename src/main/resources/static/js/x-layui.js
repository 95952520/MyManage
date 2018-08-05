/*弹出层*/

/*
	参数解释：
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
function x_admin_show(title, url, w, h, full) {
    if (title == null || title == '') {
        title = false;
    }
    if (url == null || url == '') {
        url = "404.html";
    }
    if (w == null || w == '') {
        w = 800;
    }
    if (h == null || h == '') {
        h = 800;
    }
    if (full){
        w = document.documentElement.clientWidth-20;
        h = document.documentElement.clientHeight-20;
    }else {
        if (h > document.documentElement.clientHeight-20) {
            h = document.documentElement.clientHeight-20;
        }
        if (w > document.documentElement.clientWidth-20) {
            w = document.documentElement.clientWidth-20;
        }
    }

    layer.open({
        type: 2,
        area: [w + 'px', h + 'px'],
        maxmin: true,
        shade: 0.6,
        title: title,
        content: url
    });
}

/*关闭弹出框口*/
function x_admin_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
