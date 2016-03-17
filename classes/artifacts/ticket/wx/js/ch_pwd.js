$(function () {
    post("web/api/customer/info", function (json) {
        var customer = json.datas[0];
        $('.username').text(customer.cname);
        $('.mobile').text(customer.mobile);
    });
});

function change() {
    var oldpassword = $('input[name="oldpassword"]').val();
    var password = $('input[name="password"]').val();
    var password2 = $('input[name="password2"]').val();
    if (oldpassword.length > 0 && password.length > 0 && password2.length > 0) {
        if (password != password2) {
            toast.show("两次密码输入不一致");
            return false;
        }
        ajax({
            oldpassword: oldpassword,
            newpassword: password
        }, "web/api/customer/updatePassword", function (json) {
            $.cookie('user',null,{path:"/"});
            //	$.cookie('COOKIE_CUSTOMER_MOBILE',null,{path:"/"});
            $.cookie('COOKIE_CUSTOMER_PASSWORD',null,{path:"/"});
            $.cookie('openid',null,{path:"/"});
            send('login.html');
        });
    }
}

function showBtn() {
    var oldpassword = $('input[name="oldpassword"]').val();
    var password = $('input[name="password"]').val();
    var password2 = $('input[name="password2"]').val();
    if (oldpassword.length > 0 && password.length > 0 && password2.length > 0) {
        $('#c_btn').css('background-color', '#ff6861');
    } else {
        $('#c_btn').css('background-color', '#aaa');
    }
}