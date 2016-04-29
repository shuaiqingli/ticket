$(function () {
    $(".driverList").sortable({
        connectWith: ".driverList",
        stop: sortGroups
    }).disableSelection();
    $(".plateList").sortable({
        connectWith: ".plateList",
        stop: sortGroups
    }).disableSelection();

    rewidth($('.cityA>div').eq(1).find(".cell_mod"), $('.cityA>div').eq(1));
    rewidth($('.cityB>div').eq(1).find(".cell_mod"), $('.cityB>div').eq(1));

    $(window).resize(function(){
        rewidth($('.cityA>div').eq(1).find(".cell_mod"), $('.cityA>div').eq(1));
        rewidth($('.cityB>div').eq(1).find(".cell_mod"), $('.cityB>div').eq(1));
    });

});

function rewidth(itemList,itemGroup){
    var win_width = $('.main_container').width();
    var total_width = (itemList.width()+10)*itemList.length+50;
    if(total_width>win_width){
        itemGroup.css('width', total_width+'px');
    }else{
        itemGroup.css('width', $('.main_tit').width()+'px');
    }
}

function sortGroups() {
    $('.plateList,.driverList').each(function () {
        $(this).find('.cell').each(function (index, element) {
            $(element).find('.index').text(index + 1);
        });
    });
}

function saveOrPreview(type) {
    if (typeof type != 'number' || (type != 1 && type != 2)) return;
    $(".driverList.work").each(function () {
        if ($(this).find('.cell').length == 0) {
            layer.msg("司机列表不能为空");
            return false;
        }
    });
    $(".plateList.work").each(function () {
        if ($(this).find('.cell').length == 0) {
            layer.msg("车牌列表不能为空");
            return false;
        }
    });
    var dataMap = {};
    dataMap.groupid = $(".date").attr("groupid");
    dataMap.date = $(".date").attr("date");
    dataMap.dataMapForCityA = {};
    dataMap.dataMapForCityA.cityID = $(".cityA").attr("cityID");
    dataMap.dataMapForCityA.driveridList = [];
    dataMap.dataMapForCityA.driverid2List = [];
    dataMap.dataMapForCityA.plateidList = [];
    $(".cityA .driverList.work").eq(0).find(".cell").each(function () {
        dataMap.dataMapForCityA.driveridList.push($(this).attr("driverid"));
    });
    $(".cityA .driverList.work").eq(1).find(".cell").each(function () {
        dataMap.dataMapForCityA.driverid2List.push($(this).attr("driverid"));
    });
    $(".cityA .plateList.work").eq(0).find(".cell").each(function () {
        dataMap.dataMapForCityA.plateidList.push($(this).attr("plateid"));
    });

    dataMap.dataMapForCityB = {};
    dataMap.dataMapForCityB.cityID = $(".cityB").attr("cityID");
    dataMap.dataMapForCityB.driveridList = [];
    dataMap.dataMapForCityB.driverid2List = [];
    dataMap.dataMapForCityB.plateidList = [];
    $(".cityB .driverList.work").eq(0).find(".cell").each(function () {
        dataMap.dataMapForCityB.driveridList.push($(this).attr("driverid"));
    });
    $(".cityB .driverList.work").eq(1).find(".cell").each(function () {
        dataMap.dataMapForCityB.driverid2List.push($(this).attr("driverid"));
    });
    $(".cityB .plateList.work").eq(0).find(".cell").each(function () {
        dataMap.dataMapForCityB.plateidList.push($(this).attr("plateid"));
    });
    var url = basePath + '/user/distributeDriverAndPlate.do';
    if (type == 2) {
        url = basePath + '/user/distributeDriverAndPlatePreview.do'
    }
    $.ajax({
        type: 'POST',
        url: url,
        data: {dataMap: JSON.stringify(dataMap)},
        dataType: "json",
        success: function (data) {
            if (type == 1) {
                if (typeof(data) == 'number' && data == 1) {
                    layer.msg("操作成功");
                    location.href = basePath + '/user/shiftManage?lmid='+$('input[name="lineid"]').val()+'&date='+$(".date").attr("date");;
                } else {
                    layer.msg('操作失败');
                }
            } else {
                if (typeof(data.shiftStartList) == 'object' && data.shiftStartList.length > 0) {
                    $('#previewDialog').find('h3').eq(0).text(data.lineForCityA.cityStartName + '/' + data.lineForCityA.lineID);
                    $('#previewDialog').find('h3').eq(1).text(data.lineForCityB.cityStartName + '/' + data.lineForCityB.lineID);
                    var head = [];
                    head.push('<tr>');
                    head.push('<th>班次号</th>');
                    head.push('<th>发车时间</th>');
                    head.push('<th>司机编号</th>');
                    head.push('<th>司机名称</th>');
                    if (data.lineForCityA.driverquantity == 2) {
                        head.push('<th>司机II编号</th>');
                        head.push('<th>司机II名称</th>');
                    }
                    head.push('<th>车牌</th>');
                    head.push('<th>核载人数</th>');
                    head.push('</tr>');
                    $('#previewDialog').find('table thead').text('').append(head.join(''));
                    var bodyForCityA = [];
                    var bodyForCityB = [];
                    for (var i = 0; i < data.shiftStartList.length; i++) {
                        var body = bodyForCityA;
                        if (data.shiftStartList[i].LMID == data.lineForCityB.id) {
                            body = bodyForCityB;
                        }
                        body.push('<tr>');
                        body.push('<td>' + data.shiftStartList[i].ShiftCode + '</td>');
                        body.push('<td>' + data.shiftStartList[i].OriginStartTime + '</td>');
                        body.push('<td>' + data.shiftStartList[i].DriverID + '</td>');
                        body.push('<td>' + data.shiftStartList[i].DriverName + '</td>');
                        if (data.lineForCityA.driverquantity == 2) {
                            body.push('<td>' + data.shiftStartList[i].DriverIDII + '</td>');
                            body.push('<td>' + data.shiftStartList[i].DriverNameII + '</td>');
                        }
                        body.push('<td>' + data.shiftStartList[i].PlateNum + '</td>');
                        body.push('<td>' + data.shiftStartList[i].NuclearLoad + '</td>');
                        body.push('<tr>');
                    }
                    $('#previewDialog').find('table tbody').eq(0).text('').append(bodyForCityA.join(''));
                    $('#previewDialog').find('table tbody').eq(1).text('').append(bodyForCityB.join(''));
                    layer.open({
                        type: 1,
                        title: '班次预览',
                        shadeClose: true,
                        shade: 0.8,
                        offset: '200px',
                        area: ['650px', 'auto'],
                        content: $('#previewDialog'),
                        yes: function () {
                        }
                    });
                } else {
                    layer.msg('操作失败');
                }
            }
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}