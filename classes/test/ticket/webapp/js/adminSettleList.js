$(function () {

});

function settle(userid, amount) {
    if (typeof(userid) == 'string' && userid.length > 0) {
        layer.confirm('确认结算此记录?', function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/adminSettle.do',
                data: {userid: userid, amount: amount},
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