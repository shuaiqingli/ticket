$(function() {
	
});

function delBlockArea(id){
	if(typeof(id) == 'number' && id > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/blockAreaDel.do',
				data : {id:id},
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

function chooseProvince() {
	var pid = $('select[name="pid"]').find("option:selected").val();
	if (typeof (pid) == 'string' && pid.length > 0) {
		$.ajax({
			type : 'POST',
			url : basePath+'/cityList.do',
			data : {pid:pid},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择城市--</option>');
					for(var i = 0;i < data.cityList.length;i++){
						optionList.push('<option value="'+data.cityList[i].id+'">'+data.cityList[i].areaname+'</option>');
					}
					$('select[name="cid"]').html(optionList.join(''));
					$('select[name="did"]').html('<option value="">--请选择区县--</option>');
				}
			}
		});
	}
}

function chooseCity(){
	var cid = $('select[name="cid"]').find("option:selected").val();
	if (typeof (cid) == 'string' && cid.length > 0) {
		$.ajax({
			type : 'POST',
			url : basePath+'/districtList.do',
			data : {cid:cid},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择区县--</option>');
					for(var i = 0;i < data.districtList.length;i++){
						optionList.push('<option value="'+data.districtList[i].id+'">'+data.districtList[i].areaname+'</option>');
					}
					$('select[name="did"]').html(optionList.join(''));
				}
			}
		});
	}
}