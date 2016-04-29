<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<%@ include file="../common/head.jsp"%>
<title>新增促销规则</title>
<style type="text/css">
ul, li {
	list-style: none;
	padding: 0px;
	margin: 0px;
}
</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/user/promotionlist.do?lmid=${param.lmid}"
					style="color: black"></a> 修改调价规则
			</h2>
		</div>
		<form method="post" id="adminForm"
			action="${basePath}/user/promotionedit.do">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>规则名称</label> <input name="promotionname" type="text"
						class="span3" id="promotionname" value="${p.promotionname}"
						datatype="*" nullmsg="规则名称不能为空" />
			      <input name="id" type="hidden" id="id" value="${p.id}">
			      <input name="lmid" type="hidden" id="lmid" value="${param.lmid}">
				</div>
			</div>
			<%--<div class="row">--%>
				<%--<div style="margin-left: 30px;">--%>
					<%--<label>开始日期</label>--%>
					<%--<div class="input-append date ">--%>
						<%--<div class="control-group">--%>
							<%--<div class="controls input-append date form_date">--%>
								<%--<input name="begindate" id="begindate" size="16" type="text"--%>
									<%--value="${p.begindate}" readonly datatype="*" nullmsg="开始时间不能为空" />--%>
								<%--<span class="add-on"><i class="icon-remove"></i></span> <span--%>
									<%--class="add-on"><i class="icon-th"></i></span>--%>
							<%--</div>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="row">--%>
				<%--<div style="margin-left: 30px;">--%>
					<%--<label>结束日期</label>--%>
					<%--<div class="input-append date">--%>
						<%--<div class="control-group">--%>
							<%--<div class="controls input-append date form_date">--%>
								<%--<input name="enddate" size="16" type="text" value="${p.enddate}"--%>
									<%--readonly datatype="*" nullmsg="开始时间不能为空" /> <span--%>
									<%--class="add-on"><i class="icon-remove"></i></span> <span--%>
									<%--class="add-on"><i class="icon-th"></i></span>--%>
							<%--</div>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="row hide">--%>
				<%--<div style="margin-left: 30px;" id="weekdayList">--%>
					<%--<input type="hidden" name="weekdays" datatype="*"--%>
						<%--nullmsg="可选星期不能为空" value="${p.weekdays}"> <label>可选星期--%>
						<%--<span style="color: gray; font-size: 12px;">(未选中表示不生效)</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="mon"--%>
						<%--type="checkbox" value="1" onClick="selectWeekday(this);"><span>周一</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="tues"--%>
						<%--type="checkbox" value="2" onClick="selectWeekday(this);"><span>周二</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="wed"--%>
						<%--type="checkbox" value="4" onClick="selectWeekday(this);"><span>周三</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="thur"--%>
						<%--type="checkbox" value="8" onClick="selectWeekday(this);"><span>周四</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="fri"--%>
						<%--type="checkbox" value="16" onClick="selectWeekday(this);"><span>周五</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="set"--%>
						<%--type="checkbox" value="32" onClick="selectWeekday(this);"><span>周六</span>--%>
					<%--</label> <label class="checkbox inline"> <input id="sun"--%>
						<%--type="checkbox" value="64" onClick="selectWeekday(this);"><span>周日</span>--%>
					<%--</label>--%>
				<%--</div>--%>
			<%--</div>--%>
			<script language="javascript">
				function selectWeekday() {
					var weekdays = 0;
					$('#weekdayList').find('input:checkbox').each(
							function(index, element) {
								if ($(this).is(':checked')) {
									weekdays += parseInt($(this).val());
								} else {
									weekdays += parseInt(0);
								}
							});
					//alert(weekdays);
					$('input[name="weekdays"]').val(weekdays);
				}
			</script>
            <script language="javascript">
			function addTime(){
				$("#timeSet").append('<div style="margin-left: 30px;"><label style="width:700px;"><input name="begintime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="From" value="07:00"> - <input name="endtime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="To" value="12:00">&nbsp;&nbsp;&nbsp;调价金额 <input name="reducesum" type="text" class="span3" style="width:100px" id="reducesum" value="0" datatype="d" nullmsg="面值不能为空">(元)&nbsp;&nbsp;&nbsp;<input name="couponpercent" type="hide" class="span3" style="width:100px" id="couponpercent" value="100" datatype="percent" nullmsg="调价票百分不能为空"><a onclick="removeTime(this);"> <i class="icon-minus-sign"></i></a></label></div>');
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
			<div class="row">
				<div style="margin-left: 30px;">
					<label>
						时间段：
					  <input name="addtimebtn" type="button" id="addtimebtn" value="添加" class="btn" onClick="addTime();"></label>
				</div>
			</div>
			<br/>
            <div class="row" id="timeSet">
            	<c:forEach items="${tlist}" var="t">
            		<div style="margin-left: 30px;">
                	<label style="width:700px;">
                	<input name="begintime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="From" value="${t.begintime}"> 
                	- 
                	<input name="endtime" readonly="readonly" type="text" class="input-mini bootstrap-timepicker" placeholder="To" value="${t.endtime}"> 
                	&nbsp;&nbsp;&nbsp;调价金额 <input name="reducesum" type="text" class="span3" style="width:100px" id="reducesum" value="${t.reducesum}" datatype="d" nullmsg="面值不能为空">(元)
                	&nbsp;&nbsp;&nbsp; <input name="couponpercent" type="hidden" class="span3" style="width:100px" id="couponpercent" value="${empty t.couponpercent ? 100:t.couponpercent}" datatype="percent" nullmsg="调价百分不能为空">
                	<a onclick="removeTime(this);"> <i class="icon-minus-sign"></i></a></label></div>
                </c:forEach>
            </div>
			<div class="row hide">
				<div style="margin-left: 30px;">
					启用 是: <input name="isenable" type="radio" class="span1" value="1"
						<c:if test="${p.isenable==1}">checked</c:if> /> 否:<input
						type="radio" value="0" name="isenable" class="span1"
						<c:if test="${p.isenable==0}">checked</c:if> />
				</div>
			</div>
			<br/>
            <div class="row">
                <br/>
                <div style="margin-left: 30px;">默认规则 是:
                    <input name="isdefault" type="radio" class="span1" value="1" ${p.isdefault==1?'checked':''}/>
                    否:<input type="radio" value="0" name="isdefault" ${empty p.isdefault or p.isdefault==0?'checked':''}/>
                </div>
            </div>
			<div class="row">
				<div class="span2" style="margin-top: 8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top: 8px;">
					<input type="button" onclick="history.go(-1)" class="btn"
						value="返回">
				</div>
			</div>
			<br/>
			<%@include file="../common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var w = $('input[name=weekdays]').val();
		if (w >= 64) {
			$("#sun").attr("checked", 'true');
			w -= 64;
		}
		if (w >= 32) {
			$("#set").attr("checked", 'true');
			w -= 32;
		}
		if (w >= 16) {
			$("#fri").attr("checked", 'true');
			w -= 16;
		}
		if (w >= 8) {
			$("#thur").attr("checked", 'true');
			w -= 8;
		}
		if (w >= 4) {
			$("#wed").attr("checked", 'true');
			w -= 4;
		}
		if (w >= 2) {
			$("#tues").attr("checked", 'true');
			w -= 2;
		}
		if (w >= 1) {
			$("#mon").attr("checked", 'true');
			w -= 1;
		}
	});
