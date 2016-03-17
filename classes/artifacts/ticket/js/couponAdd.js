$(function () {
    window.couponForm = $('#couponForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
        },
        beforeSubmit: function () {
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/admin/couponLineList.do';
            } else {
                layer.msg('操作失败');
            }
        }
    });
});

function selectCustomerDialog() {
    layer.open({
        type: 2,
        title: '选取顾客',
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/admin/selectCustomer.do'
    });
}

function selectCustomer(cid, cname) {
    $('input[name="cid"]').val(cid);
    $('input[name="cname"]').val(cname);
    layer.closeAll();
}