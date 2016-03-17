$(function(){
	$('.savelink').click(function(){
		if($('.lmobile').val().length!=11){
			toast.show('手机号码填写不正确！');
			return;
		}
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
});