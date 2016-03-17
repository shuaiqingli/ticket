<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<%@ include file="../common/head.jsp"%>
<title>新增优惠券规则</title>
<style type="text/css">
ul, li {
	list-style: none;
	padding: 0px;
	margin: 0px;
}
</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page"
					href="<%=basePath%>/admin/mileageconvertrulelist.do"
					style="color: black"></a> 新增里程兑换规则
			</h2>
		</div>
		<form method="post" id="adminForm"
			action="<%=basePath%>/admin/mileageconvertruleadd.do">

			<div class="row">
				<div style="margin-left: 30px;">
					<label>兑换里程</label>
                    <input name="mileagecvt" type="text" class="span3"
						id="mileagecvt" datatype="*" nullmsg="规则名称不能为空" />
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>换到优惠券</label>
                     <input name="vprice" type="text" class="span3" id="vprice"
						datatype="n" nullmsg="有效期不能为空"> 元
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					启用 是: <input name="isenable" type="radio" class="span1" value="1"
						checked="CHECKED" /> 否:<input type="radio" value="0"
						name="isenable" class="span1" />
				</div>
			</div>
			<div class="row">
				<div class="span2" style="margin-top: 8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top: 8px;">
					<input type="button" onclick="history.go(-1)" class="btn"
						value="返回">
				</div>
			</div>
			<%@include file="../common/footer.jsp"%>
</body>
<script type="text/javascript">
	$(function(e) {
		form = $("form").Validform(
				{
					tiptype : 4,
					postonce : true,
					ajaxPost : true,
					beforeCheck : function(curform) {
						return true;
					},
					beforeSubmit : function(curform) {

					},
					callback : function(data) {
						console.debug(data);
						if (data >= 1) {
							layer.msg('操作成功');
							location.href = basePath
									+ "/admin/mileageconvertrulelist.do"
						} else if (data == -100) {
							layer.msg('该记录已经存在');
						} else {
							layer.msg('操作失败');
						}
					}
				});

	});
</script>

</html>