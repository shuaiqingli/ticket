<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String ak="F7611c68af149e79b454126156660ead";
application.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="1700">
	<title>数据统计</title>
</head>
<script type="text/javascript" src="<%=basePath%>/common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/common.js"></script>
<body>
	<div class="container" style="width: 1280px;margin:0 auto;background-color: #d0d1d5">
		<div class="content" style="margin: 0 46px 0 44px;padding-bottom: 30px;">
			<div class="cell">
				<div style="overflow: hidden;line-height: 140px;height: 140px;">
					<div style="width: 396px;float: left;text-align: left;padding-top: 24px;">
						<img src="${basePath}/images/logo2.png"/>
					</div>
					<div style="width: 398px;float: left;text-align: center;font-size: 75px;font-family: 微软雅黑,黑体;font-weight: bold;color:#fff;" class="time">
						11:32
					</div>
					<div style="width: 396px;float: left;text-align: right;font-family: 微软雅黑,黑体;font-size: 32px;color:#fff;line-height: 170px;">
						<span class="date">1月19日</span>&nbsp;<span style="font-family: 黑体;" class="weekday">星期二</span>
					</div>
				</div>
			</div>
			<div class="cell" style="overflow: hidden;">
				<div style="width: 390px;float: left;">
					<div style="font-family: 黑体;font-size: 32px;height: 78px;line-height: 78px;width: 390px;background-color: #fff;text-indent: 22px;color:#d0d0d0">出票</div>
					<div style="height: 4px;background-color: #e4e4e4;"></div>
					<div style="height:308px;background-color: #fff;text-align: center;">
						<div style="display: inline-block;margin-top: 66px;font-family: 微软雅黑,黑体;font-weight: bold;">
							<div style="font-size: 100px;line-height: 100px;margin-bottom: 20px;">
							<span style="color: #d1d2d5;" class="ticket_prefix">00</span><span style="color:#000;" class="todayticket">0</span></div>
							<div style="font-size: 26px;line-height: 26px;"><img src="${basePath}/images/red_arrow.png"/>
								<span style="display:inline-block;margin-left: 18px;"><span class="ticket_percent">0</span>%</span>
								<span style="display:inline-block;margin-left: 30px;color:#d9d8dd;" class="differ_ticket">0</span>
							</div>
						</div>
					</div>
				</div>
				<div style="width: 390px;margin: 0 10px;float: left;">
					<div style="font-family: 黑体;font-size: 32px;height: 78px;line-height: 78px;width: 390px;background-color: #fff;text-indent: 22px;color:#d0d0d0">下单</div>
					<div style="height: 4px;background-color: #e4e4e4;"></div>
					<div style="height:308px;background-color: #fff;text-align: center;">
						<div style="display: inline-block;margin-top: 66px;font-family: 微软雅黑,黑体;font-weight: bold;">
							<div style="font-size: 100px;line-height: 100px;margin-bottom: 20px;">
							<span style="color: #a0dbd7;" class="order_prefix">00</span><span style="color:#01c4b4;" class="todayorder">0</span>
						</div>
						<div style="font-size: 26px;line-height: 26px;"><img src="${basePath}/images/red_arrow.png"/>
							<span style="display:inline-block;margin-left: 18px;"><span class="order_percent">0</span>%</span>
							<span style="display:inline-block;margin-left: 30px;color:#d9d8dd;" class="differ_order">0</span>
						</div>
						</div>
					</div>
				</div>
				<div style="width: 390px;float: left;">
					<div style="height: 195px;background-color: #fff;padding-left: 22px;">
						<div style="font-size: 32px;font-family: 黑体;color:#d0d0d0;padding-top: 30px;">关注</div>
						<div style="font-size: 72px;font-family: 微软雅黑,黑体;overflow:hidden;"><span style="color: #d1d2d5;font-weight: bold;">
						</span><span style="color:#000;font-weight: bold;float:left;" class="todayfllow">0</span>
						<span style="display: inline-block;float:left;margin-left: 20px">
						<img src="${basePath}/images/green_arrow.png"/></span>
						<span style="display: inline-block;float:left;margin-left: 5px;padding-top:50px;font-size:26px;font-family: 微软雅黑,黑体;font-weight: bold;">
						<span class="follw_percent">0</span>%</span>
						<span style="display: inline-block;float:left;padding-top:50px;margin-left: 5px;color:#d9d8dd;font-size:26px;font-family: 微软雅黑,黑体;font-weight: bold;" class="differ_follw">0</span>
					</div>
					</div>
					<div style="height: 195px;background-color: #fff;padding-left: 22px;">
						<div style="font-size: 32px;font-family: 黑体;color:#d0d0d0;padding-top: 30px;">注册</div>
						<div style="font-size: 72px;font-family: 微软雅黑,黑体;overflow:hidden;"><span style="color: #d1d2d5;font-weight: bold;">
						</span><span style="color:#000;font-weight: bold;float:left;" class="todayregister">1</span><span style="display: inline-block;float:left;margin-left: 20px">
						<img src="${basePath}/images/green_arrow.png"/></span>
						<span style="display: inline-block;float:left;margin-left: 5px;padding-top:50px;font-size:26px;font-family: 微软雅黑,黑体;font-weight: bold;"><span class="register_percent">0</span>%</span>
						<span style="display: inline-block;float:left;margin-left: 5px;padding-top:50px;color:#d9d8dd;font-size:26px;font-family: 微软雅黑,黑体;font-weight: bold;" class="differ_register">0</span></div>
					</div>
				</div>
			</div>
			<div class="cell" style="background-color: #fff;margin-top: 10px;font-family: 黑体;color: #d9d9d9;">
				<div style="font-size: 32px;padding: 20px 20px;display:inline-block;">总</div>
				<div style="overflow: hidden;line-height: 72px;">
					<div style="width: 396px;float: left;text-align: center;font-size: 20px;">
						下单<span style="font-size: 32px;font-family: 微软雅黑,黑体;font-weight: bold;color:#000;display: inline-block;margin-left: 14px;" class="sumorder">0</span>
					</div>
					<div style="width: 398px;float: left;text-align: center;font-size: 20px;">
						关注<span style="font-size: 32px;font-family: 微软雅黑,黑体;font-weight: bold;color:#000;display: inline-block;margin-left: 14px;" class="total">0</span>
					</div>
					<div style="width: 396px;float: left;text-align: center;font-size: 20px;">
						注册<span style="font-size: 32px;font-family: 微软雅黑,黑体;font-weight: bold;color:#000;display: inline-block;margin-left: 14px;" class="sumregister">0</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
