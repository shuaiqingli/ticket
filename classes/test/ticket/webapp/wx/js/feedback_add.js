$(function () {
    var type = getUrlParam('type');
    if(typeof type != 'string' || type.length == 0){
        type = '1';
    }
    $('#options').text('').append($('#sel_box').find('span[type='+type+']').clone()).append('&gt;');
    refreshHolder(type);
    resizePage();
    $(window).resize(function () {
        resizePage();
    });
    $('#sel_option').click(function () {
        $('#sel_box').show();
        $('#overlay').show();
    });
    $('#overlay').click(function () {
        $('#sel_box').hide();
        $('#overlay').hide();
    });
    $('#sel_box').find('div').click(function () {
        $('#options').text('').append($(this).html() + '&gt;');
        refreshHolder($(this).find('span').attr('type'));
        $('#sel_box').hide();
        $('#overlay').hide();
    });
});

function resizePage() {
    var width = $('#sel_box').width();
    var win_width = $(window).width();
    var f_width = $('#container').width();

    var lefts = (win_width - width) / 2;
    var width_text = f_width - 52;
    $('#sel_box').css({
        left: lefts + "px"
    });
    $('#text_box').css('width', width_text + 'px');
}

function refreshHolder(type){
    if(type == '8'){
        $('#text_box').attr('placeholder','请描述您的投诉或建议，我们的客服人员将尽快处理...');
        $('.ticket_notice').hide();
    }else if(type == '7'){
        $('#text_box').attr('placeholder','改/退票的规则请参考“订票须知”，请描述您的问题，我们的客服人员会根据现行规则处理您的问题...');
        $('.ticket_notice').show();
    }else{
        $('#text_box').attr('placeholder','请在此处描述问题...');
        $('.ticket_notice').hide();
    }
}

function addFeedback() {
    var params = {};
    params.type = $('#options').find('span').attr('type');
    params.content = $('#text_box').val();
    params.remark = getUrlParam('remark');
    if (typeof params.content != 'string' || params.content.length == 0) {
        toast.show('问题内容不能为空');
    }
    ajax(params, "web/api/customer/feedbackAdd", function (json) {
        if (json.datas[0] == 'success') {
            location.href = 'feedback_list.html';
        } else {
            toast.show('操作失败');
        }
    });
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}