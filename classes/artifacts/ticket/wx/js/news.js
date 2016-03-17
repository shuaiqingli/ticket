$(function () {
    loadData();
    initPage();
});

function initPage() {
    var systom = document.getElementById('systom');
    var personel = document.getElementById('personel');
    var sspan = systom.getElementsByTagName('span')[0];
    var pspan = personel.getElementsByTagName('span')[0];
    var nav1 = document.getElementById('nav1');
    var nav2 = document.getElementById('nav2');
    systom.onclick = function () {
        sspan.className = 'n_on';
        pspan.className = '';
        nav1.className = 'n_nav n_play';
        nav2.className = 'n_nav';
    };
    personel.onclick = function () {
        sspan.className = '';
        pspan.className = 'n_on';
        nav1.className = 'n_nav';
        nav2.className = 'n_nav n_play';
        /*var text_heits = $('.n_text').height();
        console.log(text_heits);
        var tip_heits = $('.tip_box').height();
        console.log(tip_heits);
        var m_top = (text_heits - tip_heits) / 2;
        if(m_top>0){
        	$('.tip_box').css('margin-top',m_top+'px');
        }*/
    };
    var width = $(window).width() - 48;
    $('#nav1 li>div').css('width', width + 'px');
    $('#nav1 li img').css('width', width + 'px');
    var win_hei = $(window).height();
    var heis = $('#call_box').height();
    var top = (win_hei - heis) / 2;
    $('#call_box').css('top', top + 'px');
    var win_width = $(window).width();
    var widths = $('#call_box').width();
    var left = (win_width - widths) / 2;
   
    $('#call_box').css('left', left + 'px');
    $(window).resize(function () {
        var win_width = $(window).width();
        var widths = $('#call_box').width();
        var left = (win_width - widths) / 2;
        $('#call_box').css('left', left + 'px');
        var win_hei = $(window).height();
        var heis = $('#call_box').height();
        var top = (win_hei - heis) / 2;
        $('#call_box').css('top', top + 'px');
    });
    $('.n_text').click(function () {
        $('#call_box').text($(this).find('p').eq(0).text());
        $('#overlay').show();
        $('#call_box').show();
    });
    $('#call_box,#overlay').click(function () {
        $('#overlay').hide();
        $('#call_box').hide();
    });
    /*$('#personel').click(function () {
        $('.notdata').hide();
    });*/
    $('#nav1 li').click(function () {
        $('#info_box,#overlay1').show();
    });
    $('#info_box,#overlay1').click(function () {
        $('#info_box,#overlay1').hide();
    });

}

function loadData() {
    var params = {};
    ajax(params, "public/api/news", function (json) {
        var newsList = json.datas[0];
        var activityElementList = [];
        var noticeElementList = [];
        var activityIDList = [];
        var noticeIDList = [];
        if (newsList.length > 0) {
            for (var i = 0; i < newsList.length; i++) {
                var news = newsList[i];
                var element = null;
                if(news.amsort == 0){
                    element = $('ul.activityList').find('li').eq(0).clone(true).css('display', '');
                    element.find('img').attr('src', news.ampictureurl);
                    element.find('div.title').text(news.amtitle);
                    element.attr('onclick', "send('news_detail.html?id="+news.id+"')");
                    activityElementList.push(element);
                    activityIDList.push(news.id);
                }else if(news.amsort == 1){
                    element = $('ul.noticeList').find('li').eq(0).clone(true).css('display', '');
                    element.find('div.title').text('').append(news.amtitle);
                    element.find('div.begindate').text(news.begindate.substring(5,10));
                    if($.cookie('noticeIDList') == null || $.cookie('noticeIDList').split(',').indexOf(news.id+'') < 0){
                        element.find('div.begindate').css('border-color', '#ff6861').css('color', '#ff6861');
                    }
                    noticeElementList.push(element);
                    noticeIDList.push(news.id);
                }
            }
        }
        $('ul.activityList').append(activityElementList);
        $('ul.noticeList').append(noticeElementList);
        if(activityElementList.length == 0){
            notdata({},$('ul.activityList'));
        }
        if(noticeElementList.length == 0){
            notdata({},$('ul.noticeList'));
        }
        var expiresDate= new Date();
        expiresDate.setTime(expiresDate.getTime() + (60 * 24 * 60 * 60 * 1000));
        $.cookie('activityIDList', activityIDList.join(','), { path: "/", expires: expiresDate});
        $.cookie('noticeIDList', noticeIDList.join(','), { path: "/", expires: expiresDate});
        $.cookie('message_flag', null, {path: "/"});
    });

	
}