<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		编辑消息推送
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
				编辑消息推送
			</h2>
		</div>
		<form method="post" action="${basePath}/user/wxsubscribeEdit">
			<input type="hidden" name="id" value="${msg.id}"/>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>图片链接</label>
					<input type="text" name="picurl" value="${msg.picurl}" class="span5" placeholder="图片链接" datatype="*" nullmsg="图片链接不能为空"/>
				</div>
			</div>
			<br/>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>文章链接</label>
					<input type="text" name="url" value="${msg.url}"  class="span5" placeholder="文章链接" datatype="*" nullmsg="文章链接不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>标题</label>
					<input type="text" name="title" value="${msg.title}"  class="span5" placeholder="标题" datatype="*" nullmsg="标题不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>级别</label>
					<input type="text" name="rank" value="${msg.rank}"  class="span5" placeholder="级别" datatype="n" nullmsg="级别不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>描述</label>
					<textarea class="span5" rows="5" cols="30"  placeholder="描述" name="description" >${msg.description}</textarea>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>是否推送</label>
					<input type="radio" value="1" name="issend" <c:if test="${msg.issend ==1 or empty msg.issend}">checked</c:if> />是
					&nbsp;&nbsp;&nbsp;
					<input type="radio" value="0" name="issend" <c:if test="${msg.issend == 0}">checked</c:if> />否
				</div>
			</div>
			<br/>
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
	$(function(){
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
				if(data>=1){
					layer.msg('操作成功');
					location.href = basePath+"/user/wxsubscribeMsgList"
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