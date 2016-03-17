<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>退票规则列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/returnedRuleList" method="get">
			<div class="page-header ishide">
				<h2>退票规则列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					 <span>时间：</span><input name="date" placeholder="时间" size="16" type="text" value="${page.param.date }" readonly="readonly" class="date _date"/>
<%--                     <span>结束时间：</span><input name="enddate" placeholder="结束时间" size="16" type="text" value="${page.param.enddate }" readonly="readonly" class="date _date"/> --%>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right ishide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/returnedRuleEdit">
						<i class="icon-plus-sign"></i>新增规则
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>开始日期</th>
						<th>结束日期</th>
						<th>会员级别</th>
<!-- 						<th>制单人</th> -->
						<th>制单日期</th>
						<th>时间段</th>
						<th>线路编号</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.begindate }</td>
							<td>${o.enddate }</td>
							<td>${o.rank==0?'普通':o.rank==1?'高级':'站务' }</td>
<%-- 							<td>${o.makename }</td> --%>
							<td>${o.makedate }</td>
							<td>
								<c:forEach var="time" items="${fn:split(o.time,',') }">
									提前：${fn:split(time,'#')[0]}小时退票，收取 ${fn:split(time,'#')[1]}%手续费
									<br/>
								</c:forEach>
							</td>
							<td>
								<c:if test="${not empty o.line }">
									<c:forEach var="line" items="${fn:split(o.line,',') }" varStatus="status">
										<a href="javascript:;;">${line}</a>|
										<c:if test="${(status.index+1)%5==0 and status.index != 0 }">
											<br/>
										</c:if>
									</c:forEach>
								</c:if>
							</td>
							<td></td>
							<td>
								<a class="btn" href="<%=basePath%>/user/returnedRuleEdit?id=${o.id}">
									<i class="icon-pencil"></i>
								</a>
								<a class="btn btn-danger deleterule" href="javascript:;;" id="${o.id}">
									<i class="icon-trash icon-white"></i>
								</a>
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
	$('[name=date]').datetimepicker();
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

$(function(){
	$('.deleterule').click(function(){
		var params = {};
		params.id = $(this).attr('id');
		layer.confirm('你确定要删除该规则以及解绑该规则的所有线路吗？',function(){
			ajax(params,basePath+"/user/deleteRefundRule",function(json){
         	   if(json>=1){
         		   layer.msg('操作成功！');
         		   location.reload();
         	   }else{
         		   layer.msg('操作失败！');
         		   throw 'error';
         	   }
            });
		});
	});	
});

</script>
</html>
