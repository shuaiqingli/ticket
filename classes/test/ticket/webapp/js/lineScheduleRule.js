$(function () {

    starttime($(".starttime").not($(".scheduleDetail").eq(0).find('.starttime')));
    starttime($(".endtime"));

    $('.btn_adddetail').bind('click', function () {
        if ($('.starttime').length == 100) {
            layer.msg('添加班次已经到上限！');
            return;
        }
        var p = $(this).parents('.scheduleDetail');
        var subdetail = $(".scheduleDetail").eq(0).clone(true).show();
        subdetail.find('.starttime').val(p.find('.starttime').val());
        p.after(subdetail);
        updateShiftCode(); 
        starttime(subdetail.find('.starttime'));
    });

    $('.btn_deldetail').click(function () {
        var p = $(this).parents('.scheduleDetail');
        var id = p.find('.id').val();
        var msg = "您确定要删除该班次码？";
        layer.confirm(msg,{offset: '170px'}, function () {
            if (id == null || id == '') {
                p.remove();
            } else {
                p.hide();
                p.find('.isdel').val(1);
            }
            updateShiftCode();
        })
    });

    $('.btn_changestarttime').click(function(){
        var intevalminute = $(this).parent().find('.intevalminute').val();
        var cks = $('.scheduleDetail').find('input:checked:visible');
        if(cks.length==0){
            layer.msg('请选择班次后操作！');
            return;
        }
        if(intevalminute==''||intevalminute==null||isNaN(intevalminute)){
            layer.msg('请输入正确的间隔时间！');
            return;
        }
        updateIntevalminuteTime(cks,intevalminute);
    });

    $('.chooseAllShif').bind('click',function(){
        var isck = $(this).prop('checked');
        $('.updatetime').prop('checked',isck);
    });

    $('.create_shift').click(function(){
        var firsttime = $('.first_time').val();
        var shiftnum = $('[name=shiftnum]').val();
        var time = $('[name=intevalminute]').val();
        if(!firsttime){
            layer.msg('始发时间不能为空！');
            return;
        }
        if(!shiftnum||isNaN(shiftnum)){
            layer.msg('班次数量填写不正确！');
            return;
        }
        if(!time||isNaN(time)){
            layer.msg('间隔时间填写不正确！');
            return;
        }
        $('.scheduleDetail:visible').remove();
        for(var i = 0;i< parseInt(shiftnum);i++){
            var detail = $('.scheduleDetail').first().clone(true).show();
            starttime(detail.find('.starttime'));
            $('.scheduleDetail').last().after(detail);
        }
        $('.scheduleDetail:visible').first().find('.starttime').val(firsttime);
        var cks = $('.scheduleDetail').find('[type=checkbox]:visible');
        updateShiftCode();
        updateIntevalminuteTime(cks,time);
    });


});

function updateIntevalminuteTime(checkboxs,intevalminute){
    var time = 0;
    $.each(checkboxs,function(i,v){
        var p = $(v).parents('.scheduleDetail');
        if(i>=1){
            var temptime = time+ intevalminute*1000*60*i;
            var currdate = new Date('2015/01/01');
            var sdate = new Date(temptime+currdate.getTime());
            var hour = sdate.getHours();
            var minute = sdate.getMinutes();
            if(hour<=9){
                hour = '0'+hour;
            }
            if(minute<=9){
                minute = '0'+minute;
            }
            var st = hour+":"+minute;
            p.find('.starttime').val(st);
        }else if(i==0){
            var val = p.find('.starttime').val();
            var arr = val.split(':')
            if(val==''||arr.length>2){
                layer.msg('数据错误！');
            }else{
                time += parseInt(arr[0])*1000*60*60;
                time += parseInt(arr[1])*1000*60;
            }
        }
    })
}


function updateShiftCode() {
    var items = $(".scheduleDetail:visible");
    var pre = $lineid;
    var index = 1;
    $.each(items, function (i, v) {
        //if (i == 0) {
        //    pre = $(v).find('.shiftcode').text().trim().substring(0, 3);
        //}
        var suffix = i;
        if (index < 10) {
            suffix = '0' + index;
        } else {
            suffix = index;
        }
        index++;
        $(v).find('.shiftcode').text(pre + suffix)
    });
}


function starttime(e) {
    e.timepicker({
        showMeridian: false,
        showInputs: true,
        defaultTime: false,
        format: 'H:i',
        minuteStep: 1
    });
}

function changeTimeShifNum(t){
    var lasttime = $('.last_time').val();
    var firsttime = $('.first_time').val();

    firsttime = parseInt(firsttime.split(':')[0])*60+parseInt(firsttime.split(':')[1]);
    lasttime = parseInt(lasttime.split(':')[0])*60+parseInt(lasttime.split(':')[1]);

    var temp = lasttime - firsttime;

    if($(t).is('[name=intevalminute]')){
        $('[name=shiftnum]').val('');
        if($(t).val()==''){
            if($('[name=shiftnum]').val()){
            }
            return;
        }else{
            CalculationShiftNum(t,lasttime,firsttime,temp);
        }
    }else if($(t).is('[name=shiftnum]')){
        $('[name=intevalminute]').val('');
        if($(t).val()==''){
            return;
        }else{
            CalculationTime(t,lasttime,firsttime,temp);
        }
    }
}

function CalculationTime(t,lasttime,firsttime,temp){
    var val = $('[name=shiftnum]').val();
    if($(t).val()==''&&val==''){
        return;
    }
    var time = parseInt($(t).val() == '' ? val:$(t).val());
    if(isNaN(lasttime)||isNaN(firsttime)||lasttime<=firsttime){
        layer.msg('结束时间不合法，不能自动计算班次数量！');
        return;
    }else{
        if($('[name=intevalminute]').val()==''){
            var temp = parseInt(temp%time==0?temp/time:temp/time+1);
            if(isNaN(temp)){
                return;
            }
            $('[name=intevalminute]').val(temp);
        }
    }
}
function CalculationShiftNum(t,lasttime,firsttime,temp){
    var val = $('[name=intevalminute]').val();
    if($(t).val()==''&&val==''){
        return;
    }
    var num =parseInt($(t).val() == '' ? val:$(t).val());
    if($('[name=shiftnum]').val()==''){
        if(isNaN(lasttime)||isNaN(firsttime)||lasttime<=firsttime){
            layer.msg('结束时间不合法，不能自动计算间隔时间！');
            return;
        }else{
            var temp = parseInt(temp%num==0?parseInt(temp/num):parseInt(temp/num)+1);
            if(isNaN(temp)){
                return;
            }
            if(temp>99){
                //layer.msg("班次数量不能超过99！")
                temp = 99;
            }
            $('[name=shiftnum]').val(temp);
        }
    }
}