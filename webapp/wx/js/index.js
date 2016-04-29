$(function () {
    window.flag = getUrlParam('flag');
    if (flag != 'show_city') {
        Client.remove('begin_city');
        Client.remove('end_city');
        Client.remove('begin_station');
        Client.remove('end_station');
    }
    Client.remove('time');
    Client.remove('ordertype');
    Client.remove('type');
    initPage();
    initEvent();
    loadNewsData();
    loadHistory();
});

function initPage() {
    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    if (day <= 9) {
        day = '0' + day;
    }
    if (month <= 9) {
        month = '0' + month;
    }
    $('#date').val(month + '-' + day + '(今天)');
    $('#date').attr('year', date.getFullYear());

    window.begin_city = JSON.parse(Client.get('begin_city'));
    window.end_city = JSON.parse(Client.get('end_city'));
    window.begin_station = JSON.parse(Client.get('begin_station'));
    window.end_station = JSON.parse(Client.get('end_station'));
    if (begin_city != null && begin_station != null) {
        $('.begin_city').find('.cityname').css('color', '#535353').html(begin_station.cityname);
    } else if (begin_city != null) {
        $('.begin_city').find('.cityname').css('color', '#535353').html(begin_city.cityname);
    }
    if (end_city != null && end_station != null) {
        $('.end_city').find('.cityname').css('color', '#535353').html(end_station.cityname);
    } else if (end_city != null) {
        $('.end_city').find('.cityname').css('color', '#535353').html(end_city.cityname);
    }
    if (begin_city != null && end_city != null) {
        $('.submit').css('background', '#00cea8');
    }
    if (begin_city != null) {
        $('.begin_city img').attr('src', 'images/delete_first@2x.png');
    }
    if (end_city != null) {
        $('.end_city img').attr('src', 'images/delete_first@2x.png');
    }
}

function loadNewsData() {
    var params = {type: 1};
    ajax(params, "public/api/news", function (json) {
        var newsList = json.datas[0];
        if (newsList.length > 0) {
            var elementList = [];
            for (var i = 0; i < newsList.length; i++) {
                elementList.push('<div>' + newsList[i].amtitle + '</div>');
            }
            $('#announcement .single-item').text('').append(elementList);
            $('#announcement').show();
            $('#announcement .single-item').slick({
                infinite: true,
                autoplaySpeed: 5000,
                speed: 2000,
                slidesToShow: 1,
                slidesToScroll: 1,
                autoplay: true,
                arrows: false
            });
        }
    }, true);
}

function initEvent() {
    var $body = $('body');
    $('.begin_city').on('click', function () {
        send('city_list.html?city_flag=begin_city');
    });
    $('.end_city').on('click', function () {
        send('city_list.html?city_flag=end_city');
    });
    $('.submit').on('click', function () {
        if (begin_city == null || end_city == null) {
            toast.show('选择出发和到达城市！');
            return;
        } else if ($('#date').val() == false) {
            toast.show('出发日期不能为空！');
            return;
        }
        if (end_city.id == begin_city.id) {
            toast.show("出发城市与到达城市不能相同！");
            return;
        }
        Client.set("start_date", $('#date').attr('year') + '-' + $('#date').val().substring(0, 5));
        send('ticket_list.html');
    });
    $('.change_icon').on('click', function () {
        if (begin_city == null && end_city == null) {
            return;
        }
        Client.set("begin_city", JSON.stringify(end_city));
        Client.set("end_city", JSON.stringify(begin_city));
        Client.set("begin_station", JSON.stringify(end_station));
        Client.set("end_station", JSON.stringify(begin_station));
        window.begin_city = JSON.parse(Client.get('begin_city'));
        window.end_city = JSON.parse(Client.get('end_city'));
        window.begin_station = JSON.parse(Client.get('begin_station'));
        window.end_station = JSON.parse(Client.get('end_station'));
        if (begin_city != null && begin_station != null) {
            $('.begin_city').find('.cityname').css('color', '#535353').html(begin_station.cityname);
        } else if (begin_city != null) {
            $('.begin_city').find('.cityname').css('color', '#535353').html(begin_city.cityname);
        } else {
            $('.begin_city').find('.cityname').css('color', '#b7b7b7').html('请选择出发地');
        }
        if (end_city != null && end_station != null) {
            $('.end_city').find('.cityname').css('color', '#535353').html(end_station.cityname);
        } else if (end_city != null) {
            $('.end_city').find('.cityname').css('color', '#535353').html(end_city.cityname);
        } else {
            $('.end_city').find('.cityname').css('color', '#b7b7b7').html('请选择到达地');
        }
    });
    $('.begin_city img').parent('span').on('click', function () {
        Client.remove('begin_city');
        Client.remove('begin_station');
        $('.begin_city').find('.cityname').css('color', '#b7b7b7').html('请选择出发地');
        $(this).find('img').attr('src', 'images/right_arrow.png');
        $('.submit').css('background', '#d9d9d9');
        return false;
    });
    $('.end_city img').parent('span').on('click', function () {
        Client.remove('end_city');
        Client.remove('end_station');
        $('.end_city').find('.cityname').css('color', '#b7b7b7').html('请选择到达地');
        $(this).find('img').attr('src', 'images/right_arrow.png');
        $('.submit').css('background', '#d9d9d9');
        return false;
    });
}

function loadHistory() {
    var history = JSON.parse(Client.get('history'));
    if (null != history && history.length > 0) {
        var elementList = [];
        for (var i = 0; i < history.length; i++) {
            var $element = $('<div class="history_item" style="color:#a38e77;line-height: 50px;padding-top: 15px;" onclick="chooseHistory(this);"></div>');
            $element.attr('data', JSON.stringify(history[i])).text(history[i].begin_station.cityname + '-' + history[i].end_station.cityname);
            elementList.push($element);
        }
        $('.history_container>div').append(elementList);
        $('.history_container').show();
    }
}

function chooseHistory(obj) {
    var history_item = JSON.parse($(obj).attr('data'));
    Client.set("begin_city", JSON.stringify(history_item.begin_city));
    Client.set("end_city", JSON.stringify(history_item.end_city));
    Client.set("begin_station", JSON.stringify(history_item.begin_station));
    Client.set("end_station", JSON.stringify(history_item.end_station));
    Client.set("start_date", $('#date').attr('year') + '-' + $('#date').val().substring(0, 5));
    send('ticket_list.html');
}


