$(function () {
    orderid = $.cookie('orderid');
    console.debug(orderid);

    ajax({orderid: orderid}, "web/api/saleorder/ticketDetail", function (json) {
        if (json.datas.length == 0) {
            $('.ticket').before('<div class=notdata></div>');
            $('.notdata').show();
        } else {
            var isold = false;
            jeach(json.datas, function (k, v) {
                var tk = $('.ticket').eq(0).clone(true).removeClass('notdata');
                tk.find('.checkcode').text(v.checkcode);
                if (v.seatno == 0 || v.seatno == undefined) {
                    v.seatno = 0;
                    tk.find('.seatno').text('空位座');
                } else {
                    tk.find('.seatno').text(v.seatno + '号');
                }
                tk.find('.qrcode').attr('src', '../wx/qrcode/' + v.checkcode + '.png').show();
                if (v.status != 0 || v.isontrain == 1) {
                    tk.find('.choose_opt').remove();
                }
                if (v.isontrain == 1) {
                    isold = true;
                }
                tk.data('json', v);
                $('.ticket').last().after(tk);

                var tk_item = $('.ticket-qrcode-item').eq(0).clone(true).removeClass('notdata');
                tk_item.find('.index').text((parseInt(k) + 1) + "/" + json.datas.length);
                tk_item.find('.checkcode').text(v.checkcode);
                if (v.seatno == 0 || v.seatno == undefined) {
                    tk_item.find('.seatno').text('空位座');
                } else {
                    tk_item.find('.seatno').text(v.seatno + '号');
                }
                tk_item.find('.qrcode').attr('src', '../wx/qrcode/' + v.checkcode + '.png').show();
                $('.ticket-qrcode-item').last().after(tk_item);
            });
            if (isold) {
                $('.outdate_btn').show();
            } else {
                $('.btn_cancel').show();
            }
            $('.ticket-qrcode-item').eq(0).remove();
        }
    });

    if ($.cookie('orderstatus') != '1') {
        $('.order_status').hide();
    }

    $('.qrcode').error(function () {
        $(this).hide();
    });

    $('.confirm_btn').click(function () {
        var items = $('input[value="1"]');
        if (items.length == 0) {
            toast.show('请选择您需要退的车票！');
            return;
        }
        var ids = [];
        $.each(items, function () {
            var json = $(this).parents('.ticket').data('json');
            ids.push(json.checkcode);
        });
        var params = {};
        params.checkcodes = ids.join(',');
        ajax(params, "web/api/saleorder/getRefundInfo", function (json) {
            var result = json.datas[0];
            $('#overlay1,#tip_box').show();
            jeach(result, function () {
                var params = {};
                params.$ = $('#tip_box');
                return params;
            });
            if (result.rank != 2) {
            } else {
                $('.percent,.refundmoney').parent().hide();
            }
            if (!isempty(result.status)) {
                if (result.status == 1) {
                    $('.order_status').show();
                }
            }
            $('#qd').data('params', params).bind('click', function () {
                ajax(params, "web/api/saleorder/refundTicket", function (json) {
                    send('order_detail.html');
                });
            });
        });

    });
    $('.tp').click(function () {
        $('.ticket-list').addClass('active');
        $('.ticket-select').show();
        $('.select-box').fadeIn();
        $('.select-disabled').fadeIn();
        $('.btn_group').show();
        $('.btn_cancel').hide();

    });
    $('.ticket-select').click(function () {
        var val = $(this).siblings('input').attr('value');
        var maxval = 0;

        if (val == 0) {
            $(this).siblings('input').val(1);
            $(this).siblings('.select-box').attr('src', './images/choose_light.png');
        } else {
            $(this).siblings('input').val(0);
            $(this).siblings('.select-box').attr('src', './images/choose_grey.png');
        }
        $('.ticket-list').find('input').each(function () {
            maxval = $(this).val() > maxval ? $(this).val() : maxval;
        });
        if (maxval == 0) {
            $('.confirm_btn').removeClass('active');
        } else {
            $('.confirm_btn').addClass('active');
        }
    });
    $('.cancel_btn').click(function () {
        $('.ticket-cell').find('input').val(0);
        $('.ticket-cell').find('.select-box').attr('src', './images/choose_grey.png');
        $('.ticket-list').removeClass('active');
        $('.ticket-select').hide();
        $('.select-box').hide();
        $('.select-disabled').hide();
        $('.btn_group').hide();
        $('.btn_cancel').show();
        $('.confirm_btn').removeClass('active');
    });
    $('.qrcode').click(function () {
        $('.ticket-dialog-mask').fadeIn();
        $('.ticket-dialog').fadeIn();
        $('.ticket-qrcode-list').slick({
            dots: true,
            infinite: true,
            speed: 300,
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
        });
    });
    $('.ticket-dialog-mask').click(function () {
        $(this).fadeOut();
        $('.ticket-dialog').fadeOut();
    });
    $('.ticket-dialog').click(function () {
        $('.ticket-dialog').fadeOut();
        $('.ticket-dialog-mask').fadeOut();
    });

    getDialogSize('.ticket-dialog');
    $(window).resize(function () {
        getDialogSize('.ticket-dialog');
    });

});


function getDialogSize(e) {
    if ($(window).width() > 640) {
        $(e).css({
            'width': $(window).width() * 0.84,
            'top': $(window).height() * 0.1,
            'padding-left': $(window).width() * 0.08,
            'padding-right': $(window).width() * 0.08,
        });
    } else {
        $(e).css({
            'width': $(window).width() * 0.9,
            'top': $(window).height() * 0.05,
            'padding-left': $(window).width() * 0.05,
            'padding-right': $(window).width() * 0.05,
        });
    }
}
