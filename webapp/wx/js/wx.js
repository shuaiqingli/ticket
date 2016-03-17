var version = 2.3;
var debug = false;
var public_url = "login.html,register.html,news.html,index.html,password.html,line_address.html";
public_url += ",option_city.html,ticket_list.html,ticket_detail.html,search_city.html,staticpage.html";
//var basepath = "http://192.168.4.176/ticket/";
var basepath = "../";
var isLast = false;
var page = 1;
var msg = null;
var islock = false;

$(function(){
	init();
	initMessage();
	//https://res.wx.qq.com/open/js/jweixin
	//jsapi();
	if($('[src*="https://res.wx.qq.com/open/js/jweixin"]').length!=0){
		jsapi();
	}else{
		if(debug){
			console.debug('not found js sdk lib ....');
		}
	}
});

//初始化消息
function initMessage(){
	if($.cookie('message_flag') != 1){
		var expiresDate= new Date();
		expiresDate.setTime(expiresDate.getTime() + (10 * 60 * 1000));
		$.cookie('message_flag', '1', { path: "/", expires: expiresDate});
		ajax({
			activityIDList: $.cookie('activityIDList'),
			noticeIDList: $.cookie('noticeIDList')
		}, "public/api/message", function(json){
			if(json.datas[0] == 'success'){
				var feedback_total = json.datas[1];
				var activity_total = json.datas[2];
				var notice_total = json.datas[3];
				$.cookie('feedback_total', feedback_total, { path: "/"});
				$.cookie('activity_total', activity_total, { path: "/"});
				$.cookie('notice_total', notice_total, { path: "/"});
				showMessage();
			}
		});
	}else{
		showMessage();
	}
}

function showMessage(){
	var feedback_total = $.cookie('feedback_total');
	var activity_total = $.cookie('activity_total');
	var notice_total = $.cookie('notice_total');
	if(feedback_total > 0 && $('.menu_nav .on_my,.menu_nav .menu_nav_mine').length > 0 ){
		var nav_element = $('.menu_nav .on_my,.menu_nav .menu_nav_mine').parents('.menu_nav');
		nav_element.prepend('<div class="pointers"></div>');
		var f_width = $('.menu_nav').width();
		nav_element.find('.pointers').css('left',(f_width/2)+15+'px');
		$(window).resize(function(){
			var f_width = $('.menu_nav').width();
			nav_element.find('.pointers').css('left',(f_width/2)+15+'px');
		});
	}
	if(feedback_total > 0 && $('li#feedback img').length > 0 ){
		$('li#feedback img').show();
	}
	if((activity_total > 0 || notice_total > 0) && $('.menu_nav .on_news,.menu_nav .menu_nav_news').length > 0 ){
		var nav_element = $('.menu_nav .on_news,.menu_nav .menu_nav_news').parents('.menu_nav');
		nav_element.prepend('<div class="pointers"></div>');
		var f_width = $('.menu_nav').width();
		nav_element.find('.pointers').css('left',(f_width/2)+18+'px');
		$(window).resize(function(){
			var f_width = $('.menu_nav').width();
			nav_element.find('.pointers').css('left',(f_width/2)+18+'px');
		});
	}
	if(activity_total > 0 && $('.activity_tab').length > 0 ){
		$('.activity_tab span').append('<span class="pointers" style="width: 16px;height: 16px;border-radius: 50%;background-color: #ff7941;display: inline-block;position: relative;bottom: 15px;"></span>');
	}
	if(notice_total > 0 && $('.notice_tab').length > 0 ){
		$('.notice_tab span').append('<span class="pointers" style="width: 16px;height: 16px;border-radius: 50%;background-color: #ff7941;display: inline-block;position: relative;bottom: 15px;"></span>');
	}
}

