$(function() {
	//单选|全选
	$('.check_all').click(function(){
		if($('.check_all').attr('checked') == 'checked'){
			$('.check_single').attr('checked','true');
		}else{
			$('.check_single').removeAttr('checked');
		}
	});
	$('.check_single').click(function(){
		if($(this).attr('checked') != 'checked'){
			$('.check_all').removeAttr('checked');
		}
	});
	init();
});

function init(){
	//自适应高度
	var $iframe = parent.$('div.layui-layer-iframe').find('iframe');
	$iframe.height($iframe.contents().height());
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