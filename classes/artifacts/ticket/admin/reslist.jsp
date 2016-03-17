<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>资源列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/reslist" method="get">
			<div class="page-header">
				<h2 onclick="location.href='${basePath}/admin/reslist'">
				<span style="color: #000;">
				</span>资源列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<select name="tagname" class="span2">
						<option value="">--- 全部 ---</option>
						<option value="CarModel" <c:if test="${page.param.tagname eq 'CarModel' }">selected</c:if> >车型</option>
<%-- 						<option value="TransCompany" <c:if test="${page.param.tagname eq 'TransCompany' }">selected</c:if> >公司</option> --%>
					</select>
					<input type="text" name="tagsubvalue" value="${page.param.tagsubvalue}"
						placeholder="名称" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right cls_hide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/resadd">
						<i class="icon-plus-sign"></i>新增资源
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>名称</th>
						<th>简称</th>
						<th>排序</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.tagsubvalue }</td>
							<td>${o.shortname }</td>
							<td>${o.tagorder }</td>
							<td class="cls_hide">
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/admin/resadd?id=${o.id}">
										<i class="icon-pencil "></i> 编辑
									</a> 
									<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id})">
										<i class="icon-remove "></i> 删除
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
	layer.confirm('确定删除该记录吗？', 
		{
		    btn: ['确定', '取消']
		},function(index, layero){
			var url = basePath+"/admin/resedit";
			ajax({id:id,isdel:1}, url,function(json){
				location.reload(true);
			});
	});
}
</script>
</html>