//初始化
function init(){
//	setInterval(function(){
//		islock = false;
//		if(debug){
//			console.debug('===>> update islock false');
//		}
//	}, 1000*5);

	$(".notnull").attr("data-notnull","");
	var cookiejs = $('<script type="text/javascript" src="js/cookie.js?version='+version+'"></script>');
	$("head").append('<link type="text/css" rel="stylesheet" href="css/loaders.css?version='+version+'">')
	$("html").append(cookiejs)
	loading()
	$('.logout').bind('click',function(){
		logout();
	});
	if($.cookie('version')==null){
		$.cookie('version',version);
	}

	var hrefparams = gethrefparams();
	try {
		if (hrefparams.openid == null || hrefparams.openid == 'null') {
			delete params.openid;
		}
	} catch (e) {

	}
	try {
		if ($.cookie('openid') == null || $.cookie('openid').length < 10) {
			$.cookie('openid', null, {
				path : '/'
			})
		}
	} catch (e) {
		$.cookie('openid', null, {
			path : '/'
		})
		console.debug(e);
	}
	if(isempty($.cookie('openid'))){
		if(hrefparams&&hrefparams.openid){
			$.cookie('openid',hrefparams.openid);
			if(debug){
//				alert(hrefparams.openid)
				console.debug('设置openid成功：' +$.cookie('openid'));
			}
		}
	}
	if(cookie('openid')==null&&iswxbrowser()){
		var href = location.href;
		var html = href.split('?')[0].split('#')[0];
		html = html.substring(html.lastIndexOf('/')+1);
		href = 'center?send='+html+"&state="+(html.length>64?'index.html':html);
//		if(href.indexOf("login.html")==-1&&href.indexOf("index.html")==-1&&href.indexOf("register.html")==-1){
//			send(href);
//		}
		send(href);
//		console.debug(href)
//		console.debug(cookie('openid'))
//		toast.show(html);
	}


	//检查登录
//	var isCheck = false;
//    var urls = public_url.split(",");
//    for (var i = 0; i < urls.length; i++) {
//        var url = urls[i];
//        if (window.location.href.indexOf(url) != -1 && url != "") {
//            isCheck = false;
//        }
//    }
//    if($.cookie("version")!=version){
//    	$.cookie("version",version);
//    	logout();
//    	return;
//    }
//    if (($.cookie("user") == ""||$.cookie("user") == null)&&isCheck==true) {
//		$.cookie("location_href", window.location.href);
//		send("login.html");
//    	return;
//    }

	$('.fonthy').css('font-family','黑体,微软雅黑,Source Sans Pro,Helvetica Neue,Helvetica,Arial,sans-serif')

	$('.about_page').bind('click',function(){
		sendstaticpage(1);
	});
	$('.buymemo_page').bind('click',function(){
		sendstaticpage(2);
	});
	$('.plicy_page').bind('click',function(){
		sendstaticpage(3);
	});

}

//退出
function logout(){
	post("web/api/customer/customerLogout", function(json){
		if(json.datas[0] == 'success'){
			$.cookie('user',null,{path:"/"});
			//	$.cookie('COOKIE_CUSTOMER_MOBILE',null,{path:"/"});
			$.cookie('COOKIE_CUSTOMER_PASSWORD',null,{path:"/"});
			$.cookie('user',null,{path:"/"});
			//$.cookie('openid',null,{path:"/"});
			send('login.html');
		}
	});
}

//loading 样式
function loading(){
	var html = '<div class="loader"><div class="loader-inner pacman"><div></div><div></div><div></div><div></div><div></div></div></div>';
	if($(".loader").length==0){
		$('body').append(html);
		$('.loader').hide();
	}
}

//消息提示
toast = {
	show:function(content,time,type){
		if($("#toast").length==0){
			createToast(type);
		}
		showToast(content);
		$("#toast").css('width','100%');
		hideToast(time);
	},
	loading:function(content,type){
		if($("#toast").length==0){
			createToast(type);
		}
		showToast(content);
	},
	hide:function(time){
		hideToast(time);
	}
}

//页面跳转
function send(name){
	location.href = name + (name.indexOf('?') > 0 ? '&v=' : '?v=') + version;
}

