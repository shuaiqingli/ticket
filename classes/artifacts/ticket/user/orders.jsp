<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>订单列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/saleorderlist" method="get">
			<div class="page-header">
				<h2>订单列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
<!-- 					<select style="width: 140px;" name="payStatus" > -->
<!-- 						<option value="">--- 支付状态 ---</option> -->
<%-- 						<option value="0"  <c:if test="${page.param.payStatus==0 }">selected</c:if> >未支付</option> --%>
<%-- 						<option value="1" <c:if test="${page.param.payStatus==1 }">selected</c:if>>已支付</option> --%>
<!-- 					</select> -->
					<select style="width: 80px;" name="status">
						<option value="">-状态-</option>
						<option value="0" <c:if test="${page.param.status==0 }">selected</c:if>>未取票</option>
						<option value="1" <c:if test="${page.param.status==1 }">selected</c:if>>已取票</option>
						<option value="2" <c:if test="${page.param.status==2 }">selected</c:if>>退款中</option>
						<option value="3" <c:if test="${page.param.status==3 }">selected</c:if>>已退款</option>
						<option value="4" <c:if test="${page.param.status==4 }">selected</c:if>>已取消</option>
					</select>
					<select name="rank"style="width: 80px;">
						<option value="">-级别-</option>
						<option value="0" <c:if test="${page.param.rank==0 }">selected</c:if> >普通
						</option>
						<option value="1" <c:if test="${page.param.rank==1 }">selected</c:if> >高级
						</option>
						<option value="2" <c:if test="${page.param.rank==2 }">selected</c:if> >站务
						</option>
					</select>
                    <span>开始时间：</span><input style="width: 120px;" name="begindate" placeholder="开始时间" size="16" type="text" value="${page.param.begindate }" readonly="readonly" class="date _date"/>
                    <span>结束时间：</span><input style="width: 120px;" name="enddate" placeholder="结束时间" size="16" type="text" value="${page.param.enddate }" readonly="readonly" class="date _date"/>
                    <span>出行日期：</span><input style="width: 120px;" name="rideDate" placeholder="出行日期" size="16" type="text" value="${page.param.rideDate }" readonly="readonly" class="date _date"/>
					<input type="hidden" value="0" name="export"/>
					<input type="text" name="mobile" value="${page.param.mobile }"
						placeholder="订单号/身份证/手机号码/班次号/线路号"  style="height:30px;">
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[name=export]').val(0);$(this).parents('form')[0].submit();">搜索</a>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[type=text]').val('')">清空</a>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$('[name=export]').val(1);$(this).parents('form')[0].submit();">导出</a>
				</div>
			</div>
			<br/>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>订单号</th>
<!-- 						<th>取票码</th> -->
						<th>线路</th>
						<th>班次号</th>
						<th>出发站</th>
						<th>到达站</th>
						<th>出发日期</th>
						<th>取票人</th>
						<th>手机号码</th>
						<th>数量</th>
						<th>总价格</th>
						<th>下单日期</th>
						<th>状态</th>
						<th class="pull-left">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.id }</td>
							<td>${o.lineName }</td>
							<td>
							${o.shiftNum}</td>
							<td>${o.sTStartName }</td>
							<td>${o.sTArriveName }</td>
							<td>${o.rideDate }</td>
							<td>${o.LName }</td>
							<td><a href="<%=basePath%>/admin/customerList.do?cname=${o.LMobile}">${o.LMobile }</a></td>
							<td>${o.quantity }</td>
							<td>${o.totalSum }</td>
							<td>
								${o.makeDate }(提前${o.daysForAdvance}天)
							</td>
							<td>${o.statusName }</td>
							<td>
								<div class="btn-group pull-left">
									<a class="btn" href="${basePath}/user/saleorderdetail?id=${o.id}">
										 详情
									</a>
								</div>
							</td>
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
