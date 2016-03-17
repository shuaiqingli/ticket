$(function () {
    var tabIndex = parseInt(location.hash.split('#')[1]);
    if (!isNaN(tabIndex) && $('.nav-tabs li').length > tabIndex) {
        $('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
        $('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
    }

    $('input[name="begindate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        prohibitForm.check(false, $('input[name="begindate"]'));
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
        prohibitForm.check(false, $('input[name="enddate"]'));
        $('input[name="begindate"]').datetimepicker('setEndDate', ev.date);
    });

    window.prohibitForm = $('#prohibitForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
            var weekdays = 0;
            $('input[name="weekdays"]').parents('div.row').find('input:checked').each(function () {
                weekdays += parseInt($(this).val());
            });
            if (weekdays <= 0) {
                layer.msg("请至少选中一个星期选项");
                return false;
            }
            $('input[name="weekdays"]').val(weekdays);
        },
        beforeSubmit: function () {
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/admin/prohibitList.do?stid=' + $('input[name="stid"]').val();
            } else {
                layer.msg('操作失败');
            }
        }
    });

    $('input[name="begintime"],input[name="endtime"]').timepicker({
        showMeridian : false,
        showInputs : true,
        defaultTime : false,
        format:'H:I',
        minuteStep : 1
    });
});

function chooseLine() {
    var id = $('input[name="id"]').val();
    var $iframe = null;
    layer.open({
        type: 2,
        title: '选取线路',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/user/selectLineForProhibit.do?pid=' + id,
        success: function (layero, index) {
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var lineidList = [];
            $iframe.contents().find('.check_single:checked').each(function () {
                lineidList.push($(this).val());
            });
            if (lineidList.length > 0) {
                $.ajax({
                    type: 'POST',
                    url: basePath + '/admin/bindLineListToProhibit.do',
                    data: {ptid: id, lineidList: lineidList.join(',')},
                    dataType: "json",
                    success: function (data) {
                        if (typeof(data) == 'number' && data == 1) {
                            location.href = location.href.split('#')[0] + '#1';
                            location.reload();
                        } else {
                            layer.msg('操作失败');
                        }
                    },
                    error: function () {
                        layer.msg('操作失败');
                    }
                });
            }
        }
    });
}

function delLine(id) {
    if (typeof(id) == 'string' && id.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/unbindLineToProhibit.do',
                data: {id: id},
                dataType: "json",
                success: function (data) {
                    if (typeof(data) == 'number' && data == 1) {
                        location.href = location.href.split('#')[0] + '#1';
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

function addTime(){
    var id = $('input[name="id"]').val();
    layer.open({
        type: 1,
        title: '新增时间',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['300px', 'auto'],
        content: $('.addTime'),
        yes: function (index) {
            var begintime = $('input[name="begintime"]').val();
            var endtime = $('input[name="endtime"]').val();
            if (begintime.match(/^(([0-1][0-9])|2[0-3]):[0-5][0-9]$/) == null) {
                layer.msg('无效开始时间');
                return false;
            }
            if (endtime.match(/^(([0-1][0-9])|2[0-3]):[0-5][0-9]$/) == null) {
                layer.msg('无效结束时间');
                return false;
            }
            if(begintime >= endtime){
                layer.msg('开始时间必须小于结束时间');
                return false;
            }
            $.ajax({
                type: "post",
                url: basePath + "/admin/bindTimeToProhibit.do",
                data: {ptid: id, begintime: begintime, endtime: endtime},
                dataType: 'json',
                success: function (data) {
                    if (typeof(data) == 'number' && data == 1) {
                        location.href = location.href.split('#')[0] + '#2';
                        location.reload();
                    } else {
                        layer.msg('操作失败');
                    }
                },
                error: function () {
                    layer.msg('操作失败');
                }
            });
        }
    });
}

function delTime(id) {
    if (typeof(id) == 'string' && id.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/unbindTimeToProhibit.do',
                data: {id: id},
                dataType: "json",
                success: function (data) {
                    if (typeof(data) == 'number' && data == 1) {
                        location.href = location.href.split('#')[0] + '#2';
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