<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String ak="F7611c68af149e79b454126156660ead";
application.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/bootstrap-responsive.min.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/bootstrap-timepicker.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/uploadify.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>/common/css/ljsedit.css">
<link href="${basePath }/common/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="<%=basePath%>/common/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/bootstrap-timepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/validform.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/laydate/laydate.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/jquery.iframe-post-form.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/common/js/cookie.js"></script>
<script type="text/javascript" src="${basePath}/common/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${basePath}/common/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
	window.basePath='<%=basePath%>';
	$(document).ajaxSuccess(function(event, request, settings) {
	    if (request.getResponseHeader('REQUIRES_AUTH') === '1') {
	       window.location = request.getResponseHeader('REQUIRES_AUTH_URL');
	    }
	});
	$.extend($.Datatype,{
		"phone" : /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/,
		"money" : /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/,
		"fax" : /^(\d{3,4})?(\-)?\d{7,8}$/,
		"score" : /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/
	});
</script>