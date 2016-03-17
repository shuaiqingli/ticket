<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务器忙</title>
<%@ include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/header.jsp"%>
	<div class="container main_container">
<!-- 		<h1>500</h1> -->
		<h1>${empty msg ? '服务器忙，请稍后再试...' : msg}</h1>
	</div>
</body>
</html>