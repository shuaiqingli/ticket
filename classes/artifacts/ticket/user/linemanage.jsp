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
		<form action="<%=basePath%>/user/linemanage.do" method="get">
			<div class="page-header ishide">
				<h2>线路列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="hidden" name="ishide" value="${param.ishide}" />
					<input type="text" name="linename" value="${page.param.linename }"
						placeholder="线路号/公司名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right ishide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/lineadd.do">
						<i class="icon-plus-sign"></i>新增线路
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th class="isshow" style="display: none;">选择</th>
						<th class="">线路编号</th>
						<th>线路</th>
						<th>运输公司</th>
						<th>已排班日期</th>
						<th>最后审核日期</th>
						<th class="ishide pull-right">&nbsp;&nbsp;操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td class="isshow" style="display: none;"><input type="radio" name="lmid" value="${o.id}" /></td>
							<td class="">${o.lineid }</td>
							<td class="lineName">${o.linename }</td>
							<td>${o.transcompany }</td>
							<td>
								<c:if test="${not empty o.lineSchedues  and fn:length(o.lineSchedues)!=0 }">
									<c:if test="${not empty o.lineSchedues[0].begindate and not empty o.lineSchedues[0].enddate}">
										${o.lineSchedues[0].begindate } 至 ${o.lineSchedues[0].enddate}
									</c:if>
								</c:if>
							</td>
							<td>
								${o.lastshiftdate}
							</td>
							<td class="ishide">
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/user/lineadd.do?id=${o.id}">
										<i class="icon-pencil"></i> 编辑
									</a>
									<a class="btn btn-danger deleteLine" href="javascript:void(0)" id="${o.id}" groupid="${o.groupid}">
										<i class="icon-remove icon-white"></i> 删除
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

	$(".deleteLine").bind("click",function(){
		var $this = $(this);
		layer.confirm('确定删除该线路吗？', {
		    btn: ['确定', '取消']
		}, function(index, layero){
			var id = $this.attr("groupid");
			var json = {groupid:id};
			ajax(json,basePath+"/user/deleteLineManage",function(){
				layer.msg("删除成功！");
				setTimeout(function () {
					location.reload();
				},500);
			});
		});
		
	});
});
</script>
</html>
