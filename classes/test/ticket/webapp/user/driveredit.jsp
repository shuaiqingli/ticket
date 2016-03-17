<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		<c:if test="${empty driver.id}">
			新增司机
		</c:if>
		<c:if test="${not empty driver.id}">
			编辑司机
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
				<a id="main_page" href="<%=basePath%>/user/drivermanage.do" style="color: black"></a>
				<c:if test="${empty driver.id}">
					新增司机
				</c:if>
				<c:if test="${not empty driver.id}">
					编辑司机
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/user/editdriver.do">
			<input type="hidden" name="issave" value="${empty driver.id?1:0}">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>员工编号</label>
					<input type="text" ${empty driver.id?'':'readonly'} name="id" class="span3" placeholder="工号" value="${driver.id}" datatype="*" nullmsg="员工不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>姓名</label>
					<input type="text" name="dname" class="span3" placeholder="姓名" value="${driver.dname}" datatype="*" nullmsg="姓名不能为空"/>
				</div>
			</div>

			<div class="row">
				<div style="margin-left: 30px;">
					<label>手机号码</label>
					<input type="text" name="dmobile" value="${driver.dmobile }" class="span3" datatype="m" nullmsg="手机号码不能为空">				
				</div>
			</div>
			<c:if test="${empty driver.id }">
				<div class="row">
					<div style="margin-left: 30px;">
						<label>密码</label>
						<input type="password" name="password" value="${driver.password }" class="span3" datatype="*" nullmsg="密码不能为空" <c:if test="${driver.password != null}">isEncrypt=true</c:if> >
					</div>
				</div>
				<div class="row">
					<div style="margin-left: 30px;">
						<label>确认密码</label>
						<input type="password" name="password2" value="${driver.password }" class="span3" datatype="*" recheck="password" nullmsg="确认密码不能为空" errormsg="两次密码输入不一致" <c:if test="${driver.password != null}">isEncrypt=true</c:if>>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>身份证号码</label>
					<input type="text" name="idcard" value="${driver.idcard }" class="span3" datatype="/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/" nullmsg="身份证不能为空">				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>停用</label>
					是:<input type="radio" value="1" name="isstop" <c:if test="${driver.isstop == 1}">checked="checked"</c:if>  class="span1"/>				
					否:<input type="radio" value="0" name="isstop" <c:if test="${empty driver.isstop}">checked="checked"</c:if> class="span1"/>				
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
				location.href = basePath+"/user/drivermanage.do"
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