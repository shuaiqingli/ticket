$(function () {

});

function del(id) {
    if (typeof(id) == 'number' && id > 0) {
        layer.confirm('确认删除此商品?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/integralProductDel.do',
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

function putoff(id) {
    if (typeof(id) == 'number' && id > 0) {
        layer.confirm('确认下架此商品?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/integralProductPutoff.do',
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

function puton(id) {
    if (typeof(id) == 'number' && id > 0) {
        layer.confirm('确认上架此商品?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/integralProductPuton.do',
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