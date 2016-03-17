$(function(){
	$('body').hide();
	ajax({id:$.cookie('orderid')},"web/api/saleorder/detail", function(json){
//		debugger;
		var order = json.datas[0];
		$('#feedback_add_for_order').attr('onclick', 'send("feedback_add.html?type=3&remark=订单ID为'+order.id+'")');
		$('.st_address').bind('click',function(){
			var params = "?stationid="+order.sTStartID+'&stationid='+order.sTArriveID;
			send('line_address.html'+params);
		});
		if(order.discountquantity&&order.discountquantity!=0){
			$('.discount').show();
		}
		if(!isempty(order.tipcontent)){
			$('.tipcontent').parent().show();
		}
		jeach(order,function(k,v){
			if(k=='iDCode'){
				if(!isempty(v)&&v.indexOf('*')==-1){
					var val = v.substring(0,6)+'****'+v.substring(v.length-4);
					return {val:val}
				}else{
					$('.iDCode').parent().hide();
				}
			}else if(k=='codeurl'&&order.payStatus==1){
				return {val:'../'+v};
			}else if(k=='quantity'){
				return {val:v-order.discountquantity};
			}else if(k=='status'){
				console.log(v);
				if(v=="0"){
					$('.o_status').text('待取票');
				}else if(v=="1"){
					$('.o_status').text('已取票');
				}else if(v=="2"){
					$('.o_status').text('退票中');
				}else if(v=="3"){
					$('.o_status').text('已退票');
				}else if(v=="4"){
					$('.o_status').text('已取消');
				}else if(v=="5"){
					$('.o_status').text('已拒绝');
				}else if(v=="6"){
					$('.o_status').text('已过期');
				}
				return {val:'images/status'+v+'.png'}
			}else{
				return true;
			}
		});
		/*if((order.quantity-order.discountquantity)<=0){
			$('.price').parents('.price_parent').hide();
		}*/
		if(order.price===order.vprice||1==1){
			$('.price').parents('.price_parent').hide();
		}
//		if(order.discountquantity<=0){
//			$('.price').parent().hide();
//		}
		$.cookie('orderstatus',order.status);
		if(order.payStatus==0){
			$('.actualSum').text('0.00');
			$('.o_info1').css('padding-top','30px');
		}else{
//			console.debug(order.payStatus)
			$('.notpay').show();
		}
		if(order.couponsSum==0||isempty(order.couponsSum)){
			$('.couponsSum').parents("tr").hide();
		}
		try {
			if (!isempty(order.seatnos) && order.seatnos.trim() != '0') {
				$('._seatnos').show();
			} else {
				$('._seatnos').hide();
			}
		} catch (e) {
			$('._seatnos').hide();
		}
		if(order.payStatus==1&&order.status==0){
			$('.return').bind('click',function(){
				if(confirm("确定要退票吗？")==false){
					return;
				}
				var params = {};
				params.id = $('.id').eq(0).text().trim();
				params.status = 2;
				ajax(params, "web/api/saleorder/cancelorder",function(json){
					send('feedback_add.html?type=7&content=请输入你要退票的数量,客服人员会根据退票规则进行处理...&remark=订单ID为'+order.id);
				});
			});
			
			$('.order').bind('click',function(){
				send('ticket_list.html');
			});
			
			
			$('.o_confirm').show();
		}else if(order.payStatus==0&&order.status==0){
			$('.return').val('取消').bind('click',function(){
				if(confirm("确定要取消吗？")==false){
					return;
				}
				var params = {};
				params.id = $('.id').eq(0).text().trim();
				params.status = 4;
				ajax(params, "web/api/saleorder/cancelorder",function(json){
					send('order_list.html');
				});
			});
			
			$('.order').val('付款').bind('click',function(){
				var id = $('.id').text().eq(0).trim();
				wxpay(id,function(res){
					$('.order').unbind('click');
					send('order_list.html');
//					location.reload();
				},function(res){
					send('order_list.html');
				});
			});
			
			$('.o_confirm').show();
		}else{
			$('.order').bind('click',function(){
				send('ticket_list.html');
			});
		}
		$('body').show();
		var lines = $(".lineName").text();
		var items = lines.split("-");
		var startCity = items[0];
		var endCity = items[1];
		$('.startcity').text(startCity);
		$('.endcity').text(endCity);
		$('.o_info1').css('padding-top','0px');
	});
	
	$('.codeurl').click(function(){
		$('.big').show();
		$('.big').find("img").attr("src",this.src);
	});
	
	$('.big').click(function(){
		$('.big').hide();
	});
});