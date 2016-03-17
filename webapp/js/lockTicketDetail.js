$(function () {
    $('.chair').live('click', function () {
        if ($(this).hasClass('sold_out')) {

        } else if ($(this).hasClass('sel')) {
            $(this).removeClass('sel');
            var nos = $(this).attr('chairNo');
            $('.show_list>div[chairno="' + nos + '"]').remove();
        } else {
            if ($('.show_list>div').length == 5) {
                alert('每次最多可购5张！');
                return;
            }
            $(this).addClass("sel");
            var no = $(this).attr('chairNo');
            $('.show_list').append($('.chair_items').eq(0).clone().show().css('display', 'inline-block').text(no + '号').attr('chairNo', no));

        }
    });
});

function buy() {
    var seatnoList = [];
    $('.show_list').find('.chair_items').each(function () {
        seatnoList.push($(this).attr('chairno'));
    });
    var id = $('input[name="id"]').val();
    var quantity = $('input[name="quantity"]').val();
    var name = $('input[name="name"]').val();
    var mobile = $('input[name="mobile"]').val();
    if ($('.seat_container').is(':visible')) {
        if (seatnoList.length == 0) {
            layer.msg('请选择座位号');
            return false;
        }
        quantity = seatnoList.length;
    }
    if (quantity > 5 || quantity < 1) {
        layer.msg('每次购买数量为1到5张');
        return false;
    }
    if (name.length == 0) {
        layer.msg('请输入姓名');
        return false;
    }
    if (mobile.length == 0) {
        layer.msg('请输入电话');
        return false;
    }
    layer.confirm('确认购买共' + quantity + '张票?', {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/user/lockTicket.do',
            data: {id: id, name: name, mobile: mobile, seatnoList: seatnoList.join(','), quantity: quantity},
            dataType: "json",
            success: function (data) {
                if (typeof(data) == 'number' && data == 1) {
                    location.href= basePath + '/user/lockTicketList.do';
                    //layer.msg('操作成功');
                } else {
                    layer.msg('操作失败');
                }
            },
            error: function () {
                layer.msg('操作失败');
            }
        });
    }, function () {
    });
}
