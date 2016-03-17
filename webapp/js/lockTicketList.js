$(function () {
    initPage();
    $('input[name="rideDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    });
    $('input[name="makeDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    });
});

function initPage() {
    $('.item').mouseover(function () {
        $(this).css({
            'background': '#ccc'
        });
    });
    $('.item').mouseout(function () {
        $(this).css({
            'background': ''
        });
    });
    $('.check_box').click(function () {
        if ($(this).attr('check') == 0) {
            $(this).find('img').show();
            $(this).attr('check', '1');
        } else if ($(this).attr('check') == 1) {
            $(this).find('img').hide();
            $(this).attr('check', '0');
        }
    });
}

function paySaleOrder(obj, id) {
    var checkcodeList = [];
    $(obj).parents('tr.item').find('input:checked').each(function () {
        checkcodeList.push($(this).val());
    });
    if (checkcodeList.length == 0) {
        layer.msg("请至少选中一个验票码");
        return false;
    }
    /*layer.confirm('确认购买共' + checkcodeList.length + '张票?', {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/user/payLockTicket.do',
            data: {id: id, checkcodeList: checkcodeList.join(',')},
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
    });*/
    $('#payDialog').find('.number').text(checkcodeList.length);
    layer.open({
        type: 1,
        title: '确认付款',
        btn: ['付款', '取消'],
        offset: '200px',
        shadeClose: true,
        shade: 0.8,
        area: ['400px', 'auto'],
        content: $('#payDialog'),
        yes: function () {
            var isLock = 0;
            if($('#payDialog').find('input.isLock:checked').length > 0) isLock=1;
            $.ajax({
                type: 'POST',
                url: basePath + '/user/payLockTicket.do',
                data: {id: id, checkcodeList: checkcodeList.join(','), isLock: isLock},
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
        },
        cancel: function(){
        }
    });
}

function cancelSaleOrder(id) {
    /*layer.confirm('确认取消所有座位?', {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/user/cancelLockTicket.do',
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
    });*/
    layer.open({
        type: 1,
        title: '确认取消',
        btn: ['确定', '取消'],
        offset: '200px',
        shadeClose: true,
        shade: 0.8,
        area: ['400px', 'auto'],
        content: $('#cancelDialog'),
        yes: function () {
            var isLock = 0;
            if($('#cancelDialog').find('input.isLock:checked').length > 0) isLock=1;
            $.ajax({
                type: 'POST',
                url: basePath + '/user/cancelLockTicket.do',
                data: {id: id, isLock: isLock},
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
        },
        cancel: function(){
        }
    });
}