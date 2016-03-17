$(function () {
    $('body').scrollTop($('body')[0].scrollHeight);

    window.feedbackForm = $('#feedbackForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
            if($('textarea[name="content"]').val().length == 0){
                layer.msg('回复内容不能为空');
                return false;
            }
        },
        beforeSubmit: function () {
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/admin/feedbackDetail.do?id=' + $('input[name="feedbackid"]').val();
            } else {
                layer.msg('操作失败');
            }
        }
    });
});

function del(id){
    layer.confirm('确认删除此回复?', {offset: '300px'}, function () {
        $.ajax({
            type: 'POST',
            url: basePath + '/admin/feedbackDetailDel.do',
            data: {id: id},
            dataType: "json",
            success: function (data) {
                if (typeof(data) == 'number' && data == 1) {
                    location.reload();
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