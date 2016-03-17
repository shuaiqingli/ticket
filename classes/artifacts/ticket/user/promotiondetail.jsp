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
					style="color: black"></a> 促销规则详情
			</h2>
		</div>
		<form method="post" id="adminForm"
			action="${basePath}/admin/promotionedit.do">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>规则名称</label> 
                    <label>${p.promotionname}</label>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>开始日期</label>
					<label>${p.begindate}</label>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>结束日期</label>
                    <label>${p.enddate}</label>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;" id="weekdayList">
                <input type="hidden" name="weekdays" datatype="*"
						nullmsg="可选星期不能为空" value="${p.weekdays}"><label>可选星期
					<span style="color: gray; font-size: 12px;">(未选中表示不生效)</span>
				  </label> <label class="checkbox inline"> <input id="mon"
						type="checkbox" value="1" onClick="selectWeekday(this);" disabled><span>周一</span>
					</label> <label class="checkbox inline"> <input id="tues"
						type="checkbox" value="2" onClick="selectWeekday(this);" disabled><span>周二</span>
					</label> <label class="checkbox inline"> <input id="wed"
						type="checkbox" value="4" onClick="selectWeekday(this);" disabled><span>周三</span>
					</label> <label class="checkbox inline"> <input id="thur"
						type="checkbox" value="8" onClick="selectWeekday(this);" disabled><span>周四</span>
					</label> <label class="checkbox inline"> <input id="fri"
						type="checkbox" value="16" onClick="selectWeekday(this);" disabled><span>周五</span>
					</label> <label class="checkbox inline"> <input id="set"
						type="checkbox" value="32" onClick="selectWeekday(this);" disabled><span>周六</span>
					</label> <label class="checkbox inline"> <input id="sun"
						type="checkbox" value="64" onClick="selectWeekday(this);" disabled><span>周日</span>
					</label>
				</div>
			</div>
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
			<div class="row">
				<div style="margin-left: 30px;">
					<label>时间段</label>
                    <c:forEach items="${tlist}" var="t">
                    <label>${t.begintime} - ${t.endtime} 优惠幅度 ${t.reducesum}</label>
                    </c:forEach>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					启用 是: <input name="isenable" type="radio" class="span1" value="1" disabled
						<c:if test="${p.isenable==1}">checked</c:if> /> 否:<input
						type="radio" value="0" name="isenable" class="span1" disabled
						<c:if test="${p.isenable==0}">checked</c:if> />
				</div>
			</div>
            
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
					location.href = basePath + "/admin/promotionlist.do"
				} else if (data == -100) {
					layer.msg('该记录已经存在');
				} else {
					layer.msg('操作失败');
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

	$('.form_date,.form_time').datetimepicker().on('changeDate', function(ev) {
		$(this).find('span').last().hide();
	});
</script>
</html>