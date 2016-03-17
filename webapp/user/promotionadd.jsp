<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>新增促销规则</title>
	<style type="text/css">
		ul,li{list-style: none;padding: 0px;margin: 0px;}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/user/promotionlist.do?lmid=${lm.id}" style="color: black"></a>
				新增促销规则
		  </h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/user/promotionadd.do">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>规则名称</label>
					<input name="promotionname" type="text" class="span3" id="promotionname" value="${lm.lineid}" datatype="*" nullmsg="规则名称不能为空"/>
			      <input name="lmid" type="hidden" id="lmid" value="${lm.id}">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>开始日期</label>
					<div class="input-append date ">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="begindate" id="begindate" size="16" type="text" value="" readonly datatype="*" nullmsg="开始时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
			  <div style="margin-left: 30px;">
			    <label>结束日期</label>
				  <div class="input-append date">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="enddate" size="16" type="text" value="" readonly datatype="*" nullmsg="结束时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
             <div style="margin-left: 30px;" id="weekdayList">
<!-- 			   <input type="hidden" name="weekdays" datatype="*" nullmsg="可选星期不能为空" value=""> -->
								<label>可选星期  <span style="color: gray;font-size: 12px;">(未选中表示不生效)</span></label>
								<label class="checkbox inline">
									<input id="mon" type="checkbox" name="weekdays" value="1" ><span>周一</span>	
								</label>
								<label class="checkbox inline">
									<input id="tues" type="checkbox" name="weekdays" value="2" ><span>周二</span>	
								</label>
								<label class="checkbox inline">
									<input id="wed" type="checkbox" name="weekdays" value="4"><span>周三</span>	
								</label>
								<label class="checkbox inline">
									<input id="thur" type="checkbox" name="weekdays" value="8" ><span>周四</span>	
								</label>
								<label class="checkbox inline">
									<input id="fri" type="checkbox" name="weekdays" value="16" ><span>周五</span>	
								</label>
								<label class="checkbox inline">
									<input id="set" type="checkbox" name="weekdays" value="32" ><span>周六</span>	
								</label>
								<label class="checkbox inline">
									<input id="sun"  type="checkbox" name="weekdays" value="64"><span>周日</span>	
								</label></div>
            </div>
            <script language="javascript">
        	function addTime(){
				$("#timeSet").append('<div style="margin-left: 30px;"><label style="width:700px;"><input name="begintime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="From" value="07:00"> - <input name="endtime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="To" value="12:00">&nbsp;&nbsp;&nbsp;优惠金额 <input name="reducesum" type="text" class="span3" style="width:100px" id="reducesum" value="0" datatype="d" nullmsg="面值不能为空">(元)&nbsp;&nbsp;&nbsp;优惠票数百分比 <input name="couponpercent" type="text" class="span3" style="width:100px" id="couponpercent" value="100" datatype="percent" nullmsg="优惠票百分不能为空">%<a onclick="removeTime(this);"> <i class="icon-minus-sign"></i></a></label></div>');
				$('#timeSet').find('input.bootstrap-timepicker').each(function(){
				$(this).timepicker({
					showMeridian : false,
					showInputs : true,
					defaultTime : false,
					format:'H:i',
					minuteStep : 1
				});
	});
			}
			function removeTime(obj){
				$(obj).parent().remove();
			}
			</script>
			<br/>
            <div class="row" id="addtime">
				<div style="margin-left: 30px;">
					<span>时间段</span>
				    <input name="addtimebtn" type="button" class="btn" id="addtimebtn" value="添加" onClick="addTime();" checked="btn">
		      	</div>
			</div>
			<br/>
            <div class="row" id="timeSet">
            </div>
			<div class="row">
				<div style="margin-left: 30px;">启用 是:
				  <input name="isenable" type="radio" class="span1" value="1" checked="CHECKED"/>				
					否:<input type="radio" value="0" name="isenable" class="span1"/>				
				</div>
			</div>
			<br/>
            
			<div class="row">
				<div class="span2" style="margin-top:8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top:8px;">
					<input type="button" onclick="history.go(-1)"  class="btn" value="返回">
				</div>
			</div>
	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
/*
$("#promotionline").change(function(){
  //alert($("#promotionline").val());
  var id = $("#id").val();
  if($("#promotionline").val()==1){
  layer.open({
    type: 2,
	title:"促销线路",
    area: ['700px', '530px'],
    fix: false, //不固定
    maxmin: false,
	scrollbar: false,
	btn: ['关闭'],
    content: basePath+'/admin/promotionline.do?id='+id
	});
  }
});*/

$('#timeSet').find('input.bootstrap-timepicker').each(function(){
		$(this).timepicker({
			showMeridian : false,
			showInputs : false,
			defaultTime : false,
			format:'H:i',
			minuteStep : 1
		});
	});

$(function(e){
	//window.ishide = 1;
	//提交表单
	form = $("form").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			return true;
		},
		beforeSubmit:function(curform){
			if($('[name=weekdays]:checked').length==0){
				layer.msg('请选择星期!')
				return false;
			}
		},
		callback:function(data){
			console.debug(data);
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/user/promotionlist.do?lmid=${lm.id}"
			}else if(data==-100){
				layer.alert('该优惠规则的日期已经被占用，请选择其他日期');
			}else{
				console.debug(data)
				layer.alert(data);
			}
		}
	});
	
});
</script>
<script type="text/javascript">
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		startDate:new Date(),
		forceParse: 0,
		
    });
	$('.form_time').datetimepicker({
	 	language:  'zh-CN',
	 	format:'hh:ii',
        weekStart: 0,
        todayBtn:  0,
		autoclose: 1,
		todayHighlight: 0,
		startView: 0,
		minView: 0,
		maxView: 1,
// 		startDate:new Date(),
		forceParse: 0
    });
	
	$('.form_date,.form_time')
	.datetimepicker()
	.on('changeDate', function(ev){
		$(this).find('span').last().hide();
	});
   
    
</script>
</html>