function jeach(json, fn) {
	//fn 回调函数
	var index = 0;

	//对 json 对象遍历 取出 key 和 val
	for (var key in json) {
		//获取 json  val
		var val = json[key];
		//判断 fn 是否是函数
		if (typeof fn == "function") {
			// 回调函数返回结果  r 接收(result)
			var r = fn(key, val,index);
			// 返回值有两种
			// 1. true | false
			// 2. json格式对象 {$:*,s:*,t:*,val:*,k.*} *代表变量
			index++;
			//判断 r 是否有返回结果，没有 不处理
			if (r == undefined || r == false || r == null) {
				continue;
			}
			//返回有结果
			//  r.t   （type） 要处理的html标签  例如 return {t:'input,img,select'} 默认即可，已经自动处理好了
			//  r.s   （selector） jquery 选择器 默认为 class 选择器 可以传入   return {s:'#'} id 选择器
			//  r.val （value） 如果返回将替换 遍历json 的默认值，通常处理图片用的  return {val:imgProject+v}
			//  r.k   （key）返回的 新的key ,例如 json 返回默认是 name 修改  return {k:'username'}  <input type='text' class='username'/> //自动赋值
			// 默认  <input type='text' class='name'/>
			// r.k 和 r.val 根据自己的需要，不需要改变值 默认即可

			//  r.$   （parent Node） 模板   为了避免冲突 修改 为 _$

			var t = r.t;
			var s = r.s;
			var rv = r.val;
			var k = r.key;
			var _$ = r.$;  //  r.$

			//如果 r.$ 没有返回 默认 为 $("*"); 相当于 $('body')
			if (_$ == undefined) {
				_$ = $("*");
			}
			//如果 r.val 没有新的值，默认遍历的 json.val
			if (rv != undefined) {
				val = rv;
			}
			//如果 r.k 没有新的值，默认遍历的 json.key
			if (k != undefined) {
				key = k;
			}
			//选择器为空 默认是 class  .
			if (s == undefined) {
				s = ".";
			}
			/**
			 * 例如一段html  <div>  <span class='name'> </span> <span class='money'> </span> </div>
			 * json 格式如下 {name:'abc',money='1000',name:'ddd',money='1000'}
			 *
			 * 当前遍历的json  key = name  val = 'abc'
			 * _$  =  div
			 * key = 'name'
			 * val = 'abc'
			 *
			 * s + key = '.' +'name' 处理结果如下：
			 * $('div').find('.name').text('abc');
			 *
			 */

			//获取 节点名称 例如 name 等于  input,img,div,span ......等等
			// 根据 节点名称不同 赋值方式不同  div 赋值 text ，input 赋值 value
			var name = _$.find(s + key)[0] == undefined ? "": _$.find(s + key)[0].nodeName.toUpperCase();
			var obj = _$.find(s + key);// class 选择器
			var obj2 = _$.find(key);// 标签选择器

			//如果返回 结果是 字符类型 $= string  return {$:'abc'} 直接赋值
			if (typeof r == "string") {
				$("." + key).text(r);
				$(key).text(r);
			} else if (typeof r == "object") {// json 类型

				//判断 如果是否自定义属性 , 例如  ${attr:'href'}       $('div').find('.name').attr('href','abc')
				if (r.attr) {
					obj.attr(r.attr, val);
					continue;
				}
				//根据不同的标签 赋值
				if (t == undefined) {
					$.each(_$.find(s + key),
						function(i, v) {
							name = $(v)[0] == undefined ? "": $(v)[0].nodeName.toUpperCase();
							if(isNaN(val)==false){
								$(v).eq(0).css('font-family','黑体,微软雅黑,Source Sans Pro,Helvetica Neue,Helvetica,Arial,sans-serif');
							}
							if (name == "IMG") {
								$(v).eq(0).attr("src", val);
							}
							if (name == "INPUT") {
								$(v).eq(0).val(val);
								$("[name='"+key+"']").val(val);
							}
							if (name != "INPUT" && name != "IMG") {
								$(v).eq(0).text(val);
								obj2.text(val);
							}
						});
					continue;
				}
				if (t.indexOf("s") != -1 && name == "IMG") {
					obj.attr("src", val);
				}
				if (t.indexOf("v") != -1 && (name == "INPUT") || name.toUpperCase() == "SELECT") {
					obj.val(val);
				}
			} else if (r == true) { // 返回 boolean 类型
				if(isNaN(val)==false){
					obj.css('font-family','黑体,微软雅黑,Source Sans Pro,Helvetica Neue,Helvetica,Arial,sans-serif');
				}
				if (name == "IMG") {
					obj.attr("src", val);
				}
				if (name == "INPUT") {
					obj.val(val);
					$("[name='"+key+"']").val(val);
				}
				if (name != "INPUT" && name != "IMG") {
					obj.text(val);
					obj2.text(val);
				}
			}
		} else if (fn == true) { // 默认处理
			$("." + key).text(val);
			$(key).text(val);
		}
	}
}

