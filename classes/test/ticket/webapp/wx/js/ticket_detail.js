$(function(e){
	$('body').hide();
	$('.start_date').html(Client.get('start_date'));
	detail = $.cookie('ticket_detail');
    //console.debug(detail);
	if($.cookie('ticket_num')){
		$('.ticket_num').val($.cookie('ticket_num'));
		$('.total_ticket').text($.cookie('ticket_num'));
	}





//	if(detail.){
//		
//	}

//	var begin = JSON.parse($.cookie('begin_city'))
//	var end = JSON.parse($.cookie('end_city'))
//	
//	$('.line').text(begin.cityname+'-'+end.cityname);
//	islogin(function(result,json){
//		if(res){
//
//		}
//	});
//	if(islogin()==false){
//		$('.coupon_desc').text('(未登录)');
//	}else{
//		post("web/api/customer/coupons",function(json){
//			try {
//				if (json.datas.length != 0) {
//					$('.coupon_desc').show();
//				}else{
//					$('.coupon_desc').hide();
//				}
//			} catch (e) {
//				$('.coupon_desc').hide();
//			}
//		})
//	}

	if(detail!=null){
		detail = JSON.parse(detail);
		if(detail.stationcouponquantity>=1&&detail.balancecouponquantity>=1){
			$('.coupon_ticket').show();
			$('.ticket_price').hide();
		}else{
			$('.coupon_ticket').hide();
			$('.ticket_price').show();
		}
		jeach(detail, function(k,v){
			return true;
		});
		if(detail.favprice >= detail.ticketprice && detail.stationcouponquantity>=1 && detail.balancecouponquantity>=1){
			$('.coupon_ticket').eq(1).hide();
		}
		$.cookie('stid',detail.ststartid,{path:'/'});
		totalPrice();
		$('body').show();
		ajax({lmid:detail.lmid}, "public/api/getLineManageMemo",function(json){
			if(isempty(json.datas[0].memo)==false){
				$('.linemanagememo').text(json.datas[0].memo).show();
			}else{
				$('.linemanagememo').hide();
			}
		});
		$('body').show();
	}

    api.getLineRefundRemark(detail.lmid, function (json) {
        if(json.refundstatus==1){
            $('.refendremark').text(json.refundremark||'该票不可改退').show();
        }else{
            $('.refendremark').hide();
        }
    })

	$('.order_ticket').click(function(){
		$.cookie('location_href',location.href);
		$.cookie('ticket_num',$('.ticket_num').val());
		//islogin(function(result){
		//	if(result){
		//		send('order_infomation.html');
		//	}else{
		//		$('location_href',location.href);
		//		send('login.html');
		//	}
		//});
		//if(islogin()==false){
		//	send('login.html');
		//	return;
		//}
		send('order_infomation.html');
	});
	var start_st = $(".ststartname").text();
	var end_st = $(".starrivename").text();
	var start_date = $(".start_date").text();
	var starttime = $(".starttime").text();
	var price = $(".favprice").text();
	var lines = $(".linename").text();
	var items = lines.split("-");
	var startCity = items[0];
	var endCity = items[1];
	var pageData = {
		start_st:start_st,
		end_st:end_st,
		start_date:start_date,
		starttime:starttime,
		price:price,
		startCity:startCity,
		endCity:endCity
	}
	$(".start_st").text(start_st);
	$(".end_st").text(end_st);
	$(".endCity").text(endCity);
	$(".startCity").text(startCity);
	$(".st_date").text(start_date);
	$(".st_time").text(starttime);
	$(".st_price").text(price);
	if(price==null||isNaN(price)){
		price = 0;
	}else{
		price = parseFloat(price);
	}
	var total_num = $("#ti_num").val();
	var total_price = (total_num*price).toFixed(1);
	$("#total_price").text(total_price);
	if(total_num==5){
		$('#plus').attr('class','plus_a');
	}else{
		$('#plus').attr('class','plus');
	}
	if(total_num==1){
		$('#minus').attr('class','minus_a');
	}else{
		$('#minus').attr('class','minus');
	}
	if($.trim($('.coupon_ticket').css('display'))==='none'){
		$('.ticket_price').css('margin','0px 0px');
	}
	$(".buy_ticket a[id='plus']").bind('click',function(){
		if(total_num>4){
			$('#mainDiv').fadeIn(1000);
			setTimeout(1000);
			$("#mainDiv").fadeOut(800);
		}
		++total_num;
		$("#ti_num").val(total_num);
		if(total_num<=5){
			$('.total_ticket').text(total_num);
		}
		var total_price = (total_num*price).toFixed(1);
		$("#total_price").text(total_price);
		$('#minus').attr('class','minus');
		$('#plus').attr('class','plus');
		if(total_num>4){
			total_num=5;
			var total_price = (total_num*price).toFixed(1);
			$("#total_price").text(total_price);
			$("#ti_num").val(total_num);
			$('#plus').attr('class','plus_a');
			/* $('#mainDiv').show(100);
			 setTimeout(function(){
			 $('#mainDiv').hide("normal");
			 },800); */
			totalPrice();
			return;
		}
		totalPrice();
	});
	$(".buy_ticket a[id='minus']").bind('click',function(){
		--total_num;
		$("#ti_num").val(total_num);
		$('.total_ticket').text(total_num<1?1:total_num);
		var total_price = (total_num*price).toFixed(1);
		$("#total_price").text(total_price);
		$('#minus').attr('class','minus');
		$('#plus').attr('class','plus');
		if(total_num<2){
			total_num=1;
			var total_price = (total_num*price).toFixed(1);
			$("#total_price").text(total_price);
			$("#ti_num").val(total_num);
			$('#minus').attr('class','minus_a');
			totalPrice();
			return;
		}
		totalPrice();
	});

	$('.st_address').click(function(){
		var params = "?stationid="+detail.ststartid+'&stationid='+detail.starriveid;
		send('line_address.html'+params);
	});

});


function totalPrice(){
	var num = $('#ti_num').val();
	var cnum = 0;
	var tnum = 0;
	if(detail){
		var bcq = detail.balancecouponquantity||0;
		var scq = detail.stationcouponquantity||0;
//		scq = 0;
		if(bcq<scq){
			scq = bcq;
		}
		if(scq<num){
			cnum = scq;
		}else{
			cnum = num;
		}
		tnum = num-cnum;

		var totalprice = tnum*(detail.ticketprice||0);
		totalprice += cnum*(detail.favprice||0);

		$('.total_price').text(totalprice.toFixed(1));

		$.cookie('cnum',cnum);
		$.cookie('tnum',tnum);
		$.cookie('total',totalprice.toFixed(1));
//		console.debug(totalprice);

//		console.debug(cnum);
//		console.debug(tnum);
//		console.debug('***********************');
	}else{
		history.back();
	}
}
