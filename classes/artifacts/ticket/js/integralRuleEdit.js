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
        integralRuleForm.check(false, $('input[name="begindate"]'));
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
        integralRuleForm.check(false, $('input[name="enddate"]'));
        $('input[name="begindate"]').datetimepicker('setEndDate', ev.date);
    });

    window.integralRuleForm = $('#integralRuleForm').Validform({
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
                location.href = basePath + '/user/integralRuleList.do';
            } else {
                layer.msg('操作失败');
            }
        }
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
        offset: '200px',
        area: ['800px', 'auto'],
        content: basePath + '/user/selectLineForIntegralRule.do?irid=' + id,
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
                    url: basePath + '/user/bindLineListToIntegralRule.do',
                    data: {id: id, lmidList: lineidList.join(',')},
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

function delLine(id, lmid) {
    if (typeof(id) == 'number' && id > 0 && typeof(lmid) == 'number' && lmid > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/unbindLineToIntegralRule.do',
                data: {id: id, lmid: lmid},
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
