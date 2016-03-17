$(function(){
	$.cookie('back','option_linkman.html',{path:'/'});
	
	ajax({pageNo:1,pageSize:1000},"web/api/linkman/list", function(json){
//		if(json.datas.length==0){
//			$.cookie('back',location.href);
//			send('link_edit.html');
//		}else{
			jeach(json.datas,function(k,v){
				var o = $('.link').eq(0).clone(true).show();
				jeach(v, function(k,v){
					return {$:o};
				});
				o.find('.json').text(JSON.stringify(v));
				$('.link').last().after(o);
			});
//		}
		
	});
	
	$('.link').click(function(){
		var json = $(this).find('.json').text();
		$.cookie('link',json);
		send('order_infomation.html');
	});
	
});