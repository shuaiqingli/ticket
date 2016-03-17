$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	initSenderForm();
});

function initSenderForm(){
	window.senderForm = $("#senderForm").Validform({
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
				$("#senderForm").attr('action',basePath+'/senderAdd.do');
			}else{
				$("#senderForm").attr('action',basePath+'/senderEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/senderList.do';
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

function chooseBlockArea(){
	var $iframe = null;
	layer.open({
	    type: 2,
	    title: '选取片区',
	    btn: ['确定', '取消'],
	    shadeClose: true,
	    shade: 0.8,
	    offset: '150px',
	    area: ['800px', 'auto'],
	    content: basePath+'/selectBlockArea.do?flag=sender',
	    success: function(layero, index){
	    	$iframe = layero.find('iframe');
	    },
	    yes : function(){
	    	var idList = [];
	    	$iframe.contents().find('.check_single:checked').each(function(){
	    		idList.push($(this).val());
	    	});
	    	if(idList.length > 0){
	    		var senderId = $('input[name="sid"]').val();
	    		$.ajax({
					type : 'POST',
					url : basePath+'/bindBlockAreaToSender.do',
					data : {senderId:senderId,blockAreaIdList:idList.join(',')},
					dataType : "json",
					success : function(data){
						if(data.status=='y'){
							location.href=location.href.split('#')[0]+'#1';
							location.reload();
						}else{
							if(typeof(data.info) == 'string' && data.info.length > 0){
								layer.msg(data.info);
							}else{
								layer.msg('操作失败');
							}
						}
					},
					error : function(){
						layer.msg('操作失败');
					}
				});
	    	}
	    }
	}); 
}

function delBlockArea(id){
	if(typeof(id) == 'number' && id > 0){
		var sid = $('input[name="sid"]').val();
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/unbindBlockAreaToSender.do',
				data : {sid:sid,id:id},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#1';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}