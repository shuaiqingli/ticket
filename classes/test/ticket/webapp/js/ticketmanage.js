var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
var date = new Date();


$(function () {

   $('.btn_ticket').click(function(){

       var $iframe = null;
       layer.open({
           type: 2,
           title: '出票',
           btn: ['确定', '取消'],
           shadeClose: true,
           shade: 0.8,
           offset:['10%'],
           move:1,
           area: ['1090px', '80%'],
           content: basePath + '/user/getTicketTask?lmid='+$lmid+'&begindate='+$('._date').text().trim(),
           success: function (layero, index) {
               $iframe = layero.find('iframe');
           },
           yes: function () {
               var ckboxs = $iframe.contents().find('.live');
               var params = {};
               params.lmid = $lmid;
               var prefix = 'tasks';
               var count = 0;
               ckboxs.each(function (i, v) {
                   var live = $(v);
                   var tplid = live.find('.tplid').val();
                   var promotionid = live.find('.promotionid').val();
                   params[prefix + '[' + i + '].tplid'] = tplid;
                   params[prefix + '[' + i + '].promotionid'] = promotionid;
                   params[prefix + '[' + i + '].date'] = live.find('.date').text().trim();
                   params[prefix + '[' + i + '].isrelease'] = live.find('.isrelease').prop('checked')?1:0;
               });
               console.debug(params);
               ajax(params, basePath + "/user/ticketTask", function () {
                   location.reload();
               })
               throw "";
           }
       });
   });

    $('.btn_release').click(function () {
        var $this = $(this);
        layer.open({
            type: 1,
            title: '发布车票',
            btn: ['确定', '取消'],
            shadeClose: true,
            shade: 0.8,
            offset: ['25%'],
            move: 0,
            zIndex:0,
            area: ['500px', '200px'],
            content: $('.release'),
            success: function (layero, index) {
                $('.begindate,.enddate').val($('._date').text().trim());
            },
            yes: function () {
                var params = {};
                params.lmid = $lmid;
                params.begindate = $('.begindate').val();
                params.enddate = $('.enddate').val();
                ajax(params,basePath+"/user/updateTicketStoreRelease", function () {
                    location.reload();
                })
                throw "";
            }
        });
    });

    $('.btn_update_release').click(function () {
        var $this = $(this);
        layer.open({
            type: 1,
            title: '修改发布状态',
            btn: ['确定', '取消'],
            shadeClose: true,
            shade: 0.8,
            offset: ['25%'],
            move: 0,
            zIndex:101,
            area: ['500px', '200px'],
            content: $('.release'),
            success: function (layero, index) {
                $('.begindate,.enddate').val($('._date').text().trim());
            },
            yes: function () {
                var params = {};
                params.lmid = $lmid;
                params.begindate = $('.begindate').val();
                params.enddate = $('.enddate').val();
                params.shiftcode = $this.parents('tr').find('.shiftcode').text().trim();
                params.status = $this.attr('status');
                ajax(params,basePath+"/user/updateReleaseStatus", function () {
                    location.reload();
                })
                throw "";
            }
        });
    });

});


//$('._date').datetimepicker({
//    language:  'zh-CN',
//    format:'yyyy-mm-dd',
//    weekStart: 1,
//    todayBtn:  1,
//	autoclose: 1,
//	todayHighlight: 1,
//	startView: 2,
//	minView: 2,
//	forceParse: 0,
//});

$(function(){

    $('._date,.begindate,.enddate').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        //startDate:new Date(),
        startView: 2,
        minView: 2,
        forceParse: 0
    });

    $('._date').datetimepicker().on('changeDate', function(ev){
        var datestr = ev.date.toISOString().split("T")[0].toString();
        datestr = datestr.replace("-","/").replace("-","/").replace("-","/");
        ev.date = new Date(datestr);
        changeDate(ev.date,true);
    });

    $('._date,.begindate,.enddate').datetimepicker().on('changeMonth', function(ev){
        getdatas(ev);
    });

    $('._date,.begindate,.enddate').datetimepicker().on('next:month', function(ev){
        getdatas(ev);
    });

    $('._date,.begindate,.enddate').datetimepicker().on('prev:month', function(ev){
        getdatas(ev);
    });
    $('._date,.begindate,.enddate').datetimepicker().on('next:year', function(ev){
        getdatas(ev);
    });

    $('._date,.begindate,.enddate').datetimepicker().on('prev:year', function(ev){
        getdatas(ev);

    });

    $('._date,.begindate,.enddate').on('show',function(ev){
        getdatas(ev);
    });

})



