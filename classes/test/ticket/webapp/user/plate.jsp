<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>车牌列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/platemanage.do" method="get">
			<div class="page-header">
				<h2>车牌列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="platenum" value="${page.param.platenum }"
						placeholder="车牌号码" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/plateadd.do">
						<i class="icon-plus-sign"></i>新增车牌
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>编号</th>
						<th>车牌</th>
						<th>核载</th>
						<th>车型</th>
						<th class="pull-left">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.id }</td>
							<td>${o.platenum }</td>
							<td>${o.nuclearload }</td>
							<td>${o.carmodelname }</td>
							<td>
								<div class="btn-group pull-left">
									<a class="btn" href="<%=basePath%>/user/plateadd.do?id=${o.id}">
										<i class="icon-pencil"></i> 编辑
									</a> 
<!-- 									<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id});"> -->
<!-- 										<i class="icon-remove"></i> 删除 -->
<!-- 									</a>  -->
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
// 			var url = basePath+"/user/delschedule.do";
// 			ajax({id:id,isDel:1}, url,function(json){
// 				location.reload(true);
// 			});
	});
}

$(function(){

});
</script>
</html>
