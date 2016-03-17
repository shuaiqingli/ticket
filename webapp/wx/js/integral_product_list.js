$(function () {
    list(1, 10);
    var win_width = $(window).width();
    var left = (win_width - 600) / 3;
    $(".coupon_cell").css('margin-left', left + 'px');
    $(window).resize(function () {
        var win_width = $(window).width();
        var left = (win_width - 600) / 3;
        $(".coupon_cell").css('margin-left', left + 'px');
    });
    $(window).scroll(function () {
        if ((typeof(isNoData) != 'undefined'&& isNoData) || isLast) {
            return;
        }
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
        var windowHeight = $(this).height();
        if (scrollTop + windowHeight >= scrollHeight - 3) {
            list(page, 10);
        }
    });
});

function list(pageNo, pageSize) {
    var params = {};
    params.pageNo = pageNo;
    params.pageSize = pageSize;
    ajax(params, "web/api/customer/integralProducts", function (json) {
        var integralTotal = json.datas[0];
        var integralProductList = json.datas[1];
        $('#integralTotal').text(integralTotal);
        if (integralProductList.length == 0 && json.page.pageNo == 1) {
            $('body').append("<div class='notdata'></div>");
            $('.notdata').show();
            window.isNoData = true;
            return;
        }
        var itemList = [];
        for (var i = 0; i < integralProductList.length; i++) {
            var integralProduct = integralProductList[i];
            var item = $('#productList').find('.coupon_cell').eq(0).clone(true).css('display', '');
            item.find('.lowlimit').text(integralProduct.lowlimit);
            item.find('.vprice').text(integralProduct.vprice);
            item.find('.amount').text(integralProduct.amount);
            if (integralTotal >= integralProduct.amount && (integralProduct.stockflag == 0 || integralProduct.currentstock > 0)) {
                item.find('.buy').css('background-image', 'url(images/green_btn.png)').attr('onclick', 'buy(' + integralProduct.id + ');');
            } else {
                item.find('.buy').css('background-image', 'url(images/grey_btn.png)');
                if(integralProduct.stockflag == 1 && integralProduct.currentstock <= 0){
                    item.find('.buy').text('已兑完');
                }
            }
            item.find('.validdate').text(integralProduct.validday);
            itemList.push(item);
        }
        $('#productList').append(itemList);
    });
}

function buy(id) {
    if (typeof(id) == 'number' && id > 0) {
        if (confirm('确认兑换此商品？') == false) {
            return;
        }
        var param = {};
        param.id = id;
        ajax(param, "web/api/customer/buyIntegralProduct", function (json) {
            if (json.datas[0] == 'success') {
                location.reload();
            } else {
                toast.show('操作失败');
            }
        });
    }
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