/**
 * ajax 请求
 * @param params
 * @param url
 * @param callback
 * @param _async
 * @param _type
 */
function ajax(params, url, callback,_async,_type) {
//	islock = true;
//	console.debug('>>>>> update islock true');
	if (url == undefined) {
		return;
	}else{
		url = basepath + url;
	}
	var data = {};
	data.mobile = $.cookie('mobile');
	data.cid = $.cookie('cid');
	data.token = $.cookie('token');
	data.openid = $.cookie('openid');
	var error = function() {
		if (typeof callback.error == "string") {
			msg = callback.error;
		}else{
			if(typeof callback.error == "function"){
				callback.error(msg);
			}else{
//            	alert("ERROR");
			}
		}
	};
	var success = function() {
		$(".loader").hide();
	};
	var beforeSend = function() {
//        $(".loader").show();
		toast.loading('数据加载中。。。');
	};
	var async = true;
	var dataType = "JSON";
	var type = "POST";
	var fn = "function";
	if (callback == undefined) {
		callback = {};
	}
	if (params == undefined) {
		params = {};
	}
	if (typeof callback == fn) {
		success = callback;
	}
	if (typeof callback.success == fn) {
		success = callback.success;
	}
	if (typeof callback.beforeSend == fn) {
		beforeSend = callback.beforeSend;
	}
	if (typeof callback.error == fn) {
		error = callback.error;
	}
	if (callback.async == false) {
		async = callback.async;
	}
	if (callback.dataType) {
		dataType = callback.dataType;
	}
	if (callback.type) {
		type = callback.type;
	}
	if (typeof params == "object") {
		$.each(params,
			function(key, val) {
				if(val!=undefined&&val!=null){
					data[key] = val;
				}
			});
	}else{
		console.debug('======== error ==========');
		return;
	}
	if(_async==false){
		async = _async;
	}
	if(_type){
		dataType = _type;
	}
	try {
		$.ajax({
			url: url,
			type: type,
			data: data,
			dataType: dataType,
			async: async,
			beforeSend: function(XMLHttpRequest) {
				XMLHttpRequest.setRequestHeader('requested-type','APP');
				XMLHttpRequest.setRequestHeader('device','wx');
				XMLHttpRequest.setRequestHeader('WEB','JCTICKET');
				$(".notdata").hide();
				beforeSend(XMLHttpRequest);
			},
			error: function(msg) {
				islock = false;
				$(".loader").hide();
				$("body").show();
				if(msg.status==200){
//            		alert("json parse error");
					var jt = msg.responseText;
					log(data, url, json, null);
				}else{
					error(msg);
					log(data, url, null, msg);
				}
				$('#toast').remove();
			},
			success: function(json) {
				islock = false;
				if(typeof json == 'string'){
					json = JSON.parse(json);
				}
				$("body").show();
				$(".loader").hide();
				$('#toast').remove();
				log(data, url, json, null);
				if(json.code!=200){
					toast.show(json.msg,2000);
					if(json.code==5008){
						$.cookie('location_href',location.href,{path:"/"});
						send('login.html');
//            			login({},function(r,o){
//            				if(r===true){
//            					toast.show('自动登录成功！');
//            					setTimeout(function(){
//            						location.reload();
//            					}, 1000);
//            				}else{
//            					toast.show('自动登录失败！');
//		            			setTimeout(function(){
//		            			}, 1500);
//            				}
//            			});
					}
					error(json);
				}else{
					success(json);
				}
				if(json.page&&page==1&&json.datas&&json.datas.length==0){
					if(msg!=null){
//            			toast.show(msg,5000);
					}else{
//            			toast.show('暂无数据....',5000);
					}
					return;
				}else if(json.datas.length>0&&json.page&&page>=1){
					$("#toast").removeClass("md-show");
				}
				if(json.page && page==1){
					isLast = false;
				}
				if(json.page){
					if(page>json.page.totalPage&&page!=1){
						toast.show('亲，已经是最后一页了');
						isLast = true;
					}else{
						page = json.page.pageNo + 1;
					}
				}
			}
		});
	} catch(e) {
		islock = false;
		$('#toast').remove();
		log("", "", "", "", e);
		$("#load").hide();
		$("body").show();
	}
};



