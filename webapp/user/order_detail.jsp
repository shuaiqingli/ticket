<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		订单详情
	</title>
	<style type="text/css">
		ul,li{list-style: none;padding: 0px;margin: 0px;}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/user/saleorderlist" style="color: black"></a>
				订单详情
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/user/editdriver.do">
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>线路</th>
						<th>出发站</th>
						<th>到达站</th>
						<th>出发日期</th>
						<th>发车时间</th>
						<th>到达时间</th>
						<th>支付状态</th>
						<th>取票状态</th>
						<th>取票日期</th>
						<th>取票码</th>
						<th>班次号</th>
					</tr>
				</thead>
				<tbody>
					<tr class="odd">
						<td>${saleOrder.lineName }</td>
						<td>${saleOrder.sTStartName }</td>
						<td>${saleOrder.sTArriveName }</td>
						<td>${saleOrder.rideDate }</td>
						<td>${saleOrder.startTime }</td>
						<td>${saleOrder.arriveTime }</td>
						<td>${saleOrder.payStatusName}</td>
						<td>${saleOrder.statusName }</td>
						<td>${saleOrder.takedate }</td>
						<td>${saleOrder.ticketCode }</td>
						<td>${saleOrder.shiftNum }</td>
					</tr>
				</tbody>
			</table>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>订单号</th>
						<th>取票人</th>
						<th>手机号码</th>
						<th>身份证号码</th>
						<th>数量</th>
						<th>总金额</th>
						<th>实付金额</th>
						<th>单价</th>
						<th>下单日期</th>
					</tr>
				</thead>
				<tbody>
					<tr class="odd">
						<td>${saleOrder.id }</td>
						<td>${saleOrder.LName }</td>
						<td>${saleOrder.LMobile }</td>
						<td>${saleOrder.IDCode }</td>
						<td>${saleOrder.quantity }</td>
						<td>${saleOrder.totalSum }</td>
						<td>${saleOrder.payStatus ==1 ? saleOrder.actualSum:'0.00' }</td>
						<td>${saleOrder.price }</td>
						<td>${saleOrder.makeDate }</td>
					</tr>
				</tbody>
			</table>
			<br/>
			<h3>车票详情</h3>
			<hr/>
			
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th><input type="checkbox" class="allchoose" style="margin-top: -5px;">&nbsp;选择</th>
						<th>验票码</th>
						<th>上车状态</th>
						<th>是否优惠票</th>
						<th>座位号</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${details }" var="detail" >
						<tr class="odd">
							<td>
								<c:choose>
									<c:when test="${saleOrder.payStatus ==1 and detail.isontrain==0 and (detail.status == 0 or detail.status==6)}">
										<input  type="checkbox" class="ticket" style="margin-top: -5px;" value="${detail.checkcode}">
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								&nbsp;
							</td>
							<td>${detail.checkcode }</td>
							<td>${detail.isontrain==1?'已上车':'未上车'  }</td>
							<td>${detail.isdiscount==1?'是':'否' }</td>
							<td>${empty detail.seatno or detail.seatno ==0 ? '无':detail.seatno}</td>
							<td>
								<%--${detail.status}--%>
								${detail.status==0?'正常购票':detail.status==1?'退票中':detail.status==3?'已退款':detail.status==5?'拒绝退票':detail.status==6?'已过期':'' }
							</td>
<!-- 							0正常购票1退票3已退款5.拒绝退票 -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="row">
				<div class="btn-group pull-right">
					<a class="btn refundticket" href="javascript:;" >
						 退票
					</a> 
					<a class="btn" href="javascript:;" onclick="history.go(-1)">
						 返回
					</a> 
				</div>
			</div>
	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
var $cid = "${saleOrder.CID}";
$(function(){
	$('.allchoose').click(function(){
		$('[type=checkbox]').prop('checked',$(this).prop('checked'))
	});
	
	$('.refundticket').click(function(){
		if($('.ticket').length==0){
			layer.msg('没有可以退的车票！');
			return false;
		}
		var items  = $('.ticket:checked');
		var tickets = [];
		if(items.length==0){
			layer.msg('请选择你要退款的车票！');
			return false;
		}
		items.each(function(i,v){
			var id = $(v).val();
			tickets.push(id);
		});
		tickets = tickets.join(',');
		layer.confirm('您确定要退票吗？操作成功后请在退票管理操作退款！',function(){
			var params = {};
			params.checkcodes = tickets;
			params.cid = $cid;
			ajax(params,basePath+'/user/applyRefundTicket',function(json){
				console.debug(json);
				if(json>=1){
					layer.msg('操作成功！');
					location.reload();
				}else{
					layer.msg('申请退款失败！');
				}
			});
		});
	});
	
})
</script>
</html>