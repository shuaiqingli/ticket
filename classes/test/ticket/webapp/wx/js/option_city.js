$(function(){
	
	if($.cookie('city_type')=="0"){
		$('title').text('出发城市');
	}else if($.cookie('city_type')=="1"){
		$('title').text('到达城市');
	}
	
	ajax({ishot:1},"public/api/getAllCity", function(json){
		jeach(json.datas, function(k,v){
			if(v.ishot==1){
				appendCity($('.hotcity'),v);
			}
		});
	})
	
	try {
		if($.cookie('begin_city')!=null){
			appendCity($('.option_history'),JSON.parse($.cookie('begin_city')));
		}
		
		if($.cookie('end_city')!=null){
			if($.cookie('end_city')==$.cookie('begin_city')){
			}else{
				appendCity($('.option_history'),JSON.parse($.cookie('end_city')));
			}
		}
	} catch (e) {
		console.debug(e)
	}
	
	$('.cityname').bind('click',function(){
		if($.cookie('city_type')=="0"){
			$.cookie('begin_city',$(this).attr('city'),{expires:9999})
		}else if($.cookie('city_type')=="1"){
			$.cookie('end_city',$(this).attr('city'),{expires:9999})
		}
		var begin = $.cookie('begin_city');
		var end = $.cookie('end_city');
		if(begin==null){
			begin = {};
		}
		if(end==null){
			end = {};
		}
		if($.cookie('begin_city')!=null){
			begin = JSON.parse($.cookie('begin_city'));
		}
		if($.cookie('end_city')!=null){
			end = JSON.parse($.cookie('end_city'));
		}
//		if(end.id!=undefined&&begin.id!=undefined&&end.id==begin.id){
//			toast.show("始发城市与到达城市不能相同！");
//			if($.cookie('city_type')=="0"){
//				$.cookie('begin_city',null);
//			}else if($.cookie('city_type')=="1"){
//				$.cookie('end_city',null);
//			}
//			return;
//		}
		send('index.html');
	});
});

function appendCity(parent,v){
	var o = $('.cityname').first().clone(true).show();
	o.val(v.cityname);
	o.attr('city',JSON.stringify(v));
	parent.append(o.show());
}