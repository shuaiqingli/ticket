<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectDriver.js"></script>
    <title>选取司机</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/user/selectDriver.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="hidden" name="groupid" value="${param.groupid}">
                <select name="type" class="span2" onchange="$(this).parents('form')[0].submit();">
                    <option value="1" <c:if test='${param.type == 1 }'>selected</c:if> >固定司机</option>
                    <option value="2" <c:if test='${param.type == 2 }'>selected</c:if> >机动司机</option>
                </select>
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="名称/手机" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th><input type="checkbox" class="check_all"></th>
                <th>编号</th>
                <th>名称</th>
                <th>手机号码</th>
                <th>身份证号码</th>
                <th>是否停用</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="driver">
                <tr class="odd">
                    <td><input type="checkbox" class="check_single" value="${driver.id }"></td>
                    <td>${driver.id }</td>
                    <td>${driver.dname }</td>
                    <td>${driver.dmobile }</td>
                    <td>${driver.idcard }</td>
                    <td>${driver.isstop == 1 ? '是':'否' }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
</body>
</html>
