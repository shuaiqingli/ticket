window.weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
var currdate = new Date(wx_date.formatDate(new Date(), '/'));
window.isLastPage = false;
$(function () {
    window.ignore_scroll = false;
    window.begin_city = JSON.parse(Client.get('begin_city'));
    window.end_city = JSON.parse(Client.get('end_city'));
    window.begin_station = JSON.parse(Client.get('begin_station'));
    window.end_station = JSON.parse(Client.get('end_station'));
    $.cookie('coupon', null);
    $.cookie('link', null);
    $.cookie('ticket_num', null);
    $.cookie('cnum', null);
    $.cookie('pnum', null);
    $.cookie('stid', null, {path: '/'});
    $.cookie('not_use_coupon', 'false');
    if (isempty(Client.get('start_date'))) {
        Client.set('start_date', wx_date.formatDate(new Date()));
    }
    loadFilterData();
    ticketlist(1, 10, Client.get('start_date'));
    $('title').text(begin_city.cityname + '-' + end_city.cityname);
    try {
        $('.start_date').html(Client.get('start_date').substr(5, 5));
        window.startdate = new Date(Client.get('start_date').replaceAll('-', '/'));
        $('.week').text('(' + weeks[startdate.getDay()] + ')');
        if (startdate.getTime() - 1000 * 60 * 60 * 24 < currdate.getTime()) {
            $('.previous').css('color', '#bfbfbf');
            $('.previous').find('img').attr('src', './images/arrow_list_grey_l.png');
        } else if (startdate.getTime() + 1000 * 60 * 60 * 24 > currdate.getTime() + 1000 * 60 * 60 * 24 * 14) {
            $('.next').css('color', '#bfbfbf');
            $('.next').find('img').attr('src', './images/arrow_list_grey_r.png');
        }
        initDateSelector();

        $('#date').mobiscroll('setValue', wx_date.formatDate(startdate), true);
    } catch (e) {
        console.debug(e);
    }

    //前一天
    $('.previous').click(function () {
        var temp = startdate.getTime() - 1000 * 60 * 60 * 24;
        if (temp < currdate.getTime()) {
            toast.show('亲，选择日期已经过时！');
            $(this).css('color', '#bfbfbf');
            $(this).find('img').attr('src', './images/arrow_list_grey_l.png');
        } else {
            startdate = new Date(temp);
            $('#date').mobiscroll('setValue', wx_date.formatDate(startdate), true);
            $('.start_date').html(wx_date.formatDate(startdate).substr(5, 5));
            $(this).css('color', '#353535');
            $('.next').css('color', '#353535');
            $('.next').find('img').attr('src', './images/arrow_list_r.png');
            $('.week').text('(' + weeks[startdate.getDay()] + ')');
            Client.set('start_date', wx_date.formatDate(startdate));
            page = 1;
            ticketlist(1, 10, true);
        }
    });
    //后一天
    $('.next').click(function () {
        var temp = startdate.getTime() + 1000 * 60 * 60 * 24;
        if (temp > currdate.getTime() + 1000 * 60 * 60 * 24 * 14) {
            toast.show('亲，已经是最后一天了！');
            $(this).css('color', '#bfbfbf');
            $(this).find('img').attr('src', './images/arrow_list_grey_r.png');
        } else {
            startdate = new Date(temp);
            $('#date').mobiscroll('setValue', wx_date.formatDate(startdate), true);
            $('.previous').css('color', '#353535');
            $('.previous').find('img').attr('src', './images/arrow_list_l.png');
            $('.start_date').html(wx_date.formatDate(startdate).substr(5, 5));
            $('.week').text('(' + weeks[startdate.getDay()] + ')');
            Client.set('start_date', wx_date.formatDate(startdate));
            page = 1;
            ticketlist(1, 10, true);
        }
    });
    //时间筛选
    $('#filterTimeBtn').click(function () {
        $('.filtertime_dialog').fadeIn(200);
        $('.filtertime_dialog_mark').fadeIn(200);
    });
    $('.filtertime_dialog_mark').click(function () {
        $('.filtertime_dialog').fadeOut(200);
        $(this).fadeOut(200);
    });
    $('.filtertime_dialog li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        Client.set("time", $(this).attr('data-id'));
        page = 1;
        ticketlist(1, 10, true);
        $('.filtertime_dialog').fadeOut(200);
        $('.filtertime_dialog_mark').fadeOut(200);
    });
    //排序
    $('#sort').click(function () {
        $('.sort_dialog').fadeIn(200);
        $('.sort_dialog_mark').fadeIn(200);
    });
    $('.sort_dialog_mark').click(function () {
        $('.sort_dialog').fadeOut(200);
        $(this).fadeOut(200);
    });
    $('.sort_dialog li').click(function () {
        $(this).addClass('active').siblings('li').removeClass('active');
        Client.set('ordertype', $(this).attr('data-id'));
        page = 1;
        ticketlist(1, 10, true);
        $('.sort_dialog').fadeOut();
        $('.sort_dialog_mark').fadeOut();
    });
    // 读取上次时间筛选和排序
    if (Client.get('ordertype') == null) {
        Client.set('ordertype', '0');
    }
    $('.sort_dialog').find('li[data-id="' + Client.get('ordertype') + '"]').addClass('active').siblings('li').removeClass('active');
    if (Client.get('time') == null) {
        Client.set('time', '00:00-23:59');
    }
    $('.filtertime_dialog').find('li[data-id="' + Client.get('time') + '"]').addClass('active').siblings('li').removeClass('active');

    $('.load_end').hide();
    $(window).scroll(function () {
        if (!ignore_scroll) {
            if (isLast || isLastPage) {
                $('.load_end').show();
                $('.loading').hide();
                return;
            }
            var scrollTop = $(this).scrollTop();
            var scrollHeight = $(document).height();
            var windowHeight = $(this).height();
            if (scrollTop + windowHeight >= scrollHeight - 100) {
                ignore_scroll = true;
                $('.loading').show();
                $('.load_end').hide();
                setTimeout(function () {
                    ignore_scroll = false;
                    ticketlist(page, 10, false);
                }, 100);
            }
        }
    });

    $('.ticketline').click(function () {
        var json = $(this).data('ticketline');
        var user = getuser();
        if (user != null && user != undefined) {
            if (user.rank == 2) {
                json.stationcouponquantity = 0;
                json.balancecouponquantity = 0;
            }
        }
        /*if (json.ticketprice <= json.favprice) {
            json.stationcouponquantity = 0;
            json.balancecouponquantity = 0;
        }*/
        $.cookie('ticket_detail', JSON.stringify(json));
        if (json.balancequantity == 0 || json.stationquantity == 0) {
            toast.show('余票不足，请选择其他班次！');
            return;
        }
        send('ticket_detail.html');
    });

    $('.carmodelid').error(function () {
        $(this).hide();
    });

});

