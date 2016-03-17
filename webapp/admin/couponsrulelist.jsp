<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>管理员列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/couponsrulelist.do" method="get">
			<div class="page-header">
				<h2>优惠券规则列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="keywords" value="${page.param.keywords}"
						placeholder="名称" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/couponsruletoadd.do">
						<i class="icon-plus-sign"></i>新增优惠券规则
				  </a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>编号</th>
						<th>规则名称</th>
						<th>类型</th>
						<th>开始日期</th>
						<th>结束日期</th>
						<th>有效期</th>
						<th>面值</th>
						<th>是否启用</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="cr">
						<tr class="odd">
							<td height="18">${cr.id}</td>
							<td>${cr.rulename}</td>
							<td>${cr.vsortname}</td>
							<td>${cr.begindate}</td>
							<td>${cr.enddate}</td>
							<td>${cr.validday}</td>
							<td>${cr.vprice}</td>
							<td>${cr.isenablename}</td>
							<td>
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/admin/couponsruletoedit.do?id=${cr.id}">
										<i class="icon-pencil"></i> 编辑
									</a> 
                                    <a class="btn btn-danger deleteLine" href="<%=basePath%>/admin/couponsruledel.do?id=${cr.id}">
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
</html>
