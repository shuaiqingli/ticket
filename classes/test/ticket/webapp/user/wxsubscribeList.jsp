<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>关注推送消息</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/user/wxsubscribeMsgList" method="get">
			<div class="page-header ishide">
				<h2>推送消息列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="title" value="${page.param.title }"
						placeholder="标题" value="" style="height:30px;">
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right ishide" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/user/wxsubscribeAdd">
						<i class="icon-plus-sign"></i>新增消息
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>标题</th>
						<th>描述</th>
						<th>级别</th>
						<th>创建日期</th>
						<th>链接</th>
						<th class="ishide pull-right">&nbsp;&nbsp;操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="o">
						<tr class="odd" style="color: ${o.issend==0?'#999':''};">
							<td>${o.title}</td>
							<td style="max-width: 280px;">${o.description}</td>
							<td>${o.rank}</td>
							<td>${o.makedate}</td>
							<td>
								<a href="${o.picurl}" target="_blank">图片</a>|
								<a href="${o.url}" target="_blank">文章</a>
							</td>
							<td>
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/user/wxsubscribeAdd?id=${o.id}">
										<i class="icon-pencil"></i> 编辑
									</a>
									<a class="btn cancel" href="javascript:void(0)" id="${o.id}" issend="${o.issend}">
										${o.issend==0?'恢复推送':'取消推送'}</a>
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

	$(".cancel").bind("click",function(){
		var params = {};
		params.id = $(this).attr("id");
		params.issend = $(this).attr("issend")=='0'?1:0;
		ajax(params,basePath+"/user/wxsubscribeEdit",function(data){
			if(data>=1){
				layer.msg('操作成功');
				location.reload();
			}else if(data==-100){
				layer.msg('该记录已经存在');
			}else{
				layer.msg('操作失败');
			}
		});
	});
});
</script>
</html>
