$(function () {

});

function delDriverHoliday(id) {
    if (typeof(id) == 'number') {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/driverHolidayDel.do',
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