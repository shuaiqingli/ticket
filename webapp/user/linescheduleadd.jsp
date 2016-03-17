<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		<c:if test="${empty ls.id}">
			新增线路
		</c:if>
		<c:if test="${not empty ls.id}">
			编辑线路
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
				<a id="main_page" href="<%=basePath%>/user/lineschedule.do" style="color: black"></a>
				<c:if test="${empty ls.id}">
					新增排班
				</c:if>
				<c:if test="${not empty ls.id}">
					编辑排班
				</c:if>
			</h2>
		</div>
		<form method="post" id="adminForm" action="${basePath}/user/editlineschedule.do">
			<input type="hidden" name="id" value="${ls.id}">
			<div class="row">
				<a href="#myModal" role="button" class="btn span2" data-toggle="modal">选择线路</a>
			</div>
			<br/>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>线路编号</label>
					<input type="text" name="lmId" value="${ls.lmId}" class="span3 lmId" datatype="n" readonly="readonly" nullmsg="请选线路">				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>线路名称</label>
					<input type="text" class="span3 lineName" readonly="readonly" value="${ls.lineManage.lineName}" />				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>开始时间</label>
					<div class="input-append date form_datetime">
						<div class="control-group">
			                <div class="controls input-append date form_time">
			                    <input name="schedueTime" size="16" type="text" value="${ls.schedueTime }" readonly="readonly" datatype="*" nullmsg="开始时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>生效时间</label>
					<div class="input-append date ">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="begindate" size="16" type="text" value="${ls.begindate }" readonly="readonly" datatype="*" nullmsg="开始时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>失效时间</label>
					<div class="input-append date">
						<div class="control-group">
			                <div class="controls input-append date form_date">
			                    <input name="enddate" size="16" type="text" value="${ls.enddate }" readonly="readonly" datatype="*" nullmsg="开始时间不能为空"/>
			                    <span class="add-on"><i class="icon-remove"></i></span>
								<span class="add-on"><i class="icon-th"></i></span>
			                </div>
			            </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>班次数量</label>
					<input type="text" name="shiftNum" value="${ls.shiftNum }" class="span3" datatype="n" nullmsg="班次数量不能为空">
					<span>(次)</span>				
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>间隔时间</label>
					<input type="text" name="intervalMinute" value="${ls.intervalMinute }" class="span3" datatype="n" nullmsg="间隔时间不能为空">				
					<span>(分钟)</span>				
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
<!-- Modal -->
<div id="myModal"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 540px;">
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
				location.href = basePath+"/user/lineschedule.do"
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
		forceParse: 0
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
    
    $(function(){
		var lm = null;
    	$(".chooseLineManage").bind("click",function(){
    		lm = $(window.frames["lineManage"].document);
			var rdo = lm.find("[type=radio]:checked");
			var id = rdo.val();
			var name = rdo.parent().parent().find(".lineName").text();
			if(id&&name){
				$(".lmId").val(id);
				$(".lineName").val(name);
			}
    	});
    	
    });
    
</script>
</html>