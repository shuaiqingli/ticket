$(function(){
	orderid = $.cookie('orderid');
	console.debug(orderid);
	
	ajax({orderid:orderid},"web/api/saleorder/ticketDetail", function(json){
		if(json.datas.length==0){
			$('.ticket').before('<div class=notdata></div>');
			$('.notdata').show();
		}else{
			var isold = false;
			jeach(json.datas,function(k,v){
				var tk = $('.ticket').eq(0).clone(true);
				var params = {};
				params.$=tk;
				jeach(v,function(k,v){
					return params;
				});
				if(v.seatno==0||v.seatno==undefined){
					v.seatno = 0;
					tk.find('.seatno').text('无座位号');
				}
				if(v.status!=0||v.isontrain==1){
					tk.find('.choose_opt').remove();
					$('.tips').remove();
				}
				if(v.isontrain==1){
					isold = true;
				}
				tk.find('.qrcode').attr('src','../wx/qrcode/'+v.checkcode+'.png');
				tk.data('json',v)
				$('.ticket').last().after(tk.show());
			});
			if(isold){
				$('.outdate_btn').show();
			}else{
				$('.btn_cancel').show();
			}
		}
	});

	if($.cookie('orderstatus')!='1'){
		$('.order_status').hide();
	}
	
	$('.qrcode').error(function(){
		$(this).hide();
	});
	
	$('.confirm_btn').click(function(){
		var items = $('[sel=1]');
		if(items.length==0){
			toast.show('请选择您需要退的车票！');
			return;
		}
		var ids = [];
		$.each(items,function(){
			var json = $(this).parents('.ticket').data('json');
			ids.push(json.checkcode);
		});
		var params = {};
		params.checkcodes = ids.join(',');
		ajax(params, "web/api/saleorder/getRefundInfo", function(json){
			var result = json.datas[0];
			var msg ="";
			$('#overlay1,#tip_box').show();
			jeach(result,function(){
				var params = {};
				params.$=$('#tip_box');
				return params;
			});
			if(result.rank!=2){
			}else{
				$('.percent,.refundmoney').parent().hide();
			}
//			msg+="您确定要退票吗？";
			
			$('#qd').data('params',params).bind('click',function(){
				ajax(params, "web/api/saleorder/refundTicket", function(json){
					send('order_detail.html');
				});
			});
//			if(confirm(msg)){
//			};
		});
		
	});
});