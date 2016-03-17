<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
<!-- 	<script type="text/javascript" src="<%=basePath%>/js/md5.js"></script> -->
<!-- 	<script type="text/javascript" src="<%=basePath%>/js/adminAdd.js"></script> -->
	<title>
		<c:if test="${empty cityStation.id}">
			新增城市
		</c:if>
		<c:if test="${not empty cityStation.id}">
			编辑城市
		</c:if>
	</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/admin/citystationlist.do" style="color: black"></a>
				<c:if test="${empty cityStation.id}">
					新增城市
				</c:if>
				<c:if test="${not empty cityStation.id}">
					编辑城市
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath }/admin/citystationedit.do">
			<input type="hidden" name="id" value="${cityStation.id }">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>城市名称</label>
					<input type="text" name="cityname" value="${cityStation.cityname }" class="span3" datatype="*" nullmsg="城市不能为空">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>城市拼音</label>
					<input type="text" name="stpinyin" value="${cityStation.stpinyin }" class="span3" readonly="readonly"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>是否热门</label>
					是:<input type="radio" value="1" name="ishot" ${cityStation.ishot == 1 ? 'checked':'' } class="span1">
					否:<input type="radio" value="0" name="ishot" ${empty cityStation or cityStation.ishot != 1  ? 'checked':'' } class="span1">
				</div>
			</div>
			<div class="row">
				<div class="span2" style="margin-top:8px;">
					<input type="submit" class="btn" value="保存">
				</div>
				<div class="span2" style="margin-top:8px;">
					<input type="button" class="btn" value="返回" onclick="history.back()">
				</div>
			</div>
		</form>
	</div>
	<%@include file="../common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	$("form").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		
		},
		beforeSubmit:function(curform){
			
		},
		callback:function(data){
			if(data=="200"){
				layer.msg('保存成功');
				if("${cityStation.id}"==''){
					setTimeout(800,function(){
						location.reload();
					});
					return;
				}
				location.href = basePath+"/admin/citystationlist.do"
			}else if(data=="-1"){
				layer.msg('该城市已经存在');
			}else{
				layer.msg('操作失败');
			}
		}
	});
});
</script>
</html>
