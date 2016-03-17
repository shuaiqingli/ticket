$(function () {
    window.ue = UE.getEditor('descEditor');
    ue.ready(function() {
        ue.setContent($('input[name="desc"]').val());
    });

    $('input[name="startdate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        integralProductForm.check(false, $('input[name="startdate"]'));
        $('input[name="enddate"]').datetimepicker('setStartDate', ev.date);
    });
    $('input[name="enddate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: new Date(),
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        integralProductForm.check(false, $('input[name="enddate"]'));
        $('input[name="startdate"]').datetimepicker('setEndDate', ev.date);
    });

    $("#iconurl").uploadify({
        height : 27,
        width : 80,
        buttonText : '选择图片',
        swf : basePath + '/common/js/uploadify.swf',
        uploader : basePath + '/user/imageFileUpload.do',
        auto : true,
        multi : false,
        fileTypeExts : '*.jpg;*.jpeg;*.png;*.gif;*.bmp',
        fileTypeDesc : '仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB',
        fileSizeLimit : '5MB',
        uploadLimit : 1,
        onUploadSuccess : function(file,data,response){
            var uploadLimit = $("#iconurl").data('uploadify').settings.uploadLimit;
            $('#iconurl').uploadify('settings','uploadLimit', ++uploadLimit);
            if(response==true){
                if(typeof(data) === 'string'){
                    $('#iconurlPreview').html('<img style="height:150px;" src="'+data+'">');
                    $('input[name="iconurl"]').val(data);
                    return;
                }
            }
            layer.msg('上传失败');
        },
        onUploadError : function(file,data,response){
            var uploadLimit = $("#iconurl").data('uploadify').settings.uploadLimit;
            $('#iconurl').uploadify('settings','uploadLimit', ++uploadLimit);
            layer.msg('上传失败');
        }
    });

    $("#mainurl").uploadify({
        height : 27,
        width : 80,
        buttonText : '选择图片',
        swf : basePath + '/common/js/uploadify.swf',
        uploader : basePath + '/user/imageFileUpload.do',
        auto : true,
        multi : false,
        fileTypeExts : '*.jpg;*.jpeg;*.png;*.gif;*.bmp',
        fileTypeDesc : '仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB',
        fileSizeLimit : '5MB',
        uploadLimit : 1,
        onUploadSuccess : function(file,data,response){
            var uploadLimit = $("#mainurl").data('uploadify').settings.uploadLimit;
            $('#mainurl').uploadify('settings','uploadLimit', ++uploadLimit);
            if(response==true){
                if(typeof(data) === 'string'){
                    $('#mainurlPreview').html('<img style="height:150px;" src="'+data+'">');
                    $('input[name="mainurl"]').val(data);
                    return;
                }
            }
            layer.msg('上传失败');
        },
        onUploadError : function(file,data,response){
            var uploadLimit = $("#mainurl").data('uploadify').settings.uploadLimit;
            $('#mainurl').uploadify('settings','uploadLimit', ++uploadLimit);
            layer.msg('上传失败');
        }
    });

    window.integralProductForm = $('#integralProductForm').Validform({
        tiptype: 4,
        postonce: true,
        ajaxPost: true,
        ignoreHidden: true,
        beforeCheck: function () {
            $('input[name="desc"]').val(window.ue.getContent());
        },
        beforeSubmit: function () {
        },
        callback: function (data) {
            if (typeof(data) == 'number' && data == 1) {
                location.href = basePath + '/admin/integralProductList.do';
            } else {
                layer.msg('操作失败');
            }
        }
    });
});

function showStock(){
    var stockflag = $('select[name="stockflag"]').find('option:selected').val();
    if(typeof(stockflag) == 'string' && stockflag == '1'){
        $('input[name="fixedstock"]').parents('div.row').show();
    }else{
        $('input[name="fixedstock"]').parents('div.row').hide();
    }
}