function changeDate(d,isreload){
	$('._date').text(formatDate(d));
	$('.weekday').text(weeks[d.getDay()]);
    if(isreload){
    	ticketmanagelist();
    }
}



function changDate(){
	if($('.begindate').val()==''||$('.enddate').val()==''){
		$('.begindate,.enddate').val($('._date').text());
	}
	var day = differDay($('.begindate').val(),$('.enddate').val());
	if(day<0){
		layer.msg('结束日期不能小于开始日期！')
		$('.approveday').text(0)
		$('.shiftnum').text(0)
		return;
	}
	$('.approveday').text(day+1)
	day = day+1;
	$('.approveday').text(day);
	$('.shiftnum').text(day*shiftnum);
}



//获取审核日期
function getdatas(ev){
	var params = getMonthFirstLastDate(ev.date);
	params.lmid = $lmid;
	ajax(params, basePath+"/user/findApproveReleaseDates",function(json){
		approvedhanlder(ev, json, 
		function(i,v){
			return v.ticketdate;
		}, 
		function(e,o){
			 if(o.tplid==undefined){
				e.css('color','red')
			}else if(o.isrelease){
                e.css('color','orange')
            }
		});
	});
}

$(function(){
	if($date!=''){
		try{
			var pdate = new Date($date.replace(/-/g,"/"))
			changeDate(pdate,false);
		}catch(e){
			changeDate(date,false);
		}
	}else{
		changeDate(date,false);
	}
});


var shiftnum = 0;
function ticketmanagelist(date){
    location.href = basePath + "/user/ticketmanage?lmid="+$lmid+"&ticketdate="+$('._date').text().trim();
}



$(function(){

	//设置自动发布日期
	$('.btn_setReleaseDay').click(function(){
		$('.releaseday').val($(this).attr('day'));
	});

	$('.updateReleaseDay').click(function(){
		var releaseday = $('.releaseday').val();
		if(releaseday==''||isNaN(releaseday)){
			layer.msg('请输入正确的发布天数！')
			return;
		}else if(parseInt(releaseday)>30){
			layer.msg('填写的发布天数不正确！')
			return;
		}
		var params = {};
		params.releaseday = releaseday;
		params.id = $lmid;
		ajax(params, basePath+"/user/updateLineManange",function(json){
			if(json==1){
				layer.msg('操作成功！');
				location.href = basePath+"/user/ticketmanage?lmid="+$lmid+"&date="+$('._date').text();
			}else{
				layer.alert('操作失败！')
			}
		});
	});

	$('.updateShift').click(function(){
        return;
		var $this = $(this);
		layer.confirm('你确定'+$(this).text()+'吗？', {offset: '300px'}, function(){
			var params = $this.data('params');
			ajax(params, basePath+"/user/cancelTicketStoreShift", function(r){
				if(r>=1){
					layer.msg('操作成功！');
//					location.reload();
					location.href = basePath+"/user/ticketmanage?lmid="+$lmid+"&date="+$('._date').text();
				}else{
					layer.msg('操作失败！');
				}
			});
		});
	});

	$(".odd1").children().not('.no').bind('click',function(){
		if($(this).parents('.odd1').find("i").hasClass("hided")){
			$(this).parents('.odd1').find("i").attr("class","showed");
			$(this).parents('.odd1').parent().parent().find(".odd2").show();
		}else{
			$(this).parents('.odd1').find("i").attr("class","hided");
			$(this).parents('.odd1').parent().parent().find(".odd2").hide();
		}
	});
	$('.imgnext').click(function(){
		var d = $('._date').text();
		if(d){
			d =  new Date(new Date(d).getTime()+1000*60*60*24);
			changeDate(d,true);
		}
	});
	$('.imgprev').click(function(){
		var d = $('._date').text();
		if(d){
			d =  new Date(new Date(d).getTime()-1000*60*60*24);
			changeDate(d,true);
		}
	});
	
	$("._date,.weekday").click(function(){
		$('._date').datetimepicker('show');
	});
})
