<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>班次列表</title>
	<style type="text/css">
		.span2{
			color: #1C2C2C;
		}
		.span2 span{
			color: #3C2C2C;
		}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/shiftlist" method="get">
			<div class="page-header">
				<h2>班次列表</h2>
			</div>
			<div class="pull-right" style="padding-right:10px;">
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
				<select style="width: 140px;" name="citystartid" class="beginCitys">
					<option value="">--- 出发城市 ---</option>
					<c:forEach var="city" items="${citys}">
						<option  value="${city.id }" <c:if test='${city.id == param.citystartid }'>selected="selected"</c:if> >${city.cityname }</option>
					</c:forEach>
				</select>
				<select style="width: 140px;" name="ststartid" class="beginStationSelect">
					<option value="">--- 出发站点 ---</option>
				</select>
				<select style="width: 140px;" name="cityarriveid" class="endCitys">
					<option value="">--- 到达城市 ---</option>
					<c:forEach var="city" items="${citys}">
						<option  value="${city.id }" <c:if test='${city.id == param.cityarriveid }'>selected="selected"</c:if> >${city.cityname }</option>
					</c:forEach>
				</select>
				<select style="width: 140px;" name="starriveid" class="endStationSelect">
					<option value="">--- 到达站点 ---</option>
				</select>
                    <span>班次日期：</span><input name="date" placeholder="班次日期" size="16" type="text" value="${page.param.date }" readonly="readonly" class="date _date"/>
                    <span>发班时间：</span><input name="starttime" placeholder="发班时间" size="16" type="text" value="${page.param.starttime }" readonly="readonly" class="starttime"/>
					<input type="text" name="shiftcode" value="${page.param.shiftcode }"
						placeholder="班次号/线路号"  style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
			</div>
			<br/>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
				<tr>
						<th>班次号</th>
						<th>线路</th>
						<th>出发站</th>
						<th>终点站</th>
						<th>发车时间</th>
						<th>发车日期</th>
						<th>是否已发车</th>
						<th>车牌</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd tr" style="<c:if test="${o.isstart==1}">color:#999;</c:if>"  >
							<td class="shiftcode">${o.shiftcode}</td>
							<td>${o.linename}</td>
							<td>${o.currstationname}</td>
							<td>${o.starrivename}</td>
							<td class="time">${o.punctualstart}</td>
							<td>${o.ridedate}</td>
							<td class="status" isstart="${o.isstart }">${o.isstart == 0 ? '未发车': o.isstart==1 ? '已发出':'已取消'}</td>
							<td>${o.platenum}</td>
							<td>
								<a class="btn btn_detail" href="javascript:;;" id="${o.id}">
									详情
								</a> 
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@include file="../common/page.jsp"%>
		</form>
	</div>
	
	<div class="detail" style="display: none;padding: 10px;">
		<div class="row">
			<div class="span2">班次号：<span class="shiftcode"></span></div>
			<div class="span2">线路：<span class="linename"></span></div>
			<div class="span2">出发站：<span class="currstationname"></span></div>
			<div class="span2">终点站：<span class="starrivename"></span></div>
		</div>
		<div class="row">
			<div class="span2">发车时间：<span class="punctualstart"></span></div>
			<div class="span2">发车日期：<span class="ridedate"></span></div>
			<div class="span2">车牌：<span class="platenum"></span></div>
			<div class="span2">核载：<span class="nuclearload"></span></div>
		</div>
		<div class="row">
			<div class="span2">全票：<span class="allticketnum"></span></div>
			<div class="span2">半票：<span class="halfticketnum"></span></div>
			<div class="span2">免票：<span class="freeticketnum"></span></div>
		</div>
		<div class="row">
			<div class="span2">托运行李件数：<span class="consignquantity"></span></div>
			<div class="span2">乘客行李金额：<span class="consignsum"></span></div>
			<div class="span2">乘客行李件数：<span class="passengerquantity"></span></div>
			<div class="span2">乘客超重行李金额：<span class="passengersum"></span></div>
		</div>
		<div class="row">
			<div class="span2">司机1：<span class="drivername"></span></div>
			<div class="span2">司机2：<span class="drivernameii"></span></div>
		</div>
		<hr/>
		<div class="row">
			<div class="span2">备注：<br><span class="memo"></span></div>
		</div>
	</div>

	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
