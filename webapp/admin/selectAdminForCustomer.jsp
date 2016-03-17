<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectAdminForCustomer.js"></script>
    <title>选取管理员</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/admin/findAdminListForBindCustomer.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="编号/姓名/手机" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>编号</th>
                <th>姓名</th>
                <th>手机</th>
                <th>注册日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adminList }" var="admin">
                <tr class="odd">
                    <td>${admin.userID }</td>
                    <td>${admin.realName }</td>
                    <td>${admin.mobile }</td>
                    <td>${admin.makeDate }</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="javascript:void(0)" onclick="parent.selectAdmin('${admin.userID}')">
                                <i class="icon-pencil"></i> 绑定
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
</body>
</html>
