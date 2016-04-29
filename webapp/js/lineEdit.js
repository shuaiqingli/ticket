$(function () {
    var tabIndex = parseInt(location.hash.split('#')[1]);
    if (!isNaN(tabIndex) && $('.nav-tabs li').length > tabIndex) {
        $('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
        $('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
    }

    $('.newScheduleRule').bind("click", function () {
        editScheduleRule(basePath+"/user/lineScheduleRule?lineid="+$lineid);
    })

    $('#scheduleRuleList').find("tr").not($('.schedules,.btn-group,a')).click(function () {
        var index = $(this).next().attr("index");
        if (index != '') {
            var cls = '.schedules' + index;
            console.debug(cls);
            $(cls).slideToggle(0);
        }
    });

    $('.newSaleRule').bind("click", function () {
        newSaleRule(basePath+"/user/lineSaleRule?lmid="+lmid);
    })
});

function newSaleRule(url) {
    var $iframe = null;
    var $index = null;
    layer.open({
        type: 2,
        title: '编辑售票规则',
        btn: ['确定', '取消'],
        shadeClose: true,
        move: 0,
        shade: 0.8,
        area: ['980px', '75%'],
        offset: '100px',
        content: url,
        success: function (layero, index) {
            $index = index;
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var e = $iframe.contents();
            var details = e.find('.detail');
            var params = {};
            params.lmid = lmid;
            params.id = e.find('.rule').find('.id').val();
            params.tplname = e.find('.ptname').val();
            params.strid = e.find('.strid').val();
            params.ticketquantity = e.find('.ticketquantity').val()||0;
            params.lockquantity = e.find('.lockquantity').val()||0;
            params.isdefault = e.find('.isdefault').prop('checked')?1:0;
            params.isstart = e.find('.isstart').val()||0;
            params.startseat = e.find('.startseat').val()||0;
            var prefix = "tps";

            if(!e.find('.seatnoExists').prop('checked')){
                params.isstart = 0;
                params.startseat = 0;
            }

            details.each(function(i,v){
                var $v = $(v);
                params[prefix+'['+i+'].lmid'] = lmid;
                params[prefix+'['+i+'].id'] = $v.find('.id').val();
                params[prefix+'['+i+'].ststartname'] = $v.find('.ststartname').val();
                params[prefix+'['+i+'].starrivename'] = $v.find('.starrivename').val();
                params[prefix+'['+i+'].ststartid'] = $v.find('.ststartid').val();
                params[prefix+'['+i+'].starriveid'] = $v.find('.starriveid').val();
                params[prefix+'['+i+'].limitsale'] = $v.find('.quantity').val();
                params[prefix+'['+i+'].limitcoupons'] = $v.find('.couponquantity').val();
                params[prefix+'['+i+'].price'] = $v.find('.price').val();
            });

            console.debug(params);
            //return;
            ajax(params, basePath + "/user/editTripPriceRule", function (data) {
                if (data >= 1) {
                    location.href = location.href.split('#')[0] + '&date=' + new Date() + '#5';
                } else {
                    layer.alert("操作失败！");
                }
            });
            throw "";
        }
    });
}
function editScheduleRule(url) {
    var $iframe = null;
    var $index = null;
    layer.open({
        type: 2,
        title: '编辑排班',
        btn: ['确定', '取消'],
        shadeClose: true,
        move: 0,
        shade: 0.8,
        area: ['900px', '75%'],
        offset: '100px',
        content: url,
        success: function (layero, index) {
            $index = index;
            $iframe = layero.find('iframe');
        },
        yes: function () {
            var e = $iframe.contents();
            var scheduname = e.find("[name=scheduname]").val();
            if (scheduname == '' || scheduname == undefined) {
                layer.msg("排班名称不能为空！")
                throw "";
            }
            var schedules = e.find('.scheduleDetail').not(e.find('.scheduleDetail').first());
            if (schedules.length == 0) {
                layer.msg("排班不能为空！")
                throw "";
            }
            var params = {};
            params.scheduname = scheduname;
            params.lmid = $id;
            params.lineid = $lineid;
            params.id = e.find(".sched_id").val();
            params.isdefault = e.find(".isdefault").prop("checked") ? 1 : 0;
            var prefix = "schedules";
            schedules.each(function (i, v) {
                params[prefix + "[" + i + "].lsuid"] = params.id;
                params[prefix + "[" + i + "].id"] = $(v).find('.id').val();
                params[prefix + "[" + i + "].shiftcode"] = $(v).find('.shiftcode').text().trim();
                params[prefix + "[" + i + "].starttime"] = $(v).find('.starttime').val();
                params[prefix + "[" + i + "].isdel"] = $(v).find('.isdel').val();
            });
            //console.debug(params);return;
            ajax(params, basePath + "/user/editlineschedule.do", function (data) {
                console.debug(data);
                console.debug(data >= 1);
                console.debug(data == "1");
                if (data >= 1) {
                    location.href = location.href.split('#')[0] + '&date=' + new Date() + '#4';
                } else {
                    layer.alert("操作失败！")
                }
            });
            throw "";
        }
    });
}


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
            // console.debug(driveridList );
            // console.debug('**********    ' + type);
            // alert(driveridList.length > 0 && typeof type == 'string')
            type = 2;
            if (driveridList.length > 0) {
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