$(function() {
	
});

function delSender(sid){
	if(typeof(sid) == 'string' && sid.length > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/senderDel.do',
				data : {sid:sid},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
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