$(function() {	
	initLoginForm();
});

function initLoginForm(){
	window.loginForm = $("#loginForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			var hash = hex_md5($('input[type="password"]').val());
			$('input[name=password]').val(hash);
		},
		beforeSubmit:function(curform){
		},
		callback:function(data){
			if(data.responseText=="success"){
				layer.msg('登录成功！');
				$('input[name="password"]').val("");
				location.href = basePath +"/user/main.do";
			}else{
				layer.msg('登录失败！');
			}
		}
	});
}