function post(url, callback) {
	ajax({},url, callback);
};

function postAsync(url, callback) {
	ajax({},url, callback,false);
}

function ajaxAsync(ps,url, callback) {
	ajax(ps,url,callback,false);
}
/**
 * 日志打印
 * @param params
 * @param url
 * @param json
 * @param msg
 * @param exception
 */
function log(params, url, json, msg, exception) {
	try {
		var date = new Date();
		if (debug) {
			console.debug("----------- " + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds()
				+ "  -----------");
			if (url) {
				console.debug("[url]\n\t" + url);
			}
			if (params) {
				console.debug("[params]\n");
				if (typeof params == "object") {
					jeach(params, function(k, v) {
						console.debug("\t" + k + "=" + v + "\t\t");
					});
				} else {
					console.debug("\tdata = " + params + "\t\t");
				}
			}
			if (json&&json.msg) {
				console.debug("[message]\n\t" + json.msg);
//				jeach(json.msg, function(k, v) {
//					console.debug("\t" + k + "=" + v + "\t\t");
//				});
			}
			if (json&&json.page) {
				console.debug("[page]\n");
				jeach(json.page, function(k, v) {
					console.debug("\t" + k + "=" + v + "\t\t");
				});
			}
			if (json&&json.records) {
				if (json.records.length >= 1) {
					console.debug("[first result]\n");
					jeach(json.records[0], function(k, v) {
						console.debug("\t" + k + "=" + v + "\t\t");
					});
				}
			}
			if (json) {
				console.debug("[result]");
				console
					.debug(typeof json == "string" ? JSON.parse(json)
						: json);
			}
			if (json&&json.records) {
				console.debug("[list]\n");
				console.debug(json.records);
			}
			if (msg) {
				console.debug("[status]\n\t" + msg.status + "\n[error]\n\t\t"
					+ msg.statusText);
			}
			if (exception) {
				console.debug("[exception]\n\t" + exception);
			}
		}
	} catch (e) {
		console.debug(e);
	}
}



patterns = {
	"*":/[\w\W]+/,
	"*6-16":/^[\w\W]{6,16}$/,
	"n":/^\d+$/,
	"n6-16":/^\d{6,16}$/,
	"s":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]+$/,
	"s6-18":/^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/,
	"p":/^[0-9]{6}$/,
	"d":/^([+-]?)\\d*\\.\\d+|\d+$/,
	"m":/\d{11}/,
	"e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
	"url":/^(\w+:\/\/)?\w+(\.\w+)+.*$/,
	"idcode":/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
}

api = {
	//验证码
	getVerificationCode:function(mobile,type){
		ajax({mobile:mobile,type:type}, "public/api/getVerificationCode",function(json){

		})
	},
	getLineTipContent:function(lmid,date,fn){
		ajax({lmid:lmid,date:date}, "web/api/saleorder/getLineTipContent",function(json){
			var content = json.datas[0];
			if(isempty(content)){
//				fn(null);
			}else{
				fn(json.datas[0]);
			}
		})
	}
}


