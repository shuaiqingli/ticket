$(function () {
    window.ue = UE.getEditor('contentEditor');
    ue.ready(function () {
        ue.setContent($('input[name="amcontent"]').val());
    });

    $('input[name="begindate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2010-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        activityMsgForm.check(false, $('input[name="begindate"]'));
        $('input[name="enddate"]').datetimepicker('setStartDate', ev.date);
    });
    $('input[name="enddate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2010-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        activityMsgForm.check(false, $('input[name="enddate"]'));
        $('input[name="begindate"]').datetimepicker('setEndDate', ev.date);
    });

    $("#ampictureurl").uploadify({
        height: 27,
        width: 80,
        buttonText: '选择图片',
        swf: basePath + '/common/js/uploadify.swf',
        uploader: basePath + '/user/imageFileUpload.do',
        auto: true,
        multi: false,
        fileTypeExts: '*.jpg;*.jpeg;*.png;*.gif;*.bmp',
        fileTypeDesc: '仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB',
        fileSizeLimit: '5MB',
        uploadLimit: 1,
        onUploadSuccess: function (file, data, response) {
            var uploadLimit = $("#ampictureurl").data('uploadify').settings.uploadLimit;
            $('#ampictureurl').uploadify('settings', 'uploadLimit', ++uploadLimit);
            if (response == true) {
                if (typeof(data) === 'string') {
                    $('#ampictureurlPreview').html('<img style="height:150px;" src="' + data + '">');
                    $('input[name="ampictureurl"]').val(data);
                    return;
                }
            }
            layer.msg('上传失败');
        },
        onUploadError: function (file, data, response) {
            var uploadLimit = $("#ampictureurl").data('uploadify').settings.uploadLimit;
            $('#ampictureurl').uploadify('settings', 'uploadLimit', ++uploadLimit);
            layer.msg('上传失败');
        }
    });

    window.activityMsgForm = $('#activityMsgForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
            $('input[name="amcontent"]').val(window.ue.getContent());
        },
        beforeSubmit: function () {
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/user/activityMsgList.do';
            } else {
                layer.msg('操作失败');
            }
        }
    });
});

function selectAMSort() {
    var amsort = $('select[name="amsort"]').find('option:selected').val();
    if (typeof(amsort) == 'string' && amsort == '1') {
        $('input[name="ampictureurl"]').parents('div.row').hide();
        $('input[name="amcontent"]').parents('div.row').hide();
    } else if (typeof(amsort) == 'string' && amsort == '2') {
        $('input[name="ampictureurl"]').parents('div.row').hide();
        $('input[name="amcontent"]').parents('div.row').hide();
    } else {
        $('input[name="ampictureurl"]').parents('div.row').show();
        $('input[name="amcontent"]').parents('div.row').show();
    }
}