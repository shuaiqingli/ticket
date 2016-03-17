$(function () {

});

function resetPassword(id) {
    layer.open({
        type: 1,
        title: '重置密码',
        btn: ['确定', '取消'],
        shadeClose: true,
        shade: 0.8,
        area: ['300px', 'auto'],
        content: $('.resetPwd'),
        yes: function (index) {
            var newpassword = $("#newpassword").val();
            var affirmpassword = $("#affrimpassword").val();
            if (newpassword != affirmpassword) {
                layer.msg('两次密码输入不一致');
                return false;
            }
            $.ajax({
                type: "post",
                url: basePath + "/user/updateDriverPassword.do",
                data: {driverid: id, password: newpassword},
                dataType: 'json',
                success: function (data) {
                    if (typeof(data) == 'number' && data == 1) {
                        layer.msg('操作成功');
                    } else {
                        layer.msg('操作失败');
                    }
                },
                error: function () {
                    layer.msg('操作失败');
                }
            });
        }
    });
}