//表单验证
validate = function(elements){
	var items = {};
	if(elements==undefined||elements==null){
		items = $('form').find('[data-notnull=""]');
	}else{
		items = elements;
	}
	var isSubmit = true;
	$.each(items,function(i,v){
		if(isSubmit==false){
			return;
		}
		var val = $(v).val();
		var msg = $(v).attr('data-msg');
		var pattern = $(v).attr('data-pattern');
		var temp = patterns[pattern];
		if(temp){
			pattern = temp;
		}
		var msg2 = '请检查数据是否填写正确！';
		msg = msg?msg:msg2;
		if(val.trim()==''){
			isSubmit = false;
			$(v).focus();
			toast.show(msg+'不能为空', 1500);
			return;
		}
		if(pattern!=undefined&&pattern!=null&&pattern.test(val)==false){
			isSubmit = false;
			$(v).focus();
			toast.show(msg+'填写不正确', 1500);
			return;
		}

	});
	return isSubmit;

}

/**
 * 显示提示框
 * @param {Object} id
 */
function showToast(content){
	$(".md-content").css('font-size','25px').css("padding",'20px').css('border-radius','7px');
	if(typeof content == 'string'){
		$(".md-content").html(content);
	}else if(typeof content == 'object'){
		$(".md-content").append(content) ;
	}
	$("#toast").addClass("md-show");
}


/**
 * 隐藏提示框
 * @param {Object} id
 */
function hideToast(time){
	setTimeout(function(){
		$("#toast").removeClass("md-show");
	},time?time:1500);
}
/**
 * 创建弹窗
 */
function createToast(type){
	if(type==undefined||type==null){
		type=1;
	}
	var cls = 'md-modal md-effect-'+type;
	var div = document.createElement('div');
	div.setAttribute('class',cls);
	div.setAttribute('id','toast');
	var div2 = document.createElement('div');
	div2.setAttribute('class','md-content');
	div2.setAttribute('class','md-content');
	div.appendChild(div2);
	document.body.appendChild(div);
}

function getparams(elements){
	if(elements==undefined||elements==null){
		elements = $('input,select,textarea');
	}
	var params = {};
	$.each(elements,function(i,v){
		var val = $(v).val();
		var name = $(v).attr("name");
		if(name){
			params[name] = val;
		}
	});
	return params;
}

wx_date = {
	formatDate:function(d,split){
		if(split==undefined||split==null){
			split = '-';
		}
		var year = d.getFullYear();
		var month = d.getMonth()+1;
		if(month<=9){
			month = '0'+month;
		}

		var day = d.getDate();
		if(day<=9){
			day = '0'+day;
		}
		return year+split+month+split+day;
	}
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
};

//是否登录
function islogin(fn){
	if(fn!=undefined&&typeof fn =='function'){
		post("public/api/islogin", {success:function(json){
			var result = json.datas[0];
			if(result==1){
				fn(true,json);
			}else{
				fn(false,json);
			}
		},error:function(json){
			fn(false,json);
		}});
	}else{
		var user = $.cookie('user');
		var islogin = $.cookie('customer_login');
		if(islogin=="0"){
			return false;
		}else{
			return true;
		}
	}
}

//获取参数
function gethrefparams(){
	var href = location.href;
	if(href.indexOf('#')!=-1){
		href = href.substring(0,href.lastIndexOf('#'));
	}
	var params = {};
	try {
		if (href.indexOf("?")) {
			href = href.split("?")[1].split("&");
			for (var i = 0; i < href.length; i++) {
				var e = href[i];
				var k = e.split("=")[0];
				var v = e.split("=")[1];
				if(isempty(params[k])){
					params[k] = v;
				}else{
					var p = params[k];
					if(!isempty(p.push)){
						p.push(v);
					}else{
						var arr = [];
						arr.push(p);
						arr.push(v);
						params[k] = arr;
					}
				}
			}
		}
	} catch (e) {
		return {};
	}
	return params;
}

//清除cookie
function clearCookie(){

};

