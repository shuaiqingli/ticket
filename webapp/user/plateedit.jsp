<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		<c:if test="${empty ls.id}">
			新增车牌
		</c:if>
		<c:if test="${not empty ls.id}">
			编辑车牌
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
				<a id="main_page" href="<%=basePath%>/user/platemanage.do" style="color: black"></a>
				<c:if test="${empty plate.id}">
					新增车牌
				</c:if>
				<c:if test="${not empty plate.id}">
					编辑车牌
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/user/editplate.do">
			<input type="hidden" name="id" value="${plate.id}">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>车牌</label>
					<input type="text" name="platenum" value="${plate.platenum}" class="span3" datatype="*" nullmsg="车牌不能为空">				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>核载</label>
					<input type="text" name="nuclearload" class="span3" value="${plate.nuclearload}" datatype="n" nullmsg="核载不能为空"/>				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>车型</label>
					<select class="span3" name="carmodelid" datatype="n" nullmsg="车型不能为空">
						<c:forEach items="${cars}" var="car">
							<option value="${car.id}" <c:if test="${car.id == plate.carmodelid}">selected</c:if> >${car.tagsubvalue}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<input type="hidden" name="carmodelname" value="${plate.carmodelname}"/>
			<div class="row">
				<div class="span2" style="margin-top:8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top:8px;">
					<input type="button" onclick="history.go(-1)"  class="btn" value="返回">
				</div>
			</div>
	<%@include file="../common/footer.jsp" %>
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 800px;height: 540px;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">选择线路</h3>
  </div>
  <div class="modal-body">
		<iframe id="lineManage" name="lineManage" scrolling="yes"  src="${basePath }/user/linemanage.do?ishide=1&isApprove=1" style="height: 400px;width: 770px;border: none;overflow: hidden; ">
		</iframe>
  </div>
  <div class="modal-footer">
    <button class="btn  btn-primary chooseLineManage" data-dismiss="modal" aria-hidden="true">确定</button>
  </div>
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
			console.debug(data);
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/user/platemanage.do"
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