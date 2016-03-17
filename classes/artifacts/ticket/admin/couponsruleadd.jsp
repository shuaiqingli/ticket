<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>新增优惠券规则
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
				<a id="main_page" href="<%=basePath%>/admin/couponsrulelist.do" style="color: black"></a>
				新增优惠券规则
		  </h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/admin/couponsruleadd.do">
			
			<div class="row">
				<div style="margin-left: 30px;">
					<label>规则名称</label>
					<input name="rulename" type="text" class="span3" id="rulename" datatype="*" nullmsg="规则名称不能为空"/>				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>券类型</label>
					<select name="vsort" id="vsort" onChange="manSend();">
					  <option value="1">注册送券</option>
					  <option value="2">满额送券</option>
					  <option value="3">送红包</option>
                  </select>
				</div>
			</div>
            <script>
				function manSend(){
						var vs = $("#vsort").val();
						if(vs=="1"){
							$("#mansend").hide();
						}else if(vs=="2"||vs=="3"){
							$("#mansend").show();
						}
						
					}
			</script>
            <div class="row" id="mansend" style="display: none">
             <div style="margin-left: 30px;">
               <label>满额可送券</label>
                <input name="buysum" type="text" value="0" class="span3" id="buysum" datatype="n" nullmsg="满额可送券必须是数字">
              </div>
            </div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>开始日期</label>
					<div class="input-append date ">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="begindate" id="begindate" size="16" type="text" value="" readonly datatype="*" nullmsg="开始时间不能为空"/>
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
			                    <input name="enddate" size="16" type="text" value="" readonly datatype="*" nullmsg="开始时间不能为空"/>
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
                <input name="validday" type="text" class="span3" id="validday" datatype="n" nullmsg="有效期不能为空">
              </div>
            </div>
            
            <div class="row">
             <div style="margin-left: 30px;">
               <label>最低消费可用</label>
                <input name="lowlimit" type="text" class="span3" id="lowlimit" datatype="n" nullmsg="最低消费不能为空">
              </div>
            </div>
            <div class="row">
             <div style="margin-left: 30px;">
               <label>面值</label>
                <input name="vprice" type="text" class="span3" id="vprice" datatype="n" nullmsg="面值不能为空">
              </div>
            </div>
			<div class="row">
				<div style="margin-left: 30px;">启用 是:
				  <input name="isenable" type="radio" class="span1" value="1" checked="CHECKED"/>				
					否:<input type="radio" value="0" name="isenable" class="span1"/>				
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
	//window.ishide = 1;
	//提交表单
	
	form = $("form").Validform({
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
	
	$('.form_date,.form_time')
	.datetimepicker()
	.on('changeDate', function(ev){
		$(this).find('span').last().hide();
	});
   
    
</script>
</html>