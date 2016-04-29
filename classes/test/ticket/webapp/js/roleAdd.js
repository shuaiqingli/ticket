$(function() {
	$('input.oneLevel').click(function(){
//		if($(this).is(":checked")){
//			$(this).parents('li').find("input.twoLevel").attr('checked',true);
//		}else{
//			$(this).parents('li').find("input.twoLevel").removeAttr('checked');
//		}
	});
	$('input.twoLevel').click(function(){
//		if(!$(this).is(":checked")){
//			$(this).parents('li').find("input.oneLevel").removeAttr('checked');
//		}
	});
	
	initRoleForm();
});

function initRoleForm(){
	window.roleForm = $("#roleForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			var params = {};
			var menus = [];
			params.menus = menus;
			var all = $('input.oneLevel');
			$('input.oneLevel,input.twoLevel').each(function(i,v){
				$(this).attr("index",i);
				
				var isck= $(v).prop('checked');
				var isdel = $(this).parent().find(".isdel").eq(0);
				var fmid = $(this).parent().find(".id").eq(0);
				fmid.attr("name","rolepowers["+i+"].fMID");
				isdel.attr("name","rolepowers["+i+"].isDel");
				if(isck){
					isdel.val(0);
				}else{
					isdel.val(1);
				}
//				if(i==all.length-1){
//					$('input.oneLevel:checked').each(function(i,v){
//					});
//					$('input.oneLevel').not(":checked").each(function(i,v){
//						var index = $(this).attr("index");
//						var isdel = $(this).parent().find("[type=hidden]").eq(0);
//						var fmid = $(this).parent().find("[type=hidden]").eq(1);
//						fmid.attr("name","rolePowers["+index+"].fMID");
//						isdel.attr("name","rolePowers["+index+"].isDel");
//						isdel.val(1);
//					});
//				}
				
			});
//			var index = all.length;
//			console.debug(index);
//			$('input.towLevel').each(function(i,v){
//				
//			});
//			return false;
		},
		callback:function(data){
			console.debug(data);
            // alert(data)
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/admin/rolelist.do"
			}else{
				layer.msg('操作失败');
			}
		}
	});
}