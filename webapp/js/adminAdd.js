$(function() {	
	initAdminForm();
});

function initAdminForm(){
	window.adminForm = $("#adminForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(!$('input[name="password"]').attr('isEncrypt')){
				var hash = hex_md5($('input[name="password"]').val());
				$('input[name="password"]').attr('isEncrypt',true);
				$('input[name="password"]').val(hash);
				$('input[name="password2"]').val(hash);
			}
			if(flag == 'add'){
				$("#adminForm").attr('action',basePath+'/adminAdd.do');
			}else{
				$("#adminForm").attr('action',basePath+'/adminEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/adminList.do';
			}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
		}
	});
}