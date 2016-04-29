$(document).ajaxError(function (event, request, settings) {
	islock = false;
//    if (request.getResponseHeader('REQUIRES_AUTH') === '1') {
//        window.location = request.getResponseHeader('REQUIRES_AUTH_URL');
//    }
	var reponsetxt = request.responseText;
//	console.debug(reponsetxt)
	if(reponsetxt){
		if(typeof reponsetxt == 'string'){
			try{
				if(reponsetxt.indexOf('code')!=-1&&reponsetxt.indexOf('msg')!=-1){
					reponsetxt = JSON.parse(reponsetxt)
				}
			}catch(e){
				console.debug(e);
			}
		}
		if(reponsetxt.code){
			switch(reponsetxt.code){
				case 8000:
					layer.msg(reponsetxt.msg);
					location.href = basePath+'/login.jsp'
					break;
				default:
					layer.msg(reponsetxt.msg);
					return false;
			}
		}
	}
});


var debug = true;
function jeach(json, fn) {
	var index = 0;
    for (var key in json) {
        var val = json[key];
        if (typeof fn == "function") {
            var r = fn(key, val,index);
            index++;
            if (r == undefined || r == false || r == null) {
                continue;
            }
            var t = r.t;
            var s = r.s;
            var rv = r.val;
            var k = r.key;
            var _$ = r.$;
            if (_$ == undefined) {
                _$ = $("*");
            }
            if (rv != undefined) {
                val = rv;
            }
            if (k != undefined) {
                key = k;
            }
            if (s == undefined) {
                s = ".";
            }
            var name = _$.find(s + key)[0] == undefined ? "": _$.find(s + key)[0].nodeName.toUpperCase();
            var obj = _$.find(s + key);
            var obj2 = _$.find(key);

            if (typeof r == "string") {
                $("." + key).text(r);
                $(key).text(r);
            } else if (typeof r == "object") {
                if (r.attr) {
                    obj.attr(r.attr, val);
                    continue;
                }
                if (t == undefined) {
                    $.each(_$.find(s + key),
                    function(i, v) {
                        name = $(v)[0] == undefined ? "": $(v)[0].nodeName.toUpperCase();
                        if (name == "IMG") {
                            $(v).eq(0).attr("src", val);
                        }
                        if (name == "INPUT") {
                            $(v).eq(0).val(val);
                            _$.find("[name="+key+"]").val(val);
                        }
                        if (name != "INPUT" && name != "IMG") {
                            $(v).eq(0).text(val);
                            _$.find("[name='"+key+"']").val(val);
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
            } else if (r == true) {
                if (name == "IMG") {
                    obj.attr("src", val);
                }
                if (name == "INPUT") {
                    obj.val(val);
                    $("[name="+key+"]").val(val);
                }
                if (name != "INPUT" && name != "IMG") {
                    obj.text(val);
                    obj2.text(val);
                }
            }
        } else if (fn == true) {
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
    if (url == undefined) {
        return;
    }
    var data = {};
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
        $("#load").hide();
    };
    var beforeSend = function() {
        $("#load").show();
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
            data[key] = val;
        });
    }else{
    	var str = convertParams(data,true);
    	data = params+str.substring(0, str.length-1); 
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
            beforeSend: function() {
                beforeSend();
            },
            error: function(msg) {
            	$("#load").hide();
            	$("body").show();
            	if(msg.status==200){
//            		alert("json parse error");
            		var jt = msg.responseText;
            		log(data, url, msg, null);
            	}else{
            		error(msg);
            		log(data, url, null, msg);
            	}
            },
            success: function(json) {
            	if(typeof json == 'string'){
            		try {
						json = JSON.parse(json);
					} catch (e) {
					}
            	}
            	$("body").show();
            	$("#load").hide();
            	log(data, url, json, null);
                success(json);
            }
        });
    } catch(e) {
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
};

function ajaxAsync(ps,url, callback) {
	ajax(ps,url,callback,false);
};

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
				console.debug("[message]\n");
				jeach(json.msg, function(k, v) {
					console.debug("\t" + k + "=" + v + "\t\t");
				});
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

//表单验证
validate = function(elements){
	var items = {};
	if(elements==undefined||elements==null){
		items = $('form').find('.notnull');
	}else{
		items = elements;
	}
	var isSubmit = true;
	$.each(items,function(i,v){
		if(isSubmit==false){
			return;
		}
		var val = $(v).val();
		var msg = $(v).attr('placeholder');
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
			layer.msg(msg+'不能为空');
			return;
		} 
		if(pattern!=undefined&&pattern!=null&&pattern.test(val)==false){
			isSubmit = false;
			$(v).focus();
			layer.msg(msg+'填写不正确');
			return;
		}
		
	});
	return isSubmit;
	
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
		"m":/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/,
		"e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
		"url":/^(\w+:\/\/)?\w+(\.\w+)+.*$/,
		"idcode":/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
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

formatDate = function(d,split){
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

function getweeks(num){
	var weeks = [];
	var arr = [64,32,16,8,4,2,1];
	var temp = num;
	for (var i = 0; i < arr.length; i++) {
		if(arr[i]>temp||temp<=0){
			continue;
		}
		weeks.push(arr[i])
		temp = temp-arr[i];
	}
	return weeks;
}

function differDay(b,e){
	if(e==''||e==null||e==undefined){
		e = b;
	}
	var b = new Date(b.replace(/-/g,"/"));
	var e = new Date(e.replace(/-/g,"/"));
	var d = (e.getTime()-b.getTime())/(1000*60*60*24);
	return d;
}


var weekstr = {1:'一',2:'二',4:'三',8:'四',16:'五',32:'六',64:'日'};

function getweekstring(sum){
	var arr = getweeks(sum);
	var str = [];
	for ( var i in arr) {
		for ( var key in weekstr) {
			if(key==arr[i]){
				str.splice(0,0,weekstr[key]);
				break;
			}
		}
	}
	return str;
}

//审核过的日期变色
function approvedhanlder(ev,dates,fn,change){
	var timepicker = $('.datetimepicker:visible').find('.switch').removeClass('switch');
	var items = $('.datetimepicker:visible').find('tbody').find('.day').not('.old,.new');
	$.each(items,function(i,v){
		var day = $(v).text();
		$.each(dates,function(i,d){
			
			var date = null;
			if(fn==null||fn==undefined){
				date = d.split('-')[2];
			}else{
				date = fn(i,d).split('-')[2];
			}
			if(isNaN(date)==false&&isNaN(day)==false){
				date = parseInt(date);
				day = parseInt(day);
				if(date==day){
					$(v).css('color','green');
					if(change!=null&&change!=undefined&&typeof change == 'function'){
						change($(v),d,day,date);
					}
				}
			}
		})
	})
}

//获取月份的第一天和最后一天
function getMonthFirstLastDate(date){
	var month = date.getMonth();
	var year = date.getFullYear();
	var begindate = new Date(year+'/'+(month+1)+'/'+'/01');
	var enddate = new Date(year+'/'+(month+2)+'/'+'/0');
	enddate.setMonth(begindate.getMonth());
	
	begindate = formatDate(begindate);
	enddate = formatDate(enddate);
	var params = {};
	params.begindate = begindate;
	params.enddate = enddate;
	
	return params;
}

function timepicker(e) {
    e.timepicker({
        showMeridian: false,
        showInputs: true,
        defaultTime: false,
        format: 'H:i',
        minuteStep: 1
    });
}

$(function(){
	$('.nav-tabs').find('li a').click(function(e){
		e.preventDefault();
	});
	/*$("tr").bind("click",function(){
		$("#transmark").remove();
	});*/

	//setInterval(function(){
	//	$("#transmark").remove();
	//},1000);
});