//jsapi
function jsapi(){
	try {
		$.ajax({
			type : "GET",
			url : "../wx/jsticketsign",
			async : false,
			data : {
				url : location.href.split("#")[0]
			},
			dataType : "JSON",
			success : function(msg) {
				var params = {
					//url,jsapi_ticket,nonceStr,timestamp,signature
					debug : false,
					appId : msg.appId,
					timestamp : msg.timestamp,
					nonceStr : msg.nonceStr,
					signature : msg.signature,
					jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
						'onMenuShareAppMessage', 'onMenuShareQQ',
						'onMenuShareWeibo', 'hideMenuItems',
						'showMenuItems', 'hideAllNonBaseMenuItem',
						'showAllNonBaseMenuItem', 'translateVoice',
						'startRecord', 'stopRecord', 'onRecordEnd',
						'playVoice', 'pauseVoice', 'stopVoice',
						'uploadVoice', 'downloadVoice', 'chooseImage',
						'previewImage', 'uploadImage', 'downloadImage',
						'getNetworkType', 'openLocation', 'getLocation',
						'hideOptionMenu', 'showOptionMenu', 'closeWindow',
						'scanQRCode', 'chooseWXPay',
						'openProductSpecificView', 'addCard', 'chooseCard',
						'openCard' ]
				}
				try {
					wx.config(params);
				} catch (e) {
					console.debug(e)
				}
			}
		});
	} catch (e) {
	}
}

try {
	//微信js sdk 初始化
	wx.ready(function(){
//		wx.hideAllNonBaseMenuItem();
//		wx.hideOptionMenu();
		if(location.href.indexOf('index.html')){
			wx.getLocation({
				type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
				success : function(res) {
					var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
					var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
					var c = $.cookie('begin_city');
					var location = latitude+','+longitude;
					if(c==null||c==''||c=='null'){
						ajax({location:location}, "public/api/getCityByLocation", function(json){
							var result = json.datas[0];
							if(result!=null){
								$.cookie('begin_city',JSON.stringify(result));
								$('.begin_city').find('.cityname').text(result.cityname)
								location.reload();
							}
						})
					}
				}
			});
		}
		wx.getLocation({
			type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			success : function(res) {
				var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
				var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
				$.cookie("lat",latitude,{path:"/"});
				$.cookie("lng",longitude,{path:"/"});
			}
		});
	});

	wx.error(function(res){

	});

} catch (e) {

}

//定位
function getPosition(fn){
	if (navigator.geolocation){
		navigator.geolocation.getCurrentPosition(function(position){
			var lat = osition.coords.latitude;
			var lng = position.coords.longitude;
			$.cookie('lat',lat);
			$.cookie('lat',lng);
			fn(lng,lat)
		},function(msg){

		});
	}
}

// js sdk 支付
function jspay(id,success,cancel){
	ajax({id:id}, "web/api/wx/pay",function(json){
		if (json.datas==null||json.datas==undefined||json.datas.length==0||json.datas[0].error||json.datas[0].err_code_des) {
			if(json.datas[0].err_code_des){
				alert(json.datas[0].err_code_des);
			}else{
				alert('服务器异常，请稍后再试！');
			}
			send('order_list.html');
			return;
		}
		var w = json.datas[0];
		wx.chooseWXPay({
			"timestamp":w.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			"nonceStr":w.nonceStr, // 支付签名随机串，不长于 32 位
			"package":w.packagestr, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
			"signType":w.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			"paySign":w.paySign, // 支付签名
			"cancel":function(res){if(cancel&&typeof cancel == 'function'){cancel(res);}},
			"error":function(res){if(cancel&&typeof cancel == 'function'){cancel(res);}},
			"success": function (res) {
				// 支付成功后的回调函数
				if(success&&typeof success == 'function'){
					success(res);
				}
			}
		});
	})
}




