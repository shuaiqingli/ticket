$(function () {
    window.id = getUrlParam('id');
    feedbackDetail();
    resizePage();
    $(window).resize(function () {
        resizePage();
    });
    $('#question_box').focus(function () {
        //$(this).attr('rows', '3');
        $('#send_btn').css('height', '124px');
        $('#send_btn>div').css('position', 'relative');
        $('#send_btn>div').css('top', '30px');

    });
    $('#question_box').blur(function () {
        $(this).attr('rows', '1');
        $('#send_btn').css('height', '56px');
        $('#send_btn>div').css('position', 'initial');
        $('#send_btn>div').css('top', 'initial');
    });
    $.cookie('message_flag', null, {path: "/"});
});

function resizePage() {
    var win_width = $(window).width();
    var width_text = win_width - 140;
    var width_texts = win_width - 20;
    $('#question_area').css('width', width_texts + 'px');
    $('#question_box').css('width', width_text + 'px');
}

function feedbackDetail() {
    if (typeof id == 'string' && id.length > 0) {
        var params = {};
        params.id = id;
        ajax(params, "web/api/customer/feedbackDetail", function (json) {
            var feedback = json.datas[0];
            if (typeof feedback == 'object' && typeof feedback.feedbackDetailList == 'object') {
                var feedbackDetailItemElementList = [];
                for (var i = 0; i < feedback.feedbackDetailList.length; i++) {
                    var feedbackDetailItem = feedback.feedbackDetailList[i];
                    var feedbackDetailItemElement = $('.feedbackDetailList').find('.feedbackDetailItem').eq(0).clone(true).css('display', '');
                    if (feedbackDetailItem.type == 1) {
                        feedbackDetailItemElement.find('.feedbackDetailItemHead').text('提问');
                    } else {
                        feedbackDetailItemElement.css('background', '#fff');
                        feedbackDetailItemElement.find('.feedbackDetailItemHead').text('回答');
                    }
                    feedbackDetailItemElement.find('.feedbackDetailItemDate').text(feedbackDetailItem.makedate);
                    feedbackDetailItemElement.find('.feedbackDetailItemContent').text(feedbackDetailItem.content);
                    feedbackDetailItemElementList.push(feedbackDetailItemElement);
                    if (i == feedback.feedbackDetailList.length - 1 && feedbackDetailItem.type == 1) {
                        $('.unanswer').show();
                    }
                }
                $('.feedbackDetailList').append(feedbackDetailItemElementList);
                if(feedback.status == 2){
                    $('#question_area').hide();
                }
            } else {
                toast.show('操作失败');
            }
        });
    }
}

function feedbackReply() {
    if (typeof id == 'string' && id.length > 0) {
        var params = {};
        params.feedbackid = id;
        params.content = $('#question_box').val();
        if (params.content.length == 0) {
            toast.show('内容不能为空');
        }
        ajax(params, "web/api/customer/feedbackReply", function (json) {
            if (json.datas[0] == 'success') {
                location.href = 'feedback_detail.html?id=' + id;
            } else {
                toast.show('操作失败');
            }
        });
    }
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};