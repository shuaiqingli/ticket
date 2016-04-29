$(function () {
    $('input[name="startDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        $('input[name="endDate"]').datetimepicker('setStartDate', ev.date);
    });
    $('input[name="endDate"]').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayHighlight: true,
        forceParse: true,
        minView: 2,
        startDate: '2000-00-00',
        endDate: '2030-00-00'
    }).on('changeDate', function (ev) {
        $('input[name="startDate"]').datetimepicker('setEndDate', ev.date);
    });
});

function pointRank(cid, rank) {
    if (typeof  cid == 'string' && cid.length > 0 && typeof  rank == 'string' && rank.length > 0) {
        $('#rankDialog').find('input[name="cid"]').val(cid);
        $('#rankDialog').find('select[name="rank"]').val(rank);
        layer.open({
            type: 1,
            title: '指定级别',
            shadeClose: true,
            shade: 0.8,
            area: ['500px', 'auto'],
            content: $('#rankDialog'),
            yes: function () {
            }
        });
    }
}

function updateCustomerRank() {
    var cid = $('#rankDialog').find('input[name="cid"]').val();
    var rank = $('#rankDialog').find('select[name="rank"]').val();
    $.ajax({
        type: 'POST',
        url: basePath + '/admin/customerRankEdit.do',
        data: {cid: cid, rank: rank},
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
}

function pointAdmin(cid) {
    window.cid = cid;
    layer.open({
        type: 2,
        title: '选取管理员',
        shadeClose: true,
        shade: 0.8,
        area: ['800px', 'auto'],
        content: basePath + '/admin/findAdminListForBindCustomer.do'
    });
}

function selectAdmin(userid) {
    $.ajax({
        type: 'POST',
        url: basePath + '/admin/bindAdminToCustomer.do',
        data: {cid: cid, userid: userid},
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
}

function exportExcel(obj){
    $(obj).parents('form').attr('action',basePath+"/admin/customerListExport.do");
    $(obj).parents('form').submit();
    $(obj).parents('form').attr('action',basePath+"/admin/customerList.do");
}