<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>修改优惠券规则</title>
	<style type="text/css">
		ul,li{list-style: none;padding: 0px;margin: 0px;}
	</style>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/admin/couponsrulelist.do" style="color: black"></a>
				修改优惠券规则
		  </h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/admin/couponsruleedit.do">
			
			<div class="row">
				<div style="margin-left: 30px;">
					<label>规则名称</label>
					<input name="rulename" type="text" class="span3" id="rulename" value="${cr.rulename}" datatype="*" nullmsg="规则名称不能为空"/>				
				      <input type="hidden" name="id" id="id" value="${cr.id}">
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>券类型</label>
					<select id="vsort" onChange="" disabled="disabled">
					  <option value="1" <c:if test="${cr.vsort==1}">selected</c:if>>注册送券</option>
					  <option value="2" <c:if test="${cr.vsort==2}">selected</c:if>>满额送券</option>
					  <option value="3" <c:if test="${cr.vsort==3}">selected</c:if>>送红包</option>
                  </select>
				</div>
			</div>
			<input type="hidden"  value="${cr.vsort}" name="vsort"/>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>开始日期</label>
					<div class="input-append date ">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="begindate" size="16" type="text" value="${cr.begindate }" readonly datatype="*" nullmsg="开始时间不能为空" onChange="change()"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
			  <div style="margin-left: 30px;">
			    <label>结束日期</label>
				  <div class="input-append date">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="enddate" size="16" type="text" value="${cr.enddate }" readonly datatype="*" nullmsg="开始时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
             <div style="margin-left: 30px;">
                <label>有效期（天）</label>
                <input name="validday" type="text" class="span3" id="validday" value="${cr.validday}" datatype="n" nullmsg="有效期不能为空">
              </div>
            </div>
            
            <div class="row "  style="<c:if test="${cr.vsort==1}">display:none;</c:if>" >
             <div style="margin-left: 30px;">
               <label>满额可送券</label>
                <input name="buysum" type="text" class="span3" id="buysum" value="${cr.buysum}" datatype="d" nullmsg="满额可送券不能为空">
              </div>
            </div>
            
            <div class="row">
             <div style="margin-left: 30px;">
               <label>最低消费可用</label>
                <input name="lowlimit" type="text" class="span3" id="lowlimit" value="${cr.lowlimit}" datatype="d" nullmsg="最低消费不能为空">
              </div>
            </div>
            <div class="row">
             <div style="margin-left: 30px;">
               <label>面值</label>
                <input name="vprice" type="text" class="span3" id="vprice" value="${cr.vprice}" datatype="d" nullmsg="面值不能为空">
              </div>
            </div>
			<div class="row">
				<div style="margin-left: 30px;">启用 是:
				  <input name="isenable" type="radio" class="span1" value="1" <c:if test="${cr.isenable==1}">checked</c:if>/>				
					否:<input type="radio" value="0" name="isenable" class="span1" <c:if test="${cr.isenable==0}">checked</c:if>/>				
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

function change(){
	alert()
}

$(function(e){
	//window.ishide = 1;
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
			//alert(data);
			console.debug(data);
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/admin/couponsrulelist.do"
			}else if(data==-100){
				layer.msg('该记录已经存在');
			}else{
				layer.msg('操作失败');
			}
		}
	});
	
});
</script>
<script type="text/javascript">
	$('.form_date').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		startDate:new Date(),
		forceParse: 0,
		hide:function(){
			alert("1");
		},
		changeDate:function(){
			alert("2");	
		}
    });
	$('.form_time').datetimepicker({
	 	language:  'zh-CN',
	 	format:'hh:ii',
        weekStart: 0,
        todayBtn:  0,
		autoclose: 1,
		todayHighlight: 0,
		startView: 0,
		minView: 0,
		maxView: 1,
// 		startDate:new Date(),
		forceParse: 0
    });
   
    
</script>
</html>