var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
var date = new Date();


$('._date,.begindate,.enddate').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0,
});

$('._date').datetimepicker().on('changeDate', function(ev){
	var datestr = ev.date.toISOString().split("T")[0].toString();
	datestr = datestr.replace("-","/").replace("-","/").replace("-","/");
	ev.date = new Date(datestr);
	changeDate(ev.date);
});

function changeDate(d){
	$('._date').text(formatDate(d));
	$('.weekday').text(weeks[d.getDay()]);
	ticketmanagelist();
}


$('._date,.begindate,.enddate').datetimepicker().on('changeMonth', function(ev){
	getdatas(ev);
});

$('._date,.begindate,.enddate').datetimepicker().on('next:month', function(ev){
	getdatas(ev);
});


$('.begindate,.enddate').datetimepicker().on('changeDate', function(ev){
	changDate();
});

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
			if(o.isrelease){
				e.css('color','orange')
			}else if(o.isapprove==0){
				e.css('color','red')
			}
		});
	});
}

$(function(){
	if($date!=''){
		try{
			var pdate = new Date($date.replace(/-/g,"/"))
			changeDate(pdate);
		}catch(e){
			changeDate(date);
		}
	}else{
		changeDate(date);
	}
});


var shiftnum = 0;
function ticketmanagelist(date){
// 	return;
	$('.ticket').not($('.ticket').eq(0).hide()).remove();
	$('.isapprove').text('未审核').css('color','red');
	$('.isrelease').text('未发布').css('color','red');
	ajax({ticketDate:$('._date').text(),lmid:$lmid},basePath+"/user/tickemanagetlist",function(json){
		shiftnum = 0;
		if(json.length<=0){
			return;
		}
		shiftnum+=json.length;
		var isapprovecount = 0;
		jeach(json,function(k,v,i){
			
			if(i==0){
				if(v.isrelease==1){
					$('.isrelease').text('已发布').css('color','orange');
					$('.isapprove').text('已审核').css('color','green');
				}else{
					$('.isrelease').text('未发布').css('color','red')
				}
				if(v.isapprove==1||v.isrelease==1||v.isapprove==2){
					isapprovecount++;
				}else{
				}
			}
			
			
			var tllist = v.ticketlines;
			var ticket  = $('.ticket').eq(0).clone(true).show();
			var shift = ticket.find('.shift').eq(0);
			var params = {};
			params.id = v.id;
			if(v.isapprove==1){
				params.status = 2;
				shift.find('.updateShift').text('取消班次').data('params',params);
			}else if(v.isapprove==2){
				params.status = 1;
				shift.find('.updateShift').text('恢复班次').data('params',params);
			}else{
				shift.find('.updateShift').remove();
			}
			jeach(v,function(k,v){
				return {$:shift};
			});
			jeach(tllist,function(k,v,i){
				var index = i;
				if(i==0){
					shift.find('.starttime').text(v.originstarttime);
				}
				var detail = ticket.find('.shiftdetail').eq(0).clone(true);
				
				jeach(v,function(k,v,i){
					return {$:detail};
				})
				ticket.find('.shiftdetail').last().after(detail.hide());
			});
			var maxprice = 0;
			var minprice = 0;
			var p_maxprice = 0;
			var p_minprice = 0;
			var promotionprice = 0;
			jeach(tllist,function(k,v,i){
				if(i==0){
					maxprice = v.ticketprice;
					minprice = v.ticketprice;
					p_maxprice = v.favprice;
					p_minprice = v.favprice;
				}
				if(v.promotionprice != 0){
					promotionprice = v.promotionprice;
				}
				var ticketprice = v.ticketprice;
				var favprice = v.favprice;
				if(isNaN(ticketprice)==false&&ticketprice!=''){
					var ticketprice = parseInt(ticketprice);
					if(ticketprice<minprice){
						minprice = ticketprice;
					}
					if(ticketprice>maxprice){
						maxprice = ticketprice;
					}
				}
				if(isNaN(favprice)==false&&favprice!=''){
					if(favprice<p_minprice){
						p_minprice = favprice;
					}
					if(favprice>p_maxprice){
						p_maxprice = favprice;
					}
				}
			})
			shift.find('.maxprice').text(maxprice);
			shift.find('.minprice').text(minprice);
			shift.find('._maxprice').text(p_maxprice);
			shift.find('._minprice').text(p_minprice);
// 			shift.find('.promotionprice').text(promotionprice);
// 			console.debug(maxprice+' '+minprice)
			ticket.find('.shiftdetail').eq(0).remove();
			$('.ticket').last().after(ticket.show());
			if(isapprovecount==0){
				$('.isapprove').text('未审核').css('color','red');
			}else{
				$('.isapprove').text('已审核').css('color','green');
			}
		})
	});
	
	
	$('.batch_approve').click(function(){
		changDate();
	});
	
	$('.batch_approve_btn').click(function(){
		var ad = $('.approveday').text();
		var sn = $('.shiftnum').text();
		ad = parseInt(ad);
		if(ad<=0||isNaN(ad)){
			layer.msg('审核天数不能小于等于0 ！');
			return;
		}
		if(ad>60){
			layer.msg('审核天数必须是60天以内！');
			return;
		}
		sn = parseInt(sn);
		if(sn<=0||isNaN(sn)){
			layer.msg('没有可以审核的班次！');
			return;
		}
		var params = {};
		params.lmid = $lmid;
		params.begindate = $('.begindate').val();
		params.enddate = $('.enddate').val();
		//myModal release
		var release = $('#myModal').find('[name=release]');
		if(release.prop('checked')){
			params.isrelease = 1;
		}
		ajax(params, basePath+"/user/approveTicketStoreTicketLine",function(json){
			if(json==1){
				layer.msg('操作成功！');
// 				location.reload();
				location.href = basePath+"/user/ticketmanage?lmid="+$lmid+"&date="+params.begindate;
			}else{
				layer.alert('操作失败！')
			}
		});
	});
	
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
//		if(releaseday<=0){
//			params.releaseday = 1;
//		}
		params.id = $lmid;
		ajax(params, basePath+"/user/updateLineManange",function(json){
			if(json==1){
				layer.msg('操作成功！');
//				location.reload();
				location.href = basePath+"/user/ticketmanage?lmid="+$lmid+"&date="+$('._date').text();
			}else{
				layer.alert('操作失败！')
			}
		});
	});
	
	$('.updateShift').click(function(){
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
	
	//重置
	$('.btn_reset').bind('click',function(){
		var date = $('._date').text();
		layer.confirm('您确定要重置'+date+'的车票吗？重置只对于已审核未发布的车票有效！',{offset:['30%','40%']},function(){
			var params = {};
			params.ticketdate = date;
			params.lmid = $lmid;
			ajax(params, basePath+"/user/resetTicketStoreByDateLMID", function(r){
				if(r>=1){
					layer.msg('操作成功！');
					location.href = basePath+"/user/ticketmanage?lmid="+$lmid+"&date="+$('._date').text();
				}else{
					layer.msg('操作失败！');
				}
			});
		});
	});
	
}



$(function(){
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
			changeDate(d);
		}
	});
	$('.imgprev').click(function(){
		var d = $('._date').text();
		if(d){
			d =  new Date(new Date(d).getTime()-1000*60*60*24);
			changeDate(d);
		}
	});
	
	$(".weekday").click(function(){
		$('._date').datetimepicker('show');
	});
})
