<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		<c:if test="${empty resource.id}">
			新增资源
		</c:if>
		<c:if test="${not empty resource.id}">
			编辑资源
		</c:if>
	</title>
	<style type="text/css">
		ul,li{list-style: none;padding: 0px;margin: 0px;}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<c:if test="${empty resource.id}">
					新增资源
				</c:if>
				<c:if test="${not empty resource.id}">
					编辑资源
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/admin/resedit">
			<input type="hidden" name="id" value="${resource.id}">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>资源类型</label>
					<select name="tagname">
<%-- 						<option value="TransCompany" <c:if test="${resource.tagname eq 'CarModel' }">selected</c:if> >运输公司</option> --%>
						<option value="CarModel" <c:if test="${resource.tagname eq 'CarModel' }">selected</c:if> >车型</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>名称</label>
					<input type="text" name="tagsubvalue" class="span3"  value="${ resource.tagsubvalue}" datatype="*" nullmsg="名称不能为空"/>				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>简称</label>
					<input type="text" name="shortname" class="span3"  value="${ resource.shortname}" datatype="*" nullmsg="简称不能为空"/>				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>排序</label>
					<input type="text" name="tagorder" value="${ empty resource.tagorder ? 1:resource.tagorder}" class="span3" datatype="n" nullmsg="手机号码不能为空">				
				</div>
			</div>
			<div class="row">
				<div class="span2" style="margin-top:8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top:8px;">
					<input type="button" onclick="history.go(-1)"  class="btn" value="返回">
				</div>
			</div>
	<%@include file="../common/footer.jsp" %>
	</form>
	</div>
</body>
<script type="text/javascript">
$(function(e){
	window.ishide = 1;
	//提交表单
	$("form").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			return true;
		},
		beforeSubmit:function(curform){
			
		},
		callback:function(data){
// 			console.debug(data);
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/admin/reslist"
			}else if(data==-100){
				layer.msg('该记录已经存在');
			}else{
				layer.msg('操作失败');
			}
		}
	});
	
});
</script>
</html>