var $date = "${page.param.date}";
//获取城市列表
function getStationList(parentid,parent,fn){
	var data = {parentid:parentid,ishot:1};
	$.ajax({
	   type: "POST",
	   dataType:"JSON",
	   url: basePath+"/public/stationlist.do",
	   data: data,
	   success: function(json){
		   console.debug(json);
	   		if(json){
	   			parent.children().not(parent.children().eq(0)).remove();
	   			fn(json,parent);
	   		}
	   }
	});
}

//获取站点列表
function fn_getStationList(parent,cityId,stationId,type){
	getStationList(cityId,parent,function(json,parent){
 			$.each(json,function(i,v){
 				if(type==0){
	 				if(v.id == "${page.param.ststartid}"){
	 					parent.append('<option selected="selected" value="'+v.id+'">'+v.cityname+'</option>');
	 				}else{
		   				parent.append('<option value="'+v.id+'">'+v.cityname+'</option>');
	 				}
 				}else{
	 				if(v.id == "${page.param.starriveid}"){
	 					parent.append('<option selected="selected" value="'+v.id+'">'+v.cityname+'</option>');
	 				}else{
		   				parent.append('<option value="'+v.id+'">'+v.cityname+'</option>');
	 				}
 				}
 			});
	});
}

$(function(){
	
	fn_getStationList($('.beginStationSelect'), "${param.citystartid}", $(".beginStations").val(),0);
	fn_getStationList($('.endStationSelect'), "${param.cityarriveid}", $(".endStations").val(),1);
	
	//选择城市
	$(".beginCitys").change(function(){
		var parentID = $(this).val();
		if(parentID==""){
			return;
		}
		fn_getStationList($('.beginStationSelect'), parentID, $(".beginStations").val(),0);
	});
	$(".endCitys").change(function(){
		var parentID = $(this).val();
		if(parentID==""){
			return;
		}
		fn_getStationList($('.endStationSelect'), parentID, $(".endStations").val(),1);
	});
	
	
	
// 	$('[name=date]').datetimepicker('setStartDate', new Date());
	if($date&&$date==formatDate(new Date())){
		var items = $('table').find('.tr');
		$.each(items,function(k,v){
			var time = $(v).find('.time').text();
			var status = $(v).find('.status').attr('isstart');
			if(status=="0"){
				var date = new Date();
				var hour = date.getHours();
				var minute = date.getMinutes();
				
				
				var h = parseInt(time.split(':')[0]);
				var m = parseInt(time.split(':')[1]);
				//当前时间 60
				var t1 = hour*60+minute;
				//发车时间 50
				var t2 = h*60+m;
				
				if(t1 >= t2 ){
					$(v).css('color','red');
				}
				if((t2-t1) <= 10 && (t2-t1)>=1){
					$(v).css('color','orange');
				}
// 				console.debug(t2-t1);
			}
		});
	}
	
	
	$('.btn_detail').bind('click',function(){
		layer.open({
			type:1,
			area:['980px','400px'],
			title:'班次详情',
			content:$('.detail')
		});
		var id = $(this).attr('id');
		ajax({id:id}, basePath+"/user/startShiftDetail", function(json){
			$('.detail').find('.span2').find('span').empty();
			jeach(json,function(k,v){
				return {$:$('.detail')}
			});
		});
		
	});
	
})

$('._date').datetimepicker({
    language:  'zh-CN',
    format:'yyyy-mm-dd',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});

$('[name=starttime]').timepicker({
	showMeridian : false,
	showInputs : true,
	defaultTime : false,
	format:'H:i',
	minuteStep : 1
});
</script>
</html>
