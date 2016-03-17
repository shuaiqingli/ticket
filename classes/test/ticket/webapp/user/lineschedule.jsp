<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>线路列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/lineschedule.do" method="get">
			<div class="page-header">
				<h2>排班列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="lineName" value="${page.param.lineName }"
						placeholder="线路/站点/公司名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/linescheduleadd.do">
						<i class="icon-plus-sign"></i>新增排班
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th class="hide">线路编号</th>
						<th>线路</th>
						<th>始发站</th>
						<th>到达站</th>
						<th>运输公司</th>
						<th>开始时间</th>
						<th>间隔(分钟)</th>
						<th>生效时间</th>
						<th>失效时间</th>
						<th>班次数量</th>
						<th class="pull-left">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td class="hide">${o.lineManage.lineID }</td>
							<td>${o.lineManage.lineName }</td>
							<td>${o.lineManage.sTStartName }</td>
							<td>${o.lineManage.sTArriveName }</td>
							<td>${o.lineManage.transCompany }</td>
							<td>${o.schedueTime }</td>
							<td>${o.intervalMinute }</td>
							<td>${o.begindate }</td>
							<td>${o.enddate }</td>
							<td>${o.shiftNum }</td>
							<td>
								<div class="btn-group pull-left">
									<a class="btn" href="<%=basePath%>/user/linescheduleadd.do?id=${o.id}">
										<i class="icon-pencil"></i> 编辑
									</a> 
									<a class="btn" href="<%=basePath%>/user/linepricedetail.do?lineId=${o.lineManage.lineID}">
										编辑价格
									</a> 
									<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id});">
										<i class="icon-remove"></i> 删除
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

//删除
function del(id){
	layer.confirm('确定删除该线路吗？', 
		{
		    btn: ['确定', '取消']
		},function(index, layero){
			var url = basePath+"/user/delschedule.do";
			ajax({id:id,isDel:1}, url,function(json){
				location.reload(true);
			});
	});
}

$(function(){

});
</script>
</html>
