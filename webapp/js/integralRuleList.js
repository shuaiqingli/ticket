$(function () {

});

function del(id) {
    if (typeof(id) == 'number' && id > 0) {
        layer.confirm('确认删除此积分规则?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/integralRuleDel.do',
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