$(function(){
	var begin_city = $.cookie('begin_city');
	var end_city = $.cookie('end_city');
	var date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	if(day<=9){
		day = '0'+day;
	}
	if(month<=9){
		month = '0'+month;
	}
	$('.today').val(month+'-'+day+'(今天)');
	
//	if($.cookie('start_date')!=null){
//		$(".today").val($.cookie('start_date').split("-")[1]+"-"+$.cookie('start_date').split("-")[2]);
//	}
	if(begin_city!=null&&begin_city!=undefined&&begin_city!='null'){
		begin_city = JSON.parse(begin_city);
		$('.begin_city').find('.cityname').css('color','#535353').html(begin_city.cityname);
	}else{
	}
	
	if(end_city!=null&&end_city!=undefined&&end_city!='null'){
		end_city = JSON.parse(end_city);
		$('.end_city').find('.cityname').css('color','#535353').html(end_city.cityname);
	}
	
	$('.begin_city').click(function(){
		$.cookie("city_type", 0);
		send('option_city.html');
	});
	$('.end_city').click(function(){
		$.cookie("city_type", 1);
		send('option_city.html');
	});
	
	$('.submit').click(function(){
		if(begin_city==null||end_city==null){
			toast.show('选择出发和到达城市！');
			return;
		}else if($('#date').val()==false){
			toast.show('出发日期不能为空！');
			return;
		}
		if(begin_city.id!=undefined&&end_city.id!=undefined&&end_city.id==begin_city.id){
			toast.show("始发城市与到达城市不能相同！");
			if($.cookie('city_type')=="0"){
				$.cookie('begin_city',null);
			}else if($.cookie('city_type')=="1"){
				$.cookie('end_city',null);
			}
			return;
		}
		$.cookie('start_date',date.getFullYear()+'-'+$('#date').val().substring(0,5));
		$.cookie('startcityid',begin_city.id);
		$.cookie('arrivecityid',end_city.id);
		send('ticket_list.html');
	});
	
	$('.change_icon').click(function(){
		if(!end_city==null||!begin_city==null||begin_city=='null'||end_city=='null'){
			return;
		}
		$.cookie('begin_city',JSON.stringify(end_city));
		$.cookie('end_city',JSON.stringify(begin_city));
		begin_city = $.cookie('begin_city');
		end_city = $.cookie('end_city');
		if(begin_city!=null&&begin_city!=undefined&&begin_city!='null'){
			begin_city = JSON.parse(begin_city);
			$('.begin_city').find('.cityname').css('color','#535353').html(begin_city.cityname);
		}
		if(end_city!=null&&end_city!=undefined&&end_city!='null'){
			end_city = JSON.parse(end_city);
			$('.end_city').find('.cityname').css('color','#535353').html(end_city.cityname);
		}
	});

	loadNewsData();
});

function loadNewsData() {
	var params = {type : 1};
	ajax(params, "public/api/news", function (json) {
		var newsList = json.datas[0];
		if(newsList.length > 0){
			var elementList = [];
			for (var i = 0; i < newsList.length; i++) {
				elementList.push('<div>' + newsList[i].amtitle + '</div>');
			}
			$('#announcement .single-item').text('').append(elementList);
			$('#announcement').show();
			$('#announcement .single-item').slick({
				infinite: true,
				autoplaySpeed: 5000,
				speed: 2000,
				slidesToShow: 1,
				slidesToScroll: 1,
				autoplay: true,
				arrows: false
			});
		}
	});
}

