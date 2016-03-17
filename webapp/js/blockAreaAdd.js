$(function() {
	initBlockAreaForm();
});

function initBlockAreaForm(){
	window.blockAreaForm = $("#blockAreaForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#blockAreaForm").attr('action',basePath+'/blockAreaAdd.do');
			}else{
				$("#blockAreaForm").attr('action',basePath+'/blockAreaEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/blockAreaList.do';
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

function trans(){
	var content = $('input[name="bname"]').val();
	if(typeof(content)=='string' && content.length > 0){
		$.ajax({
			type : 'POST',
			url : basePath+'/pinyinTrans.do',
			data : {content:content},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					$('input[name="pinyin"]').val(data.pinyin);
					$('input[name="shortpinyin"]').val(data.shortpinyin);
				}
			}
		});
	}
}

function chooseProvince() {
	var pid = $('select[name="pname"]').find("option:selected").attr('data');
	if (typeof (pid) == 'string' && pid.length > 0) {
		$('input[name="pid"]').val(pid);
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
						optionList.push('<option value="'+data.cityList[i].areaname+'" data="'+data.cityList[i].id+'" >'+data.cityList[i].areaname+'</option>');
					}
					$('select[name="cname"]').html(optionList.join(''));
					$('select[name="dname"]').html('<option value="">--请选择区县--</option>');
				}
			}
		});
	}
}

function chooseCity(){
	var cid = $('select[name="cname"]').find("option:selected").attr('data');
	if (typeof (cid) == 'string' && cid.length > 0) {
		$('input[name="cid"]').val(cid);
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
						optionList.push('<option value="'+data.districtList[i].areaname+'" data="'+data.districtList[i].id+'" >'+data.districtList[i].areaname+'</option>');
					}
					$('select[name="dname"]').html(optionList.join(''));
				}
			}
		});
	}
}

function chooseDistrict(){
	var did = $('select[name="dname"]').find("option:selected").attr('data');
	if (typeof (did) == 'string' && did.length > 0) {
		$('input[name="did"]').val(did);
	}
}