function ticketlist(pageNo, pageSize, isremove) {
    if (page == 1) {
        window.scrollTo(0, 0);
    }
    var params = {};
    params.citystartid = JSON.parse(Client.get('begin_city')).id;
    params.cityarriveid = JSON.parse(Client.get('end_city')).id;
    params.ticketdate = Client.get('start_date');
    if (isempty(params.citystartid) || isempty(params.cityarriveid)) {
        toast.show('请选择出发城市和到达城市');
        setTimeout(function () {
            send('index.html')
        }, 1500);
        return;
    }

    params.ordertype = Client.get('ordertype') == null ? '' : Client.get('ordertype');
    params.pageNo = pageNo;
    params.pageSize = pageSize;

    var time = Client.get('time');
    params.starttime = isempty(time) ? '' : time.split('-')[0];
    params.arrivetime = isempty(time) ? '' : time.split('-')[1];
    params.ststartid = begin_station == null ? '' : begin_station.id;
    params.starriveid = end_station == null ? '' : end_station.id;

    if (isremove) {
        $('.ticketline').not($('.ticketline').eq(0)).remove();
    }
    setTimeStamp(params);
    ajax(params, "public/api/getTicketLineList", function (json) {
        window.ignore_scroll = true;
        console.debug("======>>>>" + checkTimeStamp(json.page));
        if (checkTimeStamp(json.page) == false) {
            return;
        }
        if (json.datas.length == 0 && json.page.pageNo == 1) {
            notdata(json);
            if (json.page.notDataType == 0) {
                //无行程
                $('.notdata').attr('style', 'background:url("images/notdata2.png") no-repeat center;display:block');
            } else if (json.page.notDataType == 1) {
                //有行程 已发完
                $('.notdata').attr('style', 'background:url("images/notdata4.png") no-repeat center;display:block');
            } else {
                //有行程 有不在当前筛选时间下的未发行程
                $('.notdata').attr('style', 'background:url("images/notdata3.png") no-repeat center;display:block');
            }
            $('.load_end').hide();
            $('.loading').hide();
        } else if (page >= json.page.totalPage) {
            $('.load_end').show();
            $('.loading').hide();
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        var list = [];
        jeach(json.datas, function (k, v) {
            var o = $('.ticketline').eq(0).clone(true).show();
            var params = {};
            params.$ = o;
            var p = v;
            jeach(v, function (k, v) {
                if (k == 'ticketprice') {
                    if ((v == p.favprice || v < p.favprice) || (p.balancecouponquantity <= 0 || p.stationcouponquantity <= 0)) {
                        o.find('.ticketprice').parent().hide();
                        o.find('.goodprice').hide().text('价格');
                        return {$: o, val: p.ticketprice};
                    }
                } else if (k == 'stationquantity' && (p.balancequantity <= v)) {
                    //                  &&(p.balancequantity<=v)
                    return {$: o, val: p.balancequantity};
                } else if (k == 'carmodelid') {
                    return {$: o, val: 'images/car_' + v + '.png'};
                } else if (k == 'stationquantity') {
                    if (isempty(p.balancequantity) || isempty(p.stationquantity) || p.balancequantity == 0 || p.stationquantity == 0) {
                        o.find('.stationquantity').parents('.t_fame').html('已售罄');
                        o.find('.goodprice').hide();
                        o.find('.ticketprice').parent().hide();
                        o.find('.favprice').parent().css('color', '#5c5c5c');

                    }
                }
                return params;
            });
            if (p.balancecouponquantity <= 0 || p.stationcouponquantity <= 0) {
                o.find('.ticketprice').parent().hide();
                o.find('.goodprice').hide().text('价格');
                o.find('.favprice').text(p.ticketprice);
            }
            o.find('.carmodelid').hide();
            o.find('.json').text(JSON.stringify(v));
            v.citybeginid = begin_city.id;
            v.citybeginname = begin_city.cityname;
            v.cityendid = end_city.id;
            v.cityendname = end_city.cityname;
            o.data('ticketline', v);
            list.push(o);
        });
        $('.ticketline').last().after(list);
        setTimeout(function () {
            if (page == 2) {
                window.scrollTo(0, 0);
            }
            window.ignore_scroll = false;
        }, 400);
    }, false);
}

function loadFilterData() {
    initFilterEvent();
    var begin_station_id = begin_station == null ? '' : begin_station.id;
    var end_station_id = end_station == null ? '' : end_station.id;
    var $filter_dialog = $('.filter_dialog');
    ajax({
        begincityid: begin_city.id,
        endcityid: end_city.id,
        endstationid: end_station_id
    }, "public/api/getStartStationList", function (json) {
        var beginCityList = json.datas[0];
        if (beginCityList.length > 0) {
            var beginCityElementList = [];
            for (var i = 0; i < beginCityList.length; i++) {
                if (beginCityList[i].hasshift == 1) {
                    beginCityElementList.push('<div data-id="' + beginCityList[i].id + '" onclick="selectBeginStation(this);">' + beginCityList[i].cityname + '</div>');
                }
            }
            $filter_dialog.find('.list_group1').append(beginCityElementList.join(''));
        }
        var $ele = $filter_dialog.find('.list_group1 div[data-id="' + begin_station_id + '"]');
        if ($ele.length > 0) {
            window.ignore_click = true;
            $ele.click();
            window.ignore_click = false;
        }
        if (begin_station_id.length > 0) {
            $('#filterBtn').parent('li').addClass('have');
        }
    }, true);
    ajax({
        endcityid: end_city.id,
        begincityid: begin_city.id,
        beginstationid: begin_station_id
    }, "public/api/getEndStationList", function (json) {
        var endCityList = json.datas[0];
        if (endCityList.length > 0) {
            var endCityElementList = [];
            for (var j = 0; j < endCityList.length; j++) {
                if (endCityList[j].hasshift == 1) {
                    endCityElementList.push('<div data-id="' + endCityList[j].id + '" onclick="selectEndStation(this);">' + endCityList[j].cityname + '</div>');
                }
            }
            $filter_dialog.find('.list_group2').append(endCityElementList.join(''));
        }
        var $ele = $filter_dialog.find('.list_group2 div[data-id="' + end_station_id + '"]');
        if ($ele.length > 0) {
            window.ignore_click = true;
            $ele.click();
            window.ignore_click = false;
        }
        if (end_station_id.length > 0) {
            $('#filterBtn').parent('li').addClass('have');
        }
    }, true);
}

function initFilterEvent() {
    var $filter_dialog = $('.filter_dialog');
    var $filter_dialog_mark = $('.filter_dialog_mark');
    $('#filterBtn').on('click', function () {
        $filter_dialog_mark.fadeIn(200);
        $filter_dialog.fadeIn(200);
    });
    $filter_dialog_mark.on('click', function () {
        $filter_dialog_mark.fadeOut(200);
        $filter_dialog.fadeOut(200);
    });
    $('.filter_dialog .confirm_btn').on('click', function () {
        $filter_dialog.fadeOut(200);
        $filter_dialog_mark.fadeOut(200);
        page = 1;
        $('.load_end').hide();
        $('.loading').fadeIn(200);
        ticketlist(1, 10, true);
    });
    $('.filter_dialog .check_list div').on('click', function () {
        if ($(this).hasClass('start_station')) {
            $(this).parents().find('div').removeClass('check_active');
            $(this).addClass('check_active');
            $filter_dialog.find('.start_station_list').css('display', 'table-cell');
            $filter_dialog.find('.wrapper2').attr('id', 'wrapper');
            $filter_dialog.find('.end_station_list,.start_time_list').hide();
            $filter_dialog.find('.wrapper1').attr('id', '');
        } else if ($(this).hasClass('end_station')) {
            $(this).parents().find('div').removeClass('check_active');
            $(this).addClass('check_active');
            $filter_dialog.find('.end_station_list').css('display', 'table-cell');
            $filter_dialog.find('.wrapper1').attr('id', 'wrapper');
            $filter_dialog.find('.start_station_list,.start_time_list').hide();
            $filter_dialog.find('.wrapper2').attr('id', '');

        }
    });
    $('.filter_dialog .empty_btn').on('click', function () {
        $filter_dialog.find('.list_group1>div').eq(0).click();
        $filter_dialog.find('.list_group2>div').eq(0).click();
    });
}

function selectBeginStation(obj) {
    var $filter_dialog = $('.filter_dialog');
    $(obj).siblings().removeClass('check_item');
    $(obj).addClass('check_item');
    var id = $(obj).attr('data-id');
    if (id != '') {
        $filter_dialog.find('.start_station_opt').text($(obj).text()).addClass('small2');
        $filter_dialog.find('.title1').addClass('small');
        var station = {};
        station.id = id;
        station.cityname = $(obj).text();
        Client.set("begin_station", JSON.stringify(station));
    } else {
        $filter_dialog.find('.start_station_opt').text('').removeClass('small2');
        $filter_dialog.find('.title1').removeClass('small');
        Client.remove("begin_station");
    }
    window.begin_station = JSON.parse(Client.get('begin_station'));
    if (begin_station == null && end_station == null) {
        $('#filterBtn').parent('li').removeClass('have');
    } else {
        $('#filterBtn').parent('li').addClass('have');
    }

    if (!window.ignore_click) {
        ajax({
            endcityid: end_city.id,
            begincityid: begin_city.id,
            beginstationid: id
        }, "public/api/getEndStationList", function (json) {
            var endCityList = json.datas[0];
            if (endCityList.length > 0) {
                var endCityElementList = [];
                for (var j = 0; j < endCityList.length; j++) {
                    if (endCityList[j].hasshift == 1) {
                        endCityElementList.push('<div data-id="' + endCityList[j].id + '" onclick="selectEndStation(this);">' + endCityList[j].cityname + '</div>');
                    }
                }
                $filter_dialog.find('.list_group2>div').not($filter_dialog.find('.list_group2>div').eq(0)).remove();
                $filter_dialog.find('.list_group2').append(endCityElementList.join(''));
            }
            var end_station_id = end_station == null ? '' : end_station.id;
            var $ele = $filter_dialog.find('.list_group2 div[data-id="' + end_station_id + '"]');
            if ($ele.length == 0) {
                window.ignore_click = true;
                $filter_dialog.find('.list_group2 div[data-id=""]').click();
                window.ignore_click = false;
            } else {
                window.ignore_click = true;
                $ele.click();
                window.ignore_click = false;
            }
        }, true);
    }
}

function selectEndStation(obj) {
    var $filter_dialog = $('.filter_dialog');
    $(obj).siblings().removeClass('check_item');
    $(obj).addClass('check_item');
    var id = $(obj).attr('data-id');
    if (id != '') {
        $filter_dialog.find('.end_station_opt').text($(obj).text()).addClass('small2');
        $filter_dialog.find('.title2').addClass('small');
        var station = {};
        station.id = id;
        station.cityname = $(obj).text();
        Client.set("end_station", JSON.stringify(station));
    } else {
        $filter_dialog.find('.end_station_opt').text('').removeClass('small2');
        $filter_dialog.find('.title2').removeClass('small');
        Client.remove("end_station");
    }
    window.end_station = JSON.parse(Client.get('end_station'));
    if (begin_station == null && end_station == null) {
        $('#filterBtn').parent('li').removeClass('have');
    } else {
        $('#filterBtn').parent('li').addClass('have');
    }

    if (!window.ignore_click) {
        ajax({
            begincityid: begin_city.id,
            endcityid: end_city.id,
            endstationid: id
        }, "public/api/getStartStationList", function (json) {
            var beginCityList = json.datas[0];
            if (beginCityList.length > 0) {
                var beginCityElementList = [];
                for (var i = 0; i < beginCityList.length; i++) {
                    if (beginCityList[i].hasshift == 1) {
                        beginCityElementList.push('<div data-id="' + beginCityList[i].id + '" onclick="selectBeginStation(this);">' + beginCityList[i].cityname + '</div>');
                    }
                }
                $filter_dialog.find('.list_group1>div').not($filter_dialog.find('.list_group1>div').eq(0)).remove();
                $filter_dialog.find('.list_group1').append(beginCityElementList.join(''));
            }
            var begin_station_id = begin_station == null ? '' : begin_station.id;
            var $ele = $filter_dialog.find('.list_group1 div[data-id="' + begin_station_id + '"]');
            if ($ele.length == 0) {
                window.ignore_click = true;
                $filter_dialog.find('.list_group1 div[data-id=""]').click();
                window.ignore_click = false;
            } else {
                window.ignore_click = true;
                $ele.click();
                window.ignore_click = false;
            }
        }, true);
    }
}

function initDateSelector() {
    var opt = {};
    opt.date = {preset: 'date'};
    opt.datetime = {preset: 'datetime'};
    opt.time = {preset: 'time'};
    opt.default = {
        theme: 'android-ics light',
        mode: 'scroller',
        lang: 'zh',
        height: 80,
        setText: '确定',
        cancelText: '取消',
        dateFormat: 'yyyy-mm-dd',
        dateOrder: 'yyyymmdd',
        minDate: new Date(),
        maxDate: new Date(new Date().getTime() + 1000 * 60 * 60 * 24 * 14),
        display: 'bottom',
        onShow: function (o, inst) {
            $('.dwv').css('height', '56px').css('line-height', '56px');
            $('.android-ics .dwwl').css('font-size', '28px').css('margin', '0 2px');
            $('.android-ics .dw .dwb').css('height', '100px').css('padding-top', '20px').css('color', '#00CEA8').css('font-size', '30px');
            $('.android-ics .dwv').hide();
            $('.dwwc .dww').css('min-width', '180px');
            $('.android-ics .dw .dwwol').css('border-top', '2px solid #00CEA8').css('border-bottom', '2px solid #00CEA8');
            $('.dwwol').css('height', '100px').css('margin-top', '-52px');
            //.android-ics .dw .dwb
            $('.dw-persp').bind('click', function () {
                $("#date").scroller('hide');
            });
            $('.dwbc').find('.dwb-s').appendTo($('.dwbc').find('.dwb-c').parent());
            $('.dwbc').find('.dwb').css('border-left', 'solid 1px #ccc');
            $('.dwbg').css({'bottom': '0px', 'top': 'initial'});
            $('.dwb').css({'padding-top': '0px', 'line-height': '110px', 'height': '110px'});
        },
        onSelect: function (o, inst) {
            var date = $('#date').val();
            Client.set('start_date', date);
            $('.start_date').html(Client.get('start_date').substr(5, 5));
            startdate = new Date(Client.get('start_date').replaceAll('-', '/'));
            $('.week').text('(' + weeks[startdate.getDay()] + ')');
            if (startdate.getTime() - 1000 * 60 * 60 * 24 < currdate.getTime()) {
                $('.previous').css('color', '#bfbfbf');
                $('.previous').find('img').attr('src', './images/arrow_list_grey_l.png');
                $('.next').css('color', '#353535');
                $('.next').find('img').attr('src', './images/arrow_list_r.png');
            } else if (startdate.getTime() + 1000 * 60 * 60 * 24 > currdate.getTime() + 1000 * 60 * 60 * 24 * 14) {
                $('.next').css('color', '#bfbfbf');
                $('.next').find('img').attr('src', './images/arrow_list_grey_r.png');
                $('.previous').css('color', '#353535');
                $('.previous').find('img').attr('src', './images/arrow_list_l.png');
            } else {
                $('.previous').css('color', '#353535');
                $('.previous').find('img').attr('src', './images/arrow_list_l.png');
                $('.next').css('color', '#353535');
                $('.next').find('img').attr('src', './images/arrow_list_r.png');
            }
            page = 1;
            ticketlist(1, 10, true);
        }
    };
    $('.t_today').click(function () {
        $("#date").mobiscroll('show');
    });
    window.mobiscroll = $("#date").mobiscroll($.extend(opt['date'], opt['default']));
}
