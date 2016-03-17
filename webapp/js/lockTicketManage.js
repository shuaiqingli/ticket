$(function () {
    initPage();
    refreshStation();
    searchShiftStartData();
    $('input[name="startDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: getAfterDate(14)
    });
});

function initPage() {
    window.dateSlick = $('.single-item').slick({
        draggable: false,
        infinite: false,
        slidesToShow: 7,
        slidesToScroll: 7
    });
    $('.record_item').mouseover(function () {
        $(this).css({
            'background': '#dfdfdf'
        });
    });
    $('.record_item').mouseout(function () {
        $(this).css({
            'background': '#fff'
        });
    });
    $('.sel_opt').mouseover(function () {
        if ($.trim($(this).attr('sel')) == 1) {
            return;
        }
        $(this).css({
            'color': '#ff6861'
        });
    });
    $('.sel_opt').mouseout(function () {
        if ($.trim($(this).attr('sel')) == 1) {
            return;
        }
        $(this).css({
            'color': '#555'
        });
    });
    $('.sel_opt').click(function () {
        $(this).css({
            background: '#ff6861',
            color: '#fff'
        }).attr('sel', '1');
        $(this).parent().find('td').not($(this)).css({
            background: '#fff',
            color: '#555'
        }).attr('sel', '0');
        loadShiftStartData();
    });
    $('.slick-slide>h3').mouseover(function () {
        if ($.trim($(this).attr('sel')) == 1) {
            return;
        }
        $(this).css({
            'color': '#ff6861'
        });
    });
    $('.slick-slide>h3').mouseout(function () {
        if ($.trim($(this).attr('sel')) == 1) {
            return;
        }
        $(this).css({
            'color': '#555'
        });
    });
    $('.slick-slide>h3').click(function () {
        $(this).css({
            background: '#ff6861',
            color: '#fff'
        }).attr('sel', '1');
        $(this).parent().parent().find('h3').not($(this)).css({
            background: '#eee',
            color: '#555'
        }).attr('sel', '0')
        var index = $(this).parent().parent().find('h3').index($(this));
        if (index >= 0 && index < 7) {
            dateSlick.slickGoTo(0);
        } else if (index >= 7 && index < 14) {
            dateSlick.slickGoTo(7);
        } else if (index >= 14) {
            dateSlick.slickGoTo(14);
        }
        $('#timeList').find('td.sel_opt').eq(0).click();
    });
    $('.chair').live('click', function () {
        if ($(this).hasClass('sold_out')) {

        } else if ($(this).hasClass('sel')) {
            $(this).removeClass('sel');
            var nos = $(this).attr('chairNo');
            $('.show_list>div[chairno="' + nos + '"]').remove();
        } else {
            $(this).addClass("sel");
            var no = $(this).attr('chairNo');
            $('.show_list').append($('.chair_items').eq(0).clone().show().css('display', 'inline-block').text(no + '号').attr('chairNo', no));

        }
    });
}

