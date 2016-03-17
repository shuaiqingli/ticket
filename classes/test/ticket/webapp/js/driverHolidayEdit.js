$(function () {
    $('input[name="startdate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        driverHolidayForm.check(false, $('input[name="startdate"]'));
        $('input[name="enddate"]').datetimepicker('setStartDate', ev.date);
    });
    $('input[name="enddate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        driverHolidayForm.check(false, $('input[name="enddate"]'));
        $('input[name="startdate"]').datetimepicker('setEndDate', ev.date);
    });

    window.driverHolidayForm = $('#driverHolidayForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
            var type = $('select[name="type"]').find('option:selected').val();
            if (type == "1") {
                $('input[name="startdate"]').val('');
                $('input[name="enddate"]').val('');
                var weekday = 0;
                $('input[name="weekday"]').parents('div.row').find('input:checked').each(function () {
                    weekday += parseInt($(this).val());
                });
                if (weekday <= 0) {
                    layer.msg("请至少选中一个星期选项");
                    return false;
                }
                $('input[name="weekday"]').val(weekday);
            } else {
                $('input[name="weekday"]').val('');
            }
        },
        beforeSubmit: function () {
            $("#driverHolidayForm").attr('action', basePath + '/user/driverHolidayAddOrEdit.do');
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/user/driverHolidayList.do?driverid=' + driverid;
            } else {
                layer.msg('操作失败');
            }
        }
    });
});

function selectType() {
    var type = $('select[name="type"]').find('option:selected').val();
    if (type == "1") {
        $('input[name="startdate"]').parents('div.row').eq(0).hide();
        $('input[name="enddate"]').parents('div.row').eq(0).hide();
        $('input[name="weekday"]').parents('div.row').eq(0).show();
    } else if (type == "2") {
        $('input[name="startdate"]').parents('div.row').eq(0).show();
        $('input[name="enddate"]').parents('div.row').eq(0).show();
        $('input[name="weekday"]').parents('div.row').eq(0).hide();
    }
}