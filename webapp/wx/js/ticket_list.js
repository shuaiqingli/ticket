var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
var startdate = new Date();
var currdate  = new Date(wx_date.formatDate(new Date(),'/'));
var isLoadCitys = false;
var ordertype = null;
$(function(){
	$.cookie('coupon',null);
	 $.cookie('link',null)
	 $.cookie('ticket_num',null)
	 $.cookie('cnum',null)
	 $.cookie('pnum',null)
	 $.cookie('stid',null,{path:'/'});
	 $.cookie('not_use_coupon','false');
	try {
		if(isempty($.cookie('start_date'))){
			$.cookie('start_date',wx_date.formatDate(new Date()));
		}
		$('.start_date').html($.cookie('start_date'));
		var date = new Date($.cookie('start_date').replaceAll('-','/'));
		startdate = date;
		$('.week').text(weeks[date.getDay()]);
	} catch (e) {
		console.debug(e);
	}
	ticketlist(1, 10, $.cookie('start_date'));
	
	//前一天
	$('.previous').click(function(){
//		if(islock){
//			return;
//		}
		var temp = startdate.getTime()-1000*60*60*24;
		if(temp<currdate.getTime()){
			toast.show('亲，选择日期已经过时！');
			$(this).css('color','#bfbfbf');
			return;
		}else{
			startdate = new Date(temp);
			$('.start_date').html(wx_date.formatDate(startdate));
			$(this).css('color','#000');
			$('.next').css('color','#000');
			$.cookie('start_date',wx_date.formatDate(startdate));
			$('.week').text(weeks[startdate.getDay()]);
			page = 1;
			ticketlist(1,10,true);
		}
	});
	//后一天
	$('.next').click(function(){
//		if(islock){
//			return;
//		}
		var temp = startdate.getTime()+1000*60*60*24;
		if(temp>currdate.getTime()+1000*60*60*24*14){
			toast.show('亲，已经是最后一天了！');
			$(this).css('color','#bfbfbf');
			return;
		}else{
			startdate = new Date(temp);
			$('.previous').css('color','#000');
			$('.start_date').html(wx_date.formatDate(startdate));
			$('.week').text(weeks[startdate.getDay()]);
			$.cookie('start_date',wx_date.formatDate(startdate));
			page = 1;
			ticketlist(1, 10,true);
		}
//		alert(page)
	});
	
	$('.filter_title,.fil_btn').click(function(){
//		if(islock){
//			return;
//		}
		/*alert("!!!");*/
		$('#choseFilter').hide();
		page = 1;
		ticketlist(1, 10,true);
		$('.t_list').attr('style','');
	});
	
	$('.f_left').click(function(){
		$('.t_list').attr('style','');
		$('#choseFilter').hide();
	});
	
	var begin = JSON.parse($.cookie('begin_city'))
	var end = JSON.parse($.cookie('end_city'))
	
	$('title').text(begin.cityname+'-'+end.cityname);
	
//	$('#filterBtn').click(function(){
//		if(isLoadCitys==true){
//			return;
//		}
		ajax({begincityid:begin.id,endcityid:end.id}, "public/api/getStationByCityId", function(json){
			appendStation($('.begin_city_div'),json.datas[0],0);
			appendStation($('.end_city_div'),json.datas[1],1);
		},false);
//		ajax({begincityid:end.id,endcityid:begin.id}, "public/api/getStationByCityId", function(json){
//			appendStation($('.end_city_div'),json.datas,1);
//		})
//		isLoadCitys = true;
//	});
	
	$('.li').find('li').click(function(){
		var input = $(this).find('input');
		if(input.prop('checked')){
			input.prop('checked',false);
			$(this).parent().find('li').not($(this)).show();
		}else{
			input.prop('checked',true);
			$(this).parent().find('li').not($(this)).hide();
		}
	});
	
	$('.menu_time').click(function(){
//		if(islock){
//			return;
//		}
		if($(this).data('type')==1){
			$(this).data('type',0);
			page = 1;
			ticketlist(1, 10,true);
			$(this).attr('type',0)
			$(this).css('color','#7c7c7c');
			$(this).find('img').attr('src','images/down_grey.png');
			return;
		}
		ordertype = 'time';
		var type = $(this).attr('type');
		if(type=='0'){
			$(this).attr('type',1)
			$(this).css('color','#2eb69a');
			$(this).find('img').attr('src','images/up_green.png');
			$('.menu_price').css('color','#7c7c7c');
			$('.menu_price').find("img").attr('src','images/up_grey.png');
			$('.menu_price').attr('type',0);
		}else{
			$(this).attr('type',0)
			$(this).css('color','#2eb69a');
			$(this).find('img').attr('src','images/down_green.png');
			$(this).data('type',1);
		}
		page = 1;
		ticketlist(1, 10,true);
	});
	$('.menu_price').click(function(){
//		if(islock){
//			return;
//		}
		ordertype = 'price';
		var type = $(this).attr('type');
		if($(this).data('type')==1){
			$(this).data('type',0);
			page = 1;
			ticketlist(1, 10,true);
			$(this).attr('type',0)
			$(this).css('color','#7c7c7c');
			$(this).find('img').attr('src','images/down_grey.png');
			return;
		}
		if(type=='0'){
			$(this).attr('type',1)
			$(this).css('color','#2eb69a');
			$(this).find('img').attr('src','images/up_green.png');
			$('.menu_time').find("img").attr('src','images/down_grey.png');
			$('.menu_time').css('color','#7c7c7c');
			$('.menu_time').attr('type',0);
		}else{
			$(this).attr('type',0)
			$(this).css('color','#2eb69a');
			$(this).find('img').attr('src','images/down_green.png');
			$(this).data('type',1);
		}
		page = 1;
		ticketlist(1, 10,true);
	});
	$('#ullist').click(function(){
		/*alert("!!!!");*/
	});
	$(window).scroll(function(){
		if(isLast){
			return;
		}
		var scrollTop = $(this).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = $(this).height();
		if(scrollTop + windowHeight >= scrollHeight-3){
//			   page++;
		ticketlist(page, 10, false);
		}
	});
	
	$('.ticketline').click(function(){
		var json = $(this).data('ticketline');
		var user  = getuser();
		if(isempty(user)){
			logout();
		}else{
			if(user.rank==2){
				json.stationcouponquantity = 0;
				json.balancecouponquantity = 0;
			}
		}
		if(json.ticketprice<=json.favprice){
			json.stationcouponquantity = 0;
			json.balancecouponquantity = 0;
		}
		$.cookie('ticket_detail',JSON.stringify(json));
		if(json.balancequantity==0||json.stationquantity==0){
			toast.show('余票不足，请选择其他班次！');
			return;
		}
		send('ticket_detail.html');
	});
	
	$('.carmodelid').error(function(){
		$(this).hide();
	});
	
});

