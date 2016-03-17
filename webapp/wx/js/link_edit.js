$(function(){
	$('.savelink').click(function(){
		if(validate()){
			var params = getparams();
			if(!isempty(params.idcode)){
				if(params.idcode.length!=18){
					toast.show("身份证号码填写不正确！");
					return;
				}
			}
			//console.debug(params);
			ajax(params, "web/api/linkman/edit", function(json){
				if($.cookie('back')==null){
					send(params.back);
				}else{
					send($.cookie('back'));
				}
			});
		}
	});
	var params = gethrefparams();
	if(!isempty(params.id)){
		ajax(params,"web/api/linkman/detail", function(json){
			jeach(json.datas[0],function(){return true});
		});
	}
	$('.lname,.lmobile,.idcode').keyup(function(){
		var lname= $.trim($('.lname').val());
		var lmobile= $.trim($('.lmobile').val());
		var idcode= $.trim($('.idcode').val());
		if(lname!=""){
			$('.lname').parent().parent().find('.del_btn').show();
		}else{
			$('.lname').parent().parent().find('.del_btn').hide();
		}
		if(lmobile!=""){
			$('.lmobile').parent().parent().find('.del_btn').show();
		}else{
			$('.lmobile').parent().parent().find('.del_btn').hide();
		}
		if(idcode!=""){
			$('.idcode').parent().parent().find('.del_btn').show();
		}else{
			$('.idcode').parent().parent().find('.del_btn').hide();
		}
	});
	$('.del_btn').click(function(){
		$(this).hide();
		$(this).parent().parent().find('input').val("");
	});
});