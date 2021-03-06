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