</script>
<script type="text/javascript">
	$(function(e) {
		//window.ishide = 1;
		//提交表单
		form = $("form").Validform({
			tiptype : 4,
			postonce : true,
			ajaxPost : true,
			beforeCheck : function(curform) {
				return true;
			},
			beforeSubmit : function(curform) {

			},
			callback : function(data) {
				console.debug(data);
				if (data >= 1) {
					layer.msg('操作成功');
                    if("${param.type}"=="1"){
                        location.href = basePath+"/user/lineadd.do?id=${param.lmid}#6"
                        return;
                    }
					location.href = basePath + "/user/promotionlist.do?lmid=${param.lmid}"
				} else if (data == -100) {
					layer.alert('该优惠规则的日期已经被占用，请选择其他日期');
				} else {
				}
			}
		});

	});
</script>
<script type="text/javascript">
	$('.form_date').datetimepicker({
		language : 'zh-CN',
		format : 'yyyy-mm-dd',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		startDate : new Date(),
		forceParse : 0,

	});
	$('.form_time').datetimepicker({
		language : 'zh-CN',
		format : 'hh:ii',
		weekStart : 0,
		todayBtn : 0,
		autoclose : 1,
		todayHighlight : 0,
		startView : 0,
		minView : 0,
		maxView : 1,
		// 		startDate:new Date(),
		forceParse : 0
	});
	$('[name=begintime],[name=endtime]').timepicker({
		showMeridian : false,
		showInputs : true,
		defaultTime : false,
		format:'H:I',
		minuteStep : 1
	});

	$('.form_date,.form_time').datetimepicker().on('changeDate', function(ev) {
		$(this).find('span').last().hide();
	});
</script>
</html>