function refreshStation() {
    var startCityID = $('select[name="startCity"] option:selected').val();
    var endCityID = $('select[name="endCity"] option:selected').val();
    if (startCityID == endCityID) {
        layer.msg('出发城市与到达城市不能相同');
        return false;
    }
    $.ajax({
        type: 'POST',
        url: basePath + '/user/getStationByCityId.do',
        data: {startCityID: startCityID, endCityID: endCityID},
        dataType: "json",
        success: function (data) {
            var startStationList = data[0];
            var endStationList = data[1];
            var startStationElementList = ['<option value="">--请选择站点--</option>'];
            var endStationElementList = ['<option value="">--请选择站点--</option>'];
            for (var i = 0; i < startStationList.length; i++) {
                startStationElementList.push('<option value="' + startStationList[i].id + '">' + startStationList[i].cityname + '</option>');
            }
            for (var i = 0; i < endStationList.length; i++) {
                endStationElementList.push('<option value="' + endStationList[i].id + '">' + endStationList[i].cityname + '</option>');
            }
            $('select[name="startStation"]').text('').append(startStationElementList);
            $('select[name="endStation"]').text('').append(endStationElementList);
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}

function searchShiftStartData() {
    var date = $('input[name="startDate"]').val();
    $('#dateList').find('h3[date="' + date + '"]').click();
}

function loadShiftStartData() {
    var citystartid = $('select[name="startCity"] option:selected').val();
    var cityarriveid = $('select[name="endCity"] option:selected').val();
    var ststartid = $('select[name="startStation"] option:selected').val();
    var starriveid = $('select[name="endStation"] option:selected').val();
    var ticketdate = $('#dateList').find('h3[sel="1"]').attr('date');
    var starttime = $('#timeList').find('td[sel="1"]').attr('startTime');
    var arrivetime = $('#timeList').find('td[sel="1"]').attr('endTime');
    if (citystartid == cityarriveid) {
        layer.msg('出发城市与到达城市不能相同');
        return false;
    }
    $.ajax({
        type: 'POST',
        url: basePath + '/user/getTicketLineList.do',
        data: {
            citystartid: citystartid,
            cityarriveid: cityarriveid,
            ststartid: ststartid,
            starriveid: starriveid,
            ticketdate: ticketdate,
            starttime: starttime,
            arrivetime: arrivetime
        },
        dataType: "json",
        success: function (data) {
            $('div.record_list').find('div.record_item:gt(0)').remove();
            var recordItemList = [];
            for (var i = 0; i < data.length; i++) {
                var recordItem = $('div.record_list').find('div.record_item').eq(0).clone(true).css('display', '');
                recordItem.find('.starttime').text(data[i].starttime);
                recordItem.find('.shiftcode').text(data[i].shiftcode);
                recordItem.find('.ststartname').text(data[i].ststartname);
                recordItem.find('.starrivename').text(data[i].starrivename);
                recordItem.find('.balancequantity').text(data[i].balancequantity);
                recordItem.find('.lockquantity').text(data[i].lockquantity);
                recordItem.find('.saledquantity').text(data[i].allquantity - data[i].balancequantity - data[i].lockquantity);
                recordItem.find('.saledquantity').parent('a').attr('href',basePath+'/user/saleorderlist?rideDate='+ticketdate+'&mobile='+data[i].shiftcode);
                recordItem.find('.prepayquantity').text(data[i].lockedseatnolist.length);
                recordItem.find('.ticketprice').text(data[i].ticketprice);
                if(typeof(data[i].showcontent) == 'string' && data[i].showcontent.length > 0){
                    var showcontent = data[i].showcontent;
                    if(data[i].showcontent.length > 25){
                        showcontent = data[i].showcontent.substring(0,25) + '...';
                    }
                    recordItem.find('.showcontent').text('(' + showcontent + ')').attr('title', data[i].showcontent);
                }
                recordItem.find('.buy').attr('onclick', "location.href='" + basePath + "/user/lockTicketDetail.do?id=" + data[i].id + "'");
                recordItem.find('.unfreeze').attr('onclick', "unfreezeDialog("+data[i].id+");");
                recordItem.find('.freeze').attr('onclick', "freezeDialog("+data[i].id+");");
                recordItemList.push(recordItem);
            }
            $('div.record_list').append(recordItemList);
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}

function getAfterDate(num) {
    var date = new Date();
    date.setDate(date.getDate() + num);
    return date;
}

function freezeDialog(id){
    $('#freezeDialog').find('input[name="id"]').val(id);
    $.ajax({
        type: 'POST',
        url: basePath + '/user/shiftDetailForFreezeOrUnfreeze.do',
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if(data.seatList.length > 0){
                $('#freezeDialog').find('.seat_container').show();
                $('#freezeDialog').find('label.quantity').hide();
                var seatElementStr = '';
                for(var i = 0; i <= Math.floor(data.seatList.length/4) + (data.seatList.length%4==0?-1:0);i++){
                    seatElementStr += '<tr>';
                    for(var j = 0; j <= 3; j++ ){
                        if(i*4+j+1 > data.seatList.length){
                            seatElementStr += '<td></td>';
                        }else{
                            seatElementStr += "<td><div chairNo='" + data.seatList[i*4+j].seatno + "' class='chair ";
                            if(data.seatList[i*4+j].issale != 0){
                                seatElementStr += 'sold_out';
                            }
                            seatElementStr += "'>" + data.seatList[i*4+j].seatno + '</div></td>';
                        }
                    }
                    seatElementStr += '</tr>';
                }
                $('#freezeDialog').find('.seat_container table').text('').append(seatElementStr);
                $('#freezeDialog').find('.show_list').text('');
            }else{
                $('#freezeDialog').find('.seat_container').hide();
                $('#freezeDialog').find('label.quantity').show();
            }
            layer.open({
                type: 1,
                title: '锁定票库',
                shadeClose: true,
                offset: '300px',
                shade: 0.8,
                area: ['500px', 'auto'],
                content: $('#freezeDialog'),
                yes: function () {
                }
            });
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}

function unfreezeDialog(id){
    $('#unfreezeDialog').find('input[name="id"]').val(id);
    $.ajax({
        type: 'POST',
        url: basePath + '/user/shiftDetailForFreezeOrUnfreeze.do',
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if(data.seatList.length > 0){
                $('#unfreezeDialog').find('.seat_container').show();
                $('#unfreezeDialog').find('label.quantity').hide();
                var seatElementStr = '';
                for(var i = 0; i <= Math.floor(data.seatList.length/4) + (data.seatList.length%4==0?-1:0);i++){
                    seatElementStr += '<tr>';
                    for(var j = 0; j <= 3; j++ ){
                        if(i*4+j+1 > data.seatList.length){
                            seatElementStr += '<td></td>';
                        }else{
                            seatElementStr += "<td><div chairNo='" + data.seatList[i*4+j].seatno + "' class='chair ";
                            if(data.seatList[i*4+j].issale != 2){
                                seatElementStr += 'sold_out';
                            }
                            seatElementStr += "'>" + data.seatList[i*4+j].seatno + '</div></td>';
                        }
                    }
                    seatElementStr += '</tr>';
                }
                $('#unfreezeDialog').find('.seat_container table').text('').append(seatElementStr);
                $('#unfreezeDialog').find('.show_list').text('');
            }else{
                $('#unfreezeDialog').find('.seat_container').hide();
                $('#unfreezeDialog').find('label.quantity').show();
            }
            layer.open({
                type: 1,
                title: '解锁票库',
                shadeClose: true,
                shade: 0.8,
                offset: '300px',
                area: ['500px', 'auto'],
                content: $('#unfreezeDialog'),
                yes: function () {
                }
            });
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}

function freeze(){
    var id = $('#freezeDialog').find('input[name="id"]').val();
    var quantity = $('#freezeDialog').find('input[name="quantity"]').val();
    var seatnoList = [];
    $('#freezeDialog').find('.show_list').find('.chair_items').each(function () {
        seatnoList.push($(this).attr('chairno'));
    });
    if ($('#freezeDialog').find('.seat_container').is(':visible')) {
        if (seatnoList.length == 0) {
            layer.msg('请选择座位号');
            return false;
        }
        quantity = seatnoList.length;
    }
    $.ajax({
        type: 'POST',
        url: basePath + '/user/freezeTicket.do',
        data: {id: id, seatnoList: seatnoList.join(','), quantity: quantity},
        dataType: "json",
        success: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/user/freezeTicketList.do';
            } else {
                layer.msg('操作失败');
            }
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}

function unfreeze(){
    var id = $('#unfreezeDialog').find('input[name="id"]').val();
    var quantity = $('#unfreezeDialog').find('input[name="quantity"]').val();
    var seatnoList = [];
    $('#unfreezeDialog').find('.show_list').find('.chair_items').each(function () {
        seatnoList.push($(this).attr('chairno'));
    });
    if ($('#unfreezeDialog').find('.seat_container').is(':visible')) {
        if (seatnoList.length == 0) {
            layer.msg('请选择座位号');
            return false;
        }
        quantity = seatnoList.length;
    }
    $.ajax({
        type: 'POST',
        url: basePath + '/user/unfreezeTicket.do',
        data: {id: id, seatnoList: seatnoList.join(','), quantity: quantity},
        dataType: "json",
        success: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/user/freezeTicketList.do';
            } else {
                layer.msg('操作失败');
            }
        },
        error: function () {
            layer.msg('操作失败');
        }
    });
}