// H5 支付
function wxpay(id,success,cancel) {
	if(isempty(WeixinJSBridge)||isempty(WeixinJSBridge.invoke)){
		if(!isempty(cancel)){
			cancel();
		}
	}
	try {
		ajax({id:id}, "web/api/wx/pay",function(json){
			if (json.datas==null||json.datas==undefined||json.datas.length==0||json.datas[0].error||json.datas[0].err_code_des) {
				if(json.datas[0].err_code_des){
					alert(json.datas[0].err_code_des);
				}else if(json.datas[0].error){
					alert(json.datas[0].error);
				}else{
					alert('服务器异常，请稍后再试！');
				}
				send('order_list.html');
				return;
			}
			var w = json.datas[0];
			WeixinJSBridge.invoke('getBrandWCPayRequest', {
					"appId": w.appId,
					"timestamp": w.timeStamp,
					"timeStamp": w.timeStamp,
					"nonceStr": w.nonceStr,
					"package": w.packagestr,
					"signType": w.signType,
					"paySign": w.paySign
				},
				function(res) {
					if (res.err_msg == "get_brand_wcpay_request:ok") {
						if(success&&typeof success == 'function'){
							success(res);
						}
					}else {
						if(cancel&&typeof cancel == 'function'){
							cancel(res);
						}
					}
				});
		})

	} catch (e) {
		cancel(e);
	}
}


function sendstaticpage(id){
	if(!isempty(id)){
		$.cookie('staticpage',id);
		send('staticpage.html');
	}
}


function isempty(s){
	if(s==null){
		return true;
	}
	if(typeof s == 'string'){
		if(s==undefined||s==''||s=='null'||s.trim()==''){
			return true;
		}
	}else if(typeof s == 'object'){
		if(s.length==0){
			return true;
		}
	}else{
		return false;
	}
}

function notdata(json,parent,type,error){
	try{
		if(parent==null||parent==false||parent==undefined){
			parent = $("body");
		}
		if(parseInt(page)==1&&(json.datas==null||json.datas.length==0)){
			if(parent.find(".notdata").length==0){
				if(type!=null&&type!=undefined&&type!=''){
					parent.append("<div class='notdata "+type+" '></div>");
				}else{
					parent.append("<div class='notdata'></div>");
				}
			}
			$('.notdata').show();
			isLast = true;
		}else{
			$(".notdata").hide().remove();
			isLast = false;
		}
	}catch(e){
	}
}

//登录接口
function login(params,fn){
//	if($.cookie('openid')==null){
//		console.debug('openid为空自动登录失败！');
//		send('login.html');
//		return;
//	}
	ajaxAsync(params, "public/api/customerLogin",{success:function(json){
		if(json.datas&&json.datas[0]){
			var user = json.datas[0];
			var age = {expires:999,path:"/"};
			$.cookie('cid',user.cid,age)
			$.cookie('token',user.token,age)
			//$.cookie('user',JSON.stringify(user),age);
			//$.cookie('openid',user.openid?user.openid:null,age);
			if( fn && typeof fn=='function'){
				fn(true,user);
			}else{
				location.reload();
			}
		}
	},
		error:function(e){
			if( fn && typeof fn=='function'){
				fn(false,e);
			}else{
				send('login.html');
			}
		}
	});
}

function iswxbrowser(){
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger") {
		return true;
	} else {
		return false;
	}
}

//获取用户最新
function refresheduser(fn){
	post("web/api/customer/info", function(json){
		var user = json.datas[0];
		var age = {expires:999};
		$.cookie('mobile',user.mobile,age)
		$.cookie('cid',user.cid,age)
		$.cookie('token',user.token,age)
		$.cookie('user',JSON.stringify(user),age);
		$.cookie('openid',user.openid?user.openid:'',age);
		fn(user);
	});
}



function checkTimeStamp(page){
	if(isempty(page)){
		return false;
	}
	if(page.timestamp!=0){
		var timestamp = $.cookie('timestamp');
		console.debug(page.timestamp+"==="+timestamp);
		if(!isempty(timestamp)){
			timestamp = parseInt(timestamp);
			if(timestamp==page.timestamp){
				return true;
			}
		}
	}
	return false;
}

function setTimeStamp(params){
	if(isempty(params)){
		params = {};
	}
	params.timestamp = new Date().getTime();
	$.cookie("timestamp",params.timestamp,{path:"/"});
}

function getuser(){
	if(islogin()){
		var user = JSON.parse($.cookie('user'));
		if(typeof user == 'string'){
			return eval(user);
		}else if(typeof user== 'object'){
			return user;
		}
	}else{
		return null;
	}
}
