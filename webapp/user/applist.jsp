<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>APP列表</title>
	<style type="text/css">
		.span2{
			color: #1C2C2C;
		}
		.span2 span{
			color: #3C2C2C;
		}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/shiftlist" method="get">
			<div class="page-header">
				<h2>APP列表</h2>
			</div>
			<div class="pull-right" style="padding-right:10px;">
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<%--<input type="text" name="shiftcode" value=""--%>
						<%--placeholder=""  style="height:30px;">--%>
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="${basePath}/user/appupload.jsp">上传APP</a>
				</div>
			</div>
			<br/>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
				<tr>
						<th>名称</th>
						<th>版本</th>
						<th>描述</th>
						<th>上传日期</th>
						<th>类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd tr">
							<td>${o.appname}</td>
							<td>${o.versioncode}</td>
							<td>${o.softdesc}</td>
							<td>${o.makedate}</td>
							<td>${o.sort==0?'站务APP':'客户端APP'}</td>
							<td>
								<a class="" href="${o.fileurl}">
									下载
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
</script>
</html>
