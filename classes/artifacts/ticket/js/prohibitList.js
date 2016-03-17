$(function () {

});

function del(id) {
    if (typeof(id) == 'number' && id > 0) {
        layer.confirm('确认删除此禁售规则?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/prohibitDel.do',
                data: {id: id},
                dataType: "json",
                success: function (data) {
                    if (typeof(data) == 'number' && data == 1) {
                        location.reload();
                    } else {
                        layer.msg('操作失败');
                    }
                },
                error: function () {
                    layer.msg('操作失败');
                }
            });
        }, function () {
        });
    }
}

function apply(id) {
    layer.confirm('确认应用禁售规则到票库?', {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/admin/prohibitApply.do',
            data: {id:id},
            dataType: "json",
            success: function (data) {
                if (typeof(data) == 'number' && data >= 0) {
                    layer.msg(data + '条行程被禁售');
                } else {
                    layer.msg('操作失败');
                }
            },
            error: function () {
                layer.msg('操作失败');
            }
        });
    }, function () {
    });
}