$(function(){
	
	appendorderlist(1,10,false);
	
	$(window).scroll(function(){
		if(isLast){
			return;
		}
		var scrollTop = $(this).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = $(this).height();
		if(scrollTop + windowHeight >= scrollHeight-3){
			 appendorderlist(page, 10,false);
		}
	});
	
	$('.order').click(function(){
		$.cookie('orderid',$(this).find('.id').text().trim());
		send('order_detail.html');
	});
	
});

function appendorderlist(pageNo,pageSize,isremove){
	if(isremove){
		$('.order').not($('.order').eq(0)).remove();
	}
	ajax({pageNo:pageNo,pageSize:pageSize},"web/api/saleorder/orderlist",function(json){
		var list = json.datas;
		notdata(json);
		jeach(list, function(k,v){
			var o = $('.order').eq(0).clone(true).show();
			var p = v;
			if(v.paystatus==0){
				var bg = "#fff url(images/status"+v.status+".png) no-repeat 100% 3%";
			}else{
				var bg = "#fff url(images/status"+v.status+".png) no-repeat 100% 3%";
			}
			o.css('background',bg);
			jeach(v,function(k,v){
				var params = {};
				params.$ = o;
				if(k=="ststartname"){
					params.val = p.linename.split('-')[0]+v;
				}else if(k=="starrivename"){
					params.val = p.linename.split('-')[1]+v;
				}
				return params;
			});
			$('.order').last().after(o);
		});
	});
}
