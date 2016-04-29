$(function (e) {
    $('body').hide();
    $('.start_date').html($.cookie('start_date'));
    window.detail = $.cookie('ticket_detail');
    window.coupon = null;

    ajax({lmid: JSON.parse(detail).lmid}, "public/api/getLineManageCouponFlag", function (json) {
        if (json.datas.length == 1) {
            if (typeof json.datas[0].couponflag == 'undefined' || json.datas[0].couponflag == 1) {
                $('.coupon_dispay').show();
                coupon = $.cookie('coupon');
            } else {
                $('.coupon_dispay').hide();
            }
            if (detail != null) {
                calculationPrice(detail);
                $('body').show();
            }
        }
    }, true);

    var link = $.cookie('link');
    if (link == null || link == '' || link == undefined) {
        ajax({pageNo: 1, pageSize: 1}, "web/api/linkman/list", function (json) {
            if (json.datas.length == 0) {
                toast.show('请添加联系人！');
            } else {
                linkhanlder(json.datas[0])
                $.cookie('link', JSON.stringify(json.datas[0]));
            }
        });
    } else {
        linkhanlder(JSON.parse(link));
    }

    $('.option').click(function () {
        send('option_linkman.html');
    });

    $('.order').click(function () {
        if (isempty(cookie('link'))) {
            toast.show('请选择联系人！');
            return;
        }
        var params = JSON.parse($.cookie('ticket_detail'));
        params.quantity = $.cookie('ticket_num');
        params.linkid = JSON.parse($.cookie('link')).id;
        params.buyway = 'WX';
        params.discountnum = $.cookie('cnum');
        if (coupon && coupon.vouchercode) {
            params.vouchercode = coupon.vouchercode;
        }
        $.cookie('coupon', null);
        if (detail.stationcouponquantity >= 1 && detail.balancecouponquantity >= 1) {
            params.isdiscount = 1;
        }
        ajax(params, "web/api/saleorder/order", {
            success: function (json) {
                $.cookie('orderid', json.datas[0].id);
                var o = json.datas[0];
                if (o.autopay == "1") {
                    saveHistory();
                    send("buy_success.html?id=" + o.id);
                    return;
                }
                try {
                    wxpay($.cookie('orderid'), function (res) {
                        saveHistory();
                        send("buy_success.html?id=" + o.id);
                    }, function (res) {
                        var params = {};
                        params.id = $.cookie('orderid');
                        params.status = 4;
                        ajax(params, "web/api/saleorder/cancelorder", function (json) {
                            send("ticket_list.html");
                        });
                    })
                } catch (e) {
                    var params = {};
                    params.id = $.cookie('orderid');
                    params.status = 4;
                    ajax(params, "web/api/saleorder/cancelorder", function (json) {
                        send("ticket_list.html");
                    });
                    send("ticket_list.html?id=" + o.id);
                }
            }, error: function (json) {
                if (json.code == 6000) {
                    setTimeout(function () {
                        send('ticket_list.html');
                    }, 1000);
                }
            }
        });
    });

    $('._coupon').bind('click', function () {
        send('coupon.html');
    });

    post("web/api/customer/coupons", function (json) {
        try {
            if (json.datas.length != 0) {
                $('._coupon').bind('click', function () {
                    if (getuser().rank == 2) {
                        toast.show('站务不能使用优惠劵！')
                        return;
                    }
                    send('coupon.html');
                });
                if (getuser().rank == 2) {
                    return;
                }
                var isuse = 0;
                $.each(json.datas, function (i, v) {
                    if (isuse == 1) {
                        return;
                    }
                    if ($.cookie('coupon') == null && $.cookie('not_use_coupon') != 'true') {
                        var total = parseFloat($.cookie('total'));
                        var num = parseInt($.cookie('ticket_num'));
                        var low = v.lowlimit;
                        if (total >= low) {
                            $.cookie('coupon', JSON.stringify(v));
                            calculationPrice();
                            isuse = 1;
                        }
                    }
                });
            } else {

            }

        } catch (e) {
            //toast.show('没有可以使用的优惠劵！');
            //$.cookie('coupon',null);
            //$('._coupon').find('span').hide();
            //calculationPrice();
        }
    })

});

function calculationPrice() {
    if (typeof detail == 'string') {
        detail = JSON.parse(detail);
    }

    detail.cnum = parseInt($.cookie('cnum') || 0);
    detail.tnum = parseInt($.cookie('tnum') || 0);
    detail.total = parseFloat($.cookie('total') || 0);

    jeach(detail, function (k, v) {
        return true;
    });
    if (detail.tnum == 0) {
        $('.ticket_price').hide();
    } else {
        $('.ticket_price').show();
    }
    if (detail.cnum == 0) {
        $('.coupon_ticket').hide();
    } else {
        $('.coupon_ticket').show();
    }
    var total = detail.total;
    var price = 0;
    if (!isempty(coupon)) {
        if (typeof coupon == 'string') {
            coupon = JSON.parse(coupon);
        }

        var t = parseFloat($.cookie('total'));
        var low = coupon.lowlimit;
        if (t < low) {
            $.cookie('coupon', null);
            calculationPrice();
            return;
        }


        price = coupon.vprice;
        if (isNaN(price) || price == null || price == undefined) {
            price = 0;
        } else {
            $('._coupon').find('*').show();
        }
        $('.vprice').text(price);
        $('.coupon_dispay').show();
    } else {

    }
    total = total.toFixed(1)
    if ((total - price) <= 0) {
        total = 0.00;
    } else {
        total = total - price;
    }
    $('.pay_price').html(total.toFixed(1));
    $('body').show();
}

function linkhanlder(json) {
    jeach(json, function (k, v) {
        if (k == 'idcode' && isempty(v) == false) {
            if (v.indexOf('*') == -1) {
                var val = v.substring(0, 6) + '****' + v.substring(v.length - 4);
                return {val: val}
            }
        }
        return true;
    })
    if (isempty(json.idcode)) {
        $('.idcode').parent().hide();
    }
}