$(function(){
	
	$.cookie('back','linkman_list.html',{path:'/'});
	
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
				//find('div:first')
//				o.find('div:first')[0].addEventListener("touchend", touchend, false);
//				o.find('div:first')[0].addEventListener("touchstart", touchstart, false);
//				o.find('div:first')[0].addEventListener("touchmove",touchmove , false);
			});
//		}
		
	});
	
	$('.link').find('.delete').click(function(){
		if(confirm('您确定要删除该联系人吗？')==false){
			return;
		}
		var $this = $(this);
		ajax({id:$(this).parents('.link').find('.id').val()},"web/api/linkman/delete", function(json){
			$this.parents('.link').remove();
		});
	});
	
	$('.link').find('.view').click(function(){
		send('link_edit.html?back=linkman_list.html&id='+$(this).parents('.link').find('.id').val());
	});
	
});