<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>线路人数统计</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/statisticShiftStartByLine" method="get">
			<div class="page-header">
				<h2>线路人数统计</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
                    <span>开始时间：</span><input name="begindate" placeholder="开始时间" size="16" type="text" value="${page.param.begindate }" readonly="readonly" class="date _date"/>
                    <span>结束时间：</span><input name="enddate" placeholder="结束时间" size="16" type="text" value="${page.param.enddate }" readonly="readonly" class="date _date"/>
<%-- 					<input type="text" name="mobile" value="${page.param.mobile }" --%>
<!-- 						placeholder="订单号/身份证/手机号码"  style="height:30px;">  -->
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[name=export]').val(0);$(this).parents('form')[0].submit();">搜索</a>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[name=export]').val(1);$(this).parents('form')[0].submit();">导出</a>
					<input type="hidden" value="0" name="export"/>
				</div>
			</div>
			<br/>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>线路号</th>
						<th>线路</th>
						<th>始发站</th>
						<th>终点站</th>
						<th>总人数</th>
						<th>全票</th>
						<th>半票</th>
						<th>免票</th>
						<th>托运数量</th>
						<th>托运金额</th>
						<th>乘客超重件数</th>
						<th>乘客超重金额</th>
<!-- 						<th class="pull-left">操作</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.lineid }</td>
							<td>${o.linename }</td>
							<td>${o.ststartname }</td>
							<td>${o.starrivename }</td>
							<td>${o.allpeople }</td>
							<td>${o.allticketnum }</td>
							<td>${o.halfticketnum }</td>
							<td>${o.freeticketnum }</td>
							<td>${o.consignquantity }</td>
							<td>${o.consignsum }</td>
							<td>${o.passengerquantity }</td>
							<td>${o.passengersum }</td>
<!-- 							<td> -->
<!-- 								<div class="btn-group pull-left"> -->
<%-- 									<a class="btn" href="${basePath}/user/saleorderdetail?id=${o.id}"> --%>
<!-- 										 详情 -->
<!-- 									</a>  -->
<!-- 								</div> -->
<!-- 							</td> -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<%@include file="../common/page.jsp"%>
		</form>
	</div>

	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">

$(function(){
	$('[name=begindate]').datetimepicker().on('changeDate',function(ev){
		$('[name=enddate]').datetimepicker('setStartDate', ev.date);
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
</script>
</html>
