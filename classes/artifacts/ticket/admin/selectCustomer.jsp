<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectCustomer.js"></script>
    <title>选取顾客</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/admin/selectCustomer.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="手机/名称" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>名称</th>
                <th>手机</th>
                <th>级别</th>
                <th>注册日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${customerList }" var="customer">
                <tr class="odd">
                    <td>${customer.cname }</td>
                    <td>${customer.mobile }</td>
                    <td>
                        <c:if test="${customer.rank==0}">普通</c:if>
                        <c:if test="${customer.rank==1}">高级</c:if>
                        <c:if test="${customer.rank==2}">站务</c:if>
                    </td>
                    <td>${customer.makeDate }</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="javascript:void(0)" onclick="parent.selectCustomer('${customer.cid}', '${customer.mobile}')">
                                <i class="icon-pencil"></i> 选择
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
