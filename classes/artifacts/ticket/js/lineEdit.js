$(function () {
    var tabIndex = parseInt(location.hash.split('#')[1]);
    if (!isNaN(tabIndex) && $('.nav-tabs li').length > tabIndex) {
        $('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
        $('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
    }
});

function chooseDriver() {
    var groupid = $('input[name="groupid"]').val();
    var $iframe = null;
    layer.open({
        type: 2,
        title: '选取司机',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/user/selectDriver.do?type=1&groupid=' + groupid,
        success: function (layero, index) {
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var driveridList = [];
            var type = $iframe.contents().find('select[name="type"] option:checked').val();
            $iframe.contents().find('.check_single:checked').each(function () {
                driveridList.push($(this).val());
            });
            if (driveridList.length > 0 && typeof type == 'string') {
                $.ajax({
                    type: 'POST',
                    url: basePath + '/user/bindDriverListToLine.do',
                    data: {groupid: groupid, driveridList: driveridList.join(','), type: type},
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

function choosePlate() {
    var $iframe = null;
    layer.open({
        type: 2,
        title: '选取车牌',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/user/selectPlate.do',
        success: function (layero, index) {
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var plateidList = [];
            $iframe.contents().find('.check_single:checked').each(function () {
                plateidList.push($(this).val());
            });
            if (plateidList.length > 0) {
                var groupid = $('input[name="groupid"]').val();
                $.ajax({
                    type: 'POST',
                    url: basePath + '/user/bindPlateListToLine.do',
                    data: {groupid: groupid, plateidList: plateidList.join(',')},
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
            }
        }
    });
}

function delDriver(groupid, driverid) {
    if (typeof(groupid) == 'string' && groupid.length > 0 && typeof(driverid) == 'string' && driverid.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/unbindDriverToLine.do',
                data: {groupid: groupid, driverid: driverid},
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

function delPlate(groupid, plateid) {
    if (typeof(groupid) == 'string' && groupid.length > 0 && typeof(plateid) == 'string' && plateid.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/user/unbindPlateToLine.do',
                data: {groupid: groupid, plateid: plateid},
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

function editDriver(driverid) {
    if (typeof(driverid) == 'string' && driverid.length > 0) {
        $('#driverEditDialog').find('select').removeAttr("disabled");
        $('#driverEditDialog').find('input[name="driverid"]').val(driverid);
        var groupid = $('input[name="groupid"]').val();
        $.ajax({
            type: 'POST',
            url: basePath + '/user/plateListForLine.do',
            data: {groupid: groupid},
            dataType: "json",
            success: function (data) {
                var $selectForPlate = $('#driverEditDialog').find('select');
                $selectForPlate.text('');
                $selectForPlate.append('<option value="">不指定车牌</option>');
                for (var i = 0; i < data.length; i++) {
                    $selectForPlate.append('<option value="' + data[i].id + '">' + data[i].platenum + '</option>');
                }
                layer.open({
                    type: 1,
                    title: '指定车牌',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['500px', 'auto'],
                    content: $('#driverEditDialog'),
                    yes: function () {
                    }
                });
            },
            error: function () {
                layer.msg('操作失败');
            }
        });
    }
}

function bindPlateToDriver() {
    var groupid = $('input[name="groupid"]').val();
    var driverid = $('#driverEditDialog').find('input[name="driverid"]').val();
    var plateid = $('#driverEditDialog').find('select option:checked').val();
    $.ajax({
        type: 'POST',
        url: basePath + '/user/bindPlateToLineDriver.do',
        data: {groupid: groupid, driverid: driverid, plateid: plateid},
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