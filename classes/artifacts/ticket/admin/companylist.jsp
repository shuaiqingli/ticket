<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>公司列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/companylist" method="get">
			<div class="page-header">
				<h2 onclick="">
				<span style="color: #000;">
				</span>公司列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="companyname" value="${page.param.companyname}"
						placeholder="名称、拼音" value="" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right cls_hide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/companydetail">
						<i class="icon-plus-sign"></i>新增公司
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>公司名称</th>
						<th>公司简称</th>
						<th>拼音</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd">
							<td>${o.companyname }</td>
							<td>${o.shortname }</td>
							<td>${o.pyname }</td>
							<td class="cls_hide">
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/admin/companydetail?id=${o.id}">
										<i class="icon-pencil "></i> 编辑
									</a> 
<%-- 									<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id})"> --%>
<!-- 										<i class="icon-remove "></i> 删除 -->
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
