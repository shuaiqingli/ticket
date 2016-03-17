$(function () {
    var tabIndex = parseInt(location.hash.split('#')[1]);
    if (!isNaN(tabIndex) && $('.nav-tabs li').length > tabIndex) {
        $('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
        $('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
    }
});

function chooseStation() {
    var userid = $('input[name="userID"]').val();
    var $iframe = null;
    layer.open({
        type: 2,
        title: '选取站点',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/admin/selectStation.do?userid=' + userid,
        success: function (layero, index) {
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var stationidList = [];
            $iframe.contents().find('.check_single:checked').each(function () {
                stationidList.push($(this).val());
            });
            if (stationidList.length > 0) {
                $.ajax({
                    type: 'POST',
                    url: basePath + '/admin/bindStationListToAdmin.do',
                    data: {userid: userid, stationidList: stationidList.join(',')},
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

function chooseLine() {
    var userid = $('input[name="userID"]').val();
    var $iframe = null;
    layer.open({
        type: 2,
        title: '选取线路',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/user/selectLine.do?userid=' + userid,
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
                    url: basePath + '/admin/bindLineListToAdmin.do',
                    data: {userid: userid, lineidList: lineidList.join(',')},
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

function delStation(userid, stationid) {
    if (typeof(userid) == 'string' && userid.length > 0 && typeof(stationid) == 'string' && stationid.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/unbindStationToAdmin.do',
                data: {userid: userid, stationid: stationid},
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

function delLine(userid, lineid) {
    if (typeof(userid) == 'string' && userid.length > 0 && typeof(lineid) == 'string' && lineid.length > 0) {
        layer.confirm('确认删除此记录?', {offset: '300px'}, function () {
            $.ajax({
                type: 'POST',
                url: basePath + '/admin/unbindLineToAdmin.do',
                data: {userid: userid, lineid: lineid},
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