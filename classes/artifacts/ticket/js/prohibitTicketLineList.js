$(function () {
    //单选|全选
    $('.check_all').click(function () {
        if ($('.check_all').attr('checked') == 'checked') {
            $('.check_single').attr('checked', 'true');
        } else {
            $('.check_single').removeAttr('checked');
        }
    });
    $('.check_single').click(function () {
        if ($(this).attr('checked') != 'checked') {
            $('.check_all').removeAttr('checked');
        }
    });

    $('input[name="beginDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2014-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        $('input[name="endDate"]').datetimepicker('setStartDate', ev.date);
    });
    $('input[name="endDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2014-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        $('input[name="beginDate"]').datetimepicker('setEndDate', ev.date);
    });
    $('input[name="startTime"],input[name="endTime"]').timepicker({
        showMeridian : false,
        showInputs : true,
        defaultTime : false,
        format:'H:I',
        minuteStep : 1
    });
});

function chooseCity(obj) {
    $obj = $(obj).parents('div').eq(0);
    var cityid = $obj.find("select").eq(0).find("option:selected").val();
    if (typeof (cityid) == 'string' && cityid.trim().length > 0) {
        $.ajax({
            type: 'POST',
            url: basePath + '/admin/stationListForProhibit.do',
            data: {cityid: cityid},
            dataType: "json",
            success: function (data) {
                if (data.length != undefined && data.length > 0) {
                    var cityList = data;
                    var optionList = [];
                    optionList.push('<option value="">--请选择站点--</option>');
                    for (var i = 0; i < cityList.length; i++) {
                        optionList.push('<option value="' + cityList[i].id + '">' + cityList[i].cityname + '</option>');
                    }
                    $obj.find('select').eq(1).html(optionList.join(''));
                }
            }
        });
    }else{
        var optionList = [];
        optionList.push('<option value="">--请选择站点--</option>');
        $obj.find('select').eq(1).html(optionList.join(''));
    }
}

function openTicketLineList(obj){
    var ticketLineIDList = [];
    var tips = "确认解禁此行程?";
    if(typeof obj == 'undefined'){
        $('.check_all').parents('table').find('.check_single:checked').each(function () {
            ticketLineIDList.push($(this).val());
        });
        if(ticketLineIDList.length == 0){
            layer.msg("请至少选中一个行程");
            return false;
        }
        tips = "确认解禁共" + ticketLineIDList.length + "个行程?";
    }else{
        ticketLineIDList.push($(obj).parents('tr').find('input:checkbox').val());
    }
    layer.confirm(tips, {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/admin/openTicketLineList.do',
            data: {ticketLineIDList: ticketLineIDList.join(',')},
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
