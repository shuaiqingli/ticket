$(function () {
    list(1, 100);
});

function list(pageNo, pageSize) {
    var params = {};
    params.pageNo = pageNo;
    params.pageSize = pageSize;
    ajax(params, "web/api/customer/feedbacks", function (json) {
        var feedbackList = json.datas;
        if (feedbackList.length == 0 && json.page.pageNo == 1) {
            $('.question_list').append("<div class='notdata'></div>");
            $('.notdata').show();
            window.isNoData = true;
            return;
        }
        var itemList = [];
        for (var i = 0; i < feedbackList.length; i++) {
            var feedback = feedbackList[i];
            var item = $('.question_item').eq(0).clone(true).css('display', '');
            item.find('.question_item_id').text("ID:" + feedback.id);
            var typeName = '其它问题';
            if (feedback.type == 1) typeName = '支付问题';
            if (feedback.type == 2) typeName = '帐户问题';
            if (feedback.type == 3) typeName = '订单问题';
            if (feedback.type == 4) typeName = '评论问题';
            if (feedback.type == 5) typeName = '促销问题';
            if (feedback.type == 6) typeName = '其他问题';
            if (feedback.type == 7) typeName = '退票问题';
            if (feedback.type == 8) typeName = '投诉建议';
            if (feedback.type == 9) typeName = '积分及代金券';
            item.find('.question_item_type').text(typeName);
            item.find('.question_item_date').text(feedback.makedate);
            item.find('.question_item_content').text(feedback.content);
            if (feedback.latestRecordsForAdmin > 0) {
                item.find('.question_item_new').show();
            } else {
                item.find('.question_item_new').hide();
            }
            if(feedback.status == 2){
                item.find('.question_item_status').text('(已关闭)');
            }else{
                item.find('.question_item_status').text('(处理中)');
            }
            item.attr('onclick', 'location.href="feedback_detail.html?id=' + feedback.id + '";');
            itemList.push(item);
        }
        $('.question_list').append(itemList);
    });
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