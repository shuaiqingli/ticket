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
			新增站点
		</c:if>
		<c:if test="${not empty cityStation.id}">
			编辑站点
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
					新增站点
				</c:if>
				<c:if test="${not empty cityStation.id}">
					编辑站点
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath }/admin/citystationedit.do">
			<input type="hidden" name="id" value="${cityStation.id }">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>所属城市</label>
					<select name="parentid" class="span3" datatype="*" nullmsg="所属城市不能为空">
						<c:forEach var="city" items="${citys}">
							<option value="${city.id }" <c:if test="${cityStation.parentid == city.id }">selected="selected"</c:if> >${city.cityname }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>站点名称</label>
					<input type="text" name="cityname" value="${cityStation.cityname }" class="span3" datatype="*" nullmsg="站点不能为空">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>简称</label>
					<input type="text" name="shortname" value="${cityStation.shortname }" class="span3" datatype="*" nullmsg="站点不能为空">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>站点地址</label>
					<input type="text" name="stationaddr" value="${cityStation.stationaddr }" class="span3" datatype="*" nullmsg="站点地址不能为空">				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>取票地址</label>
					<input type="text" name="ticketaddr" value="${cityStation.ticketaddr }" class="span3" datatype="*" nullmsg="取票地址不能为空">				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>站点拼音</label>
					<input type="text" name="stpinyin" value="${cityStation.stpinyin }" class="span3" readonly="readonly"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>默认卖票百分比</label>
					<input type="text" name="ticketpercent" value="${empty cityStation.ticketpercent?100:cityStation.ticketpercent }" class="span3" datatype="percent" maxlength="3" />		%			
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>默认优惠票百分比</label>
					<input type="text" name="couponticketpercent" value="${empty cityStation.couponticketpercent?100:cityStation.couponticketpercent }" class="span3" datatype="percent"/>	%			
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>默认优惠金额百分比</label>
					<input type="text" name="couponpercent" value="${empty cityStation.couponpercent?100:cityStation.couponpercent }" class="span3" datatype="percent"/>	%			
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
				var val = $('[name=parentid]').val();
				$("[name=cityName],[name=stationaddr],[name=ticketaddr]").val('');
				if("${cityStation.id}"==''){
					location.reload();
					return;
				}
// 				cityName stationaddr ticketaddr
				if(val){
					location.href = basePath+"/admin/citystationlist.do?parentid="+val+"&isstation=1&ishot=1"
				}else{
					location.href = basePath+"/admin/citystationlist.do"
				}
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
