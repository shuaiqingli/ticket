<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		上传APP
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
				上传APP
			</h2>
		</div>
		<form method="post" action="${basePath}/user/appupload" enctype="multipart/form-data">
			<div class="row">
				<div style="margin-left: 30px;">
					<label>APP类型</label>
					<select name="sort">
						<option>站务APP</option>
						<option>客户端APP</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>文件</label>
					<input type="file" name="app" class="span5" placeholder="文件" datatype="*" nullmsg="文件不能为空"/>
				</div>
			</div>
			<br/>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>版本号</label>
					<input type="text" name="versionCode" class="span5" placeholder="版本号" datatype="n" nullmsg="版本号不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>下载路径(可选)</label>
					<input type="text" name="fileUrl" class="span5" placeholder="下载路径" datatype="n" nullmsg="下载路径不能为空"/>
				</div>
			</div>
			<div class="row">
				<div style="margin-left: 30px;">
					<label>描述</label>
					<textarea rows="0" cols="30"  placeholder="描述" name="softDesc" style="width: 100%;"></textarea>
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
	$(function(){
		$('form').submit(function(){
			if($('[name=app]').val()==''){
				layer.msg("请选择上传文件！")
				return false;
			}
			if($('[name=versionCode]').val()==''){
				layer.msg("版本号不能为空！")
				return false;
			}
			return true;
		});
	});
</script>
</html>