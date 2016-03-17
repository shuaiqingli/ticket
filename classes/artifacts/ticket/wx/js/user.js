$(function(){
	if($.cookie('user')){
//		var user = JSON.parse($.cookie('user'));
		post("web/api/customer/info", function(json){
			var user = json.datas[0];
			jeach(json.datas[0], function(){return true;});
			if(user.cname){
				$('.cname').text(user.cname);
			}else{
				$('.cname').text('');
			}
			$('.coupons').text(user.coupons);
			$('.u_jifen').text((typeof(user.integral) == 'undefined'? 0 : user.integral)+'分');
			if(user.headphoto){
				$('.h_img')
				.css('background',"url("+user.headphoto+") no-repeat center")
				.css('background-size','120px');
			}
		});
	}
	var win_hei = $(window).height();
	var heis = $('#tip_box').height();
	var top = (win_hei-heis)/2;
	$('#tip_box').css('top',top+'px');
	var win_width = $(window).width();
	var widths = $('#tip_box').width();
	var left = (win_width-widths)/2;
	$('#tip_box').css('left',left+'px');
	$(window).resize(function(){
		var win_width = $(window).width();
		var widths = $('#tip_box').width();
		var left = (win_width-widths)/2;
		$('#tip_box').css('left',left+'px');
		var win_hei = $(window).height();
		var heis = $('#tip_box').height();
		var top = (win_hei-heis)/2;
		$('#tip_box').css('top',top+'px');
	});
	$('#cancels').click(function(){
		$('#overlay').hide();
		$('#tip_box').hide();
	});
	$('.logouts').click(function(){
		$('#overlay').show();
		$('#tip_box').show();
	});
});

function getFeedbackStatus(){

}