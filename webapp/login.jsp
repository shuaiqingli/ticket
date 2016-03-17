<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/login.css">
    <script type="text/javascript" src="<%=basePath%>/js/md5.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/login.js"></script>
    <title>登录</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="span4 offset4 well">
            <div style="text-align:center;">
                <img src="<%=basePath%>/images/login_web1.png"/>
            </div>
            <form id="loginForm" action="<%=basePath%>/public/login.do" method="post">
                <fieldset>
                    <label>
                        <sapn>手机号码或员工编号</sapn>
                        <input type="text" name="mobile" class="span4" value="" datatype="*" nullmsg="手机号码不能为空"
                               errormsg="手机号码格式错误"/>
                    </label>
                    <label>
                        <span>密码</span>
                        <input type="password" class="span4" value="" datatype="*" nullmsg="密码不能为空"/>
                        <input type="hidden" name="password" class="span4" datatype="*" nullmsg="****密码不能为空"/>
                    </label>
                    <label class="checkbox">
                        <input type="checkbox" class="rememberFlag" name="remember" value="1" style="height:20px;"/>
                        保存我的登录状态
                    </label>
                    <input type="submit" class="btn btn-primary" value="登陆"/>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    //{ expires: 7 }
    $(function () {
        if ($.cookie("mobile")) {
            $("[name=mobile]").val($.cookie("mobile"));
            $(".rememberFlag").prop("checked", true);
        }
// 	if($.cookie("password")){
// 		$("[name=password]").val($.cookie("password"))
// 		$(".rememberFlag").prop("checked",true);
// 	}
        $("form").submit(function () {
            if ($(".rememberFlag").prop("checked")) {
                $.cookie("mobile", $("[name=mobile]").val(), {expires: 7 * 9999});
            } else {
                $.cookie("mobile", null);
            }
        });
    });
</script>
</html>
