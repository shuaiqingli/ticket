$(function(){
	if($.cookie('COOKIE_CUSTOMER_MOBILE')){
		$('.mobile').val($.cookie('COOKIE_CUSTOMER_MOBILE'));
	}
	$('.btn_login').click(function(){
		if(validate()){
            //clearAllCookie();
			var params = getparams();
			params.openid = '';
			login(params,function(result,json){
				var href = $.cookie('location_href');
				if(result==false){
					return;
				}
				if(href!=null&&href!='null'){
					location.href = href;
					$.cookie('location_href',null);
				}else{
					send('index.html');
				}
			})
//			ajax(params, "public/api/customerLogin", function(json){
//				if(json.datas&&json.datas[0]){
//					var user = json.datas[0];
//					var age = {expires:999};
//					$.cookie('mobile',user.mobile,age)
//					$.cookie('cid',user.cid,age)
//					$.cookie('token',user.token,age)
//					$.cookie('openid',user.openid,age)
//					$.cookie('user',JSON.stringify(user),age);
//					var href = $.cookie('location_href');
//					if(href!=null&&href!='null'){
//						location.href = href;
//						$.cookie('location_href',null);
//					}else{
//						send('index.html');
//					}
//				}
//			})
		}
	});
});