function ticketlist(pageNo,pageSize,isremove){
//	if(islock){
//		return;
//	}
	var params = {};
	params.citystartid  = $.cookie('startcityid');
	params.cityarriveid =  $.cookie('arrivecityid');
	params.ticketdate = $.cookie('start_date');
	if(isempty(params.citystartid)||isempty(params.cityarriveid)){
		toast.show('请选择出发城市和到达城市');
		setTimeout(function(){send('index.html')}, 1500);
		return;
	}
	
	params.ticketprice = $('.menu_price').attr('type');
	params.time = $('.menu_time').attr('type');
	params.pageNo = pageNo;
	params.pageSize = pageSize;
	
	if(ordertype=='price'){
		delete params.time;
	}else if(ordertype=='time'){
		delete params.ticketprice;
	}else{
		delete params.time;
		delete params.ticketprice;
	}
	
	var time = $('[name=time]:checked').val();
	var start = $('[name=start]:checked').val();
	var end = $('[name=end]:checked').val();
//	alert(time)
	if(!isempty(time)){
		params.starttime = time.split('-')[0];
	}
	if(!isempty(time)){
		params.arrivetime = time.split('-')[1];
	}
//	alert(JSON.stringify(params));
	if(start!=''){
		params.ststartid  = start;
	}
	if(end!=''){
		params.starriveid =  end;
	}
	if(isremove){
		$('.ticketline').not($('.ticketline').eq(0)).remove();
	}
	setTimeStamp(params);
	ajax(params, "public/api/getTicketLineList", function(json){
		console.debug("======>>>>"+checkTimeStamp(json.page));
		if(checkTimeStamp(json.page)==false){
			return;
		}
		if(json.datas.length==0&&json.page.pageNo==1){
			notdata(json);
		}
		
//		console.debug(json);
		jeach(json.datas,function(k,v){
			var o = $('.ticketline').eq(0).clone(true);
			var params = {};
			params.$ = o;
			var p = v;
			jeach(v,function(k,v){
				if(k=='ticketprice'){
					if((v==p.favprice||v<p.favprice)||(p.balancecouponquantity<=0||p.stationcouponquantity<=0)){
						o.find('.ticketprice').parent().hide();
						o.find('.goodprice').hide().text('价格');
						return {$:o,val:p.ticketprice};
					}
				}
				else if(k=='stationquantity'&&(p.balancequantity<=v)){
//					&&(p.balancequantity<=v)
					return {$:o,val:p.balancequantity};
				}else if(k=='carmodelid'){
					return {$:o,val:'images/car_'+v+'.png'};
				}else if(k=='stationquantity'){
					if(isempty(p.balancequantity)||isempty(p.stationquantity)||p.balancequantity==0||p.stationquantity==0){
						o.find('.stationquantity').parents('.t_fame').html('已售罄');
						o.find('.goodprice').hide();
						o.find('.ticketprice').parent().hide();
						o.find('.favprice').parent().css('color','#5c5c5c');
						
					}
				}
				return params;
			});
			if(p.balancecouponquantity<=0||p.stationcouponquantity<=0){
				o.find('.ticketprice').parent().hide();
				o.find('.goodprice').hide().text('价格');
				o.find('.favprice').text(p.ticketprice);
			}
			o.find('.carmodelid').hide();
			$('.ticketline').last().after(o.show());
			o.find('.json').text(JSON.stringify(v));
			o.data('ticketline',v);
		});
	},false);
}

function appendStation(parent,list,type){
	jeach(list,function(k,v){
		var station = $('.station').eq(0).clone(true).show();
		if(type==0){
			station.find('input').attr('name','start')
			station.addClass("startcity");
		}else{
			station.find('input').attr('name','end')
			station.addClass("endtcity");
		}
		var params = {};
		params.$ = station;
		jeach(v,function(k,v){
			return params;
		});
		parent.append(station);
	});
}