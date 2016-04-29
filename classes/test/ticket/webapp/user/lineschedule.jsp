<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp" %>
	<script type="text/javascript" src="<%=basePath%>/js/lineScheduleRule.js"></script>
	<title>排班</title>
</head>
<script type="text/javascript">
</script>
<script type="text/javascript">

	$(function(){

		$('[name=begindate],[name=enddate]').datetimepicker({
			language:  'zh-CN',
			format:'yyyy-mm-dd',
			weekStart: 1,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startDate:new Date(),
			startView: 2,
			minView: 2,
			forceParse: 0
		});

		$('[name=begindate]').datetimepicker().on('changeDate',function(ev){
			$('[name=enddate]').datetimepicker('setStartDate', ev.date);
		});

		$('.allchoose').click(function () {
			$('.live').find('[type=checkbox]').prop("checked",$(this).prop("checked"))
		});
	})

</script>
<body>
<div class="container main_container" style="height:auto;">
	<form action="${basePath}/user/getScheduleTask" method="get">
		<div class="pull-left" style="padding-right:10px;">
			<input name="lmid" value="${param.lmid}" type="hidden">
			<span>排班日期：</span>
			<input  style="width: 120px;" name="begindate" placeholder="开始日期" size="16" value="${begindate}" readonly="readonly" class="" type="text"/>
			&nbsp;至&nbsp;
			<input style="width: 120px;" name="enddate" placeholder="结束日期" size="16" value="${enddate}" readonly="readonly" class="" type="text"/>
			<a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">确定</a>
		</div>
		<div style="clear: both;"></div>
		<hr/>
		<table class="table table-striped" style="font-size: 14px;">
			<thead>
				<tr>
					<th>日期</th>
					<th>星期</th>
					<th>线路规则</th>
					<th>排班规则</th>
					<th>状态</th>
					<th style="position: relative">是否排班 <input type="checkbox" class="allchoose" style="top: 7px;position: absolute;left:70px;" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="task" items="${tasks}">
					<tr class="${task.ticketstatus==1?'disabled':'live'}">
						<td class="date">${task.date}</td>
						<td class="weekday">${task.weekday}</td>
						<td>
							<select ${task.ticketstatus==1?'disabled':''} class="lrid">
								<c:forEach var="rule" items="${linerules}">
									<option ${task.lrid==rule.id?'selected':''} value="${rule.id}">${rule.rulename}${rule.isdefault==1?'(默认规则)':''}</option>
								</c:forEach>
							</select>
							<input type="hidden" class="old_lrid" value="${task.lrid}"/>
						</td>
						<td>
							<select  ${task.ticketstatus==1?'disabled':''} class="lsid">
								<c:forEach var="rule" items="${scheduerules}">
									<option ${task.lsid==rule.id?'selected':''}  value="${rule.id}">${rule.scheduname}${rule.isdefault==1?'(默认规则)':''}</option>
								</c:forEach>
							</select>
							<input type="hidden" class="old_lsid" value="${task.lsid}" />
						</td>
						<td >
                            ${empty task.ticketstatus?'未排班':not empty task.tplid and task.ticketstatus == 0 ?'未发布':
                            task.ticketstatus==1?'已发布':task.ticketstatus == 0 ?'未出票':''}
						</td>
						<td style="padding-left: 70px;">
							<input type="checkbox" ${task.ticketstatus==1?'disabled':''} checked />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</form>
</div>
</body>
</html>
