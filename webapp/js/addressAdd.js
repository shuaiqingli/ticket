$(function() {
	initAddressForm();
	initMap();
});

function initAddressForm(){
	window.addressForm = $("#addressForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#addressForm").attr('action',basePath+'/addressAdd.do');
			}else{
				$("#addressForm").attr('action',basePath+'/addressEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/addressList.do';
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

function initMap(){
	var marker = null;
	var map = new BMap.Map("allmap"); 
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 18);
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom();
	map.enableContinuousZoom();
	map.addEventListener("click",function(e){
		$('input[name="lng"]').val(e.point.lng);
		$('input[name="lat"]').val(e.point.lat);
		map.removeOverlay(marker);
		var newpoint = new BMap.Point(e.point.lng, e.point.lat);
		marker = new BMap.Marker(newpoint);
		map.addOverlay(marker);
	});
}

function showMap(){
	if($('#mapContainer:visible').length > 0){
		$('#mapContainer').hide();
		$('#mapBtn').text('显示地图');
	}else{
		$('#mapContainer').show();
		$('#mapBtn').text('隐藏地图');
	}
}

function trans(){
	var content = $('input[name="addrname"]').val();
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
	var pid = $('#provinceSelector').find("option:selected").val();
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
						optionList.push('<option value="'+data.cityList[i].id+'">'+data.cityList[i].areaname+'</option>');
					}
					$('#citySelector').html(optionList.join(''));
					$('#districtSelector').html('<option value="">--请选择区县--</option>');
					$('#blockAreaSelector').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseCity(){
	var cid = $('#citySelector').find("option:selected").val();
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
						optionList.push('<option value="'+data.districtList[i].id+'">'+data.districtList[i].areaname+'</option>');
					}
					$('#districtSelector').html(optionList.join(''));
					$('#blockAreaSelector').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseDistrict(){
	var did = $('#districtSelector').find("option:selected").val();
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
					$('#blockAreaSelector').html(optionList.join(''));
				}
			}
		});
	}
}