window.basePath='<%=basePath%>';
$(function(){
	settime();
	 getdata();
	setInterval(function(){
		settime();
	}, 1000);
	setInterval(function(){
// 		 getdata();
		location.reload();
	}, 1000*60*29);
	
});

function getdata(){
	ajax({},basePath+"/public/getDataStatistic",function(json){
// 		json.todayticket = 10;
		if(json.todayticket>9){
			$('.ticket_prefix').text('0');
		}
		if(json.todayticket>99){
			$('.ticket_prefix').text('');
		}
		
// 		json.todayorder = 2001;
		if(json.todayorder>9){
			$('.order_prefix').text('0');
		}
		if(json.todayorder>99){
			$('.order_prefix').text('');
		}
		
		var register = 0;
		var ticket = 0;
		var order = 0;
		register = (json.todayregister-json.yesterdayregister);
		ticket = (json.todayticket-json.yesterdayticket);
		order = (json.todayorder-json.yesterdayorder);
		follw = (json.todayfllow-json.yesterdayfllow);
		
		var register_percent = register/(json.yesterdayregister==0?1:json.yesterdayregister)*100;
		var ticket_percent = ticket/(json.yesterdayticket==0?1:json.yesterdayticket)*100;
		var order_percent = order/(json.yesterdayorder==0?1:json.yesterdayorder)*100;
		var follw_percent = follw/(json.yesterdayfllow==0?1:json.yesterdayfllow)*100;
		
// 		console.debug(register_percent);
// 		console.debug(order_percent);
// 		console.debug(ticket_percent);
		
		if(order>0){
			order = '+'+order;
		}else{
			$('.differ_order').parent().find('img').attr('src',basePath+'/images/green_arrow.png')
		}
		if(ticket>0){
			ticket = '+'+ticket;
		}else{
			$('.differ_ticket').parent().find('img').attr('src',basePath+'/images/green_arrow.png')
		}
		if(register>0){
			register = '+'+register;
			$('.differ_register').parent().find('img').attr('src',basePath+'/images/red_arrow.png')
		}else{
			$('.differ_register').parent().find('img').attr('src',basePath+'/images/green_arrow.png')
		}
		if(follw>0){
			follw = '+'+follw;
			$('.differ_follw').parent().find('img').attr('src',basePath+'/images/red_arrow.png')
		}else{
			$('.differ_follw').parent().find('img').attr('src',basePath+'/images/green_arrow.png')
		}
		
		json.differ_order = order;
		json.differ_ticket = ticket;
		json.differ_register = register;
		json.differ_follw = follw;
		//-----------------------------------------
		json.register_percent = register_percent.toFixed(1);
		json.order_percent = order_percent.toFixed(1);
		json.ticket_percent = ticket_percent.toFixed(1);
		json.follw_percent = follw_percent.toFixed(1);
		
// 		console.debug(register);
// 		console.debug(order);
// 		console.debug(ticket);
		console.debug(follw_percent);
		
		
		jeach(json,function(){
			return true;
		});
	});
}

var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
function settime(){
	var date = new Date();
	var h = date.getHours();
	var m = date.getMinutes();
	var s = date.getSeconds();
	if(h<10){
		h='0'+h;
	}
	if(m<10){
		m='0'+m;
	}
	if(s<10){
		s='0'+s;
	}
// 	var time = h+'：'+m+'：'+s;
	var time = h+'：'+m;
	
	var month = date.getMonth()+1;
	var day = date.getDate();
	if(month<10){
		month='0'+month;
	}
	if(day<10){
		day='0'+day;
	}
	
	$('.date').text(month+'月'+day+'日');
	
	$('.weekday').text(weeks[date.getDay()]);
	$('.time').text(time);
}

</script>
</html>