$(function() {
	
});

function delAddress(id){
	if(typeof(id) == 'number' && id > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/addressDel.do',
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
	var pid = $('select[name="blockArea.pid"]').find("option:selected").val();
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
					$('select[name="blockArea.cid"]').html(optionList.join(''));
					$('select[name="blockArea.did"]').html('<option value="">--请选择区县--</option>');
					$('select[name="blockArea.id"]').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseCity(){
	var cid = $('select[name="blockArea.cid"]').find("option:selected").val();
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
					$('select[name="blockArea.did"]').html(optionList.join(''));
					$('select[name="blockArea.id"]').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseDistrict(){
	var did = $('select[name="blockArea.did"]').find("option:selected").val();
	if (typeof (did) == 'string' && did.length > 0) {
		$('input[name="did"]').val(did);
		$.ajax({
			type : 'POST',
			url : basePath+'/blockAreaList.do',
			data : {did:did},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择片区--</option>');
					for(var i = 0;i < data.blockAreaList.length;i++){
						optionList.push('<option value="'+data.blockAreaList[i].id+'">'+data.blockAreaList[i].bname+'</option>');
					}
					$('select[name="blockArea.id"]').html(optionList.join(''));
				}
			}
		});
	}
}