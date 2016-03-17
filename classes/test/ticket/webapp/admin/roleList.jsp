<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/roleList.js"></script>
	<title>角色列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/rolelist.do" method="get">
			<div class="page-header">
				<h2>角色列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="roleName" value="${page.param.rolename }"
						placeholder="角色名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/roleadd.do">
						<i class="icon-plus-sign"></i>新增角色
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>角色名称</th>
						<th>包含权限</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${roles }" var="role">
						<tr class="odd">
							<td>${role.rolename }</td>
							<td>
								<c:forEach items="${role.menus }" var="menu" varStatus="status">
									<a href="javascript:void(0)">${menu.menuName }</a> <c:if test="${!status.last }">|</c:if><c:if test="${status.count%5==0 }"><br></c:if>
								</c:forEach>
							</td>
							<td>
								<div class="btn-group pull-right">
									<a class="btn remove_role" href="javascript:;" roleid="${role.id}">
										<i class="icon-remove"></i> 删除
									</a>
									<a class="btn" href="<%=basePath%>/admin/roleadd.do?id=${role.id }">
										<i class="icon-pencil"></i> 编辑
									</a>
<!-- 									<a class="btn btn-danger" href="javascript:void(0)" onclick="delRole(${role.id});"> -->
<!-- 										<i class="icon-trash icon-white"></i> 删除 -->
<!-- 									</a> -->
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
		$(".remove_role").bind("click",function(){
			var $this = $(this);
			layer.confirm("你确定要删除该角色吗？对使用过的角色无效",function(){
				var params = {};
				params.id = $this.attr("roleid");
//				console.debug(params);
				ajax(params,basePath+"/admin/deleteRole.do",function(data){
					location.reload();
				});
			})
		});

		$("tr").bind("click",function(){
			$("#transmark").remove();
		});
	});
</script>
</html>
