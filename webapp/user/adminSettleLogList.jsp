<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>结算日志</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/adminSettleLogList.do" method="get">
        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/user/adminSettleList.do" style="color: black"></a>
            <h2>结算日志</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="编号/名称" value="" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>站务编号</th>
                <th>站务名称</th>
                <th>订单数量</th>
                <th>车票数量</th>
                <th>结算金额</th>
                <th>结算人编号</th>
                <th>结算人名称</th>
                <th>结算日期</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${adminSettleLogList }" var="adminSettleLog">
                <tr class="odd">
                    <td>${adminSettleLog.userid }</td>
                    <td>${adminSettleLog.username }</td>
                    <td>${fn:length(fn:split(adminSettleLog.saleorders,'|')) }</td>
                    <td>${adminSettleLog.quantitytotal }</td>
                    <td>¥${adminSettleLog.pricetotal }</td>
                    <td>${adminSettleLog.operatorid }</td>
                    <td>${adminSettleLog.operatorname }</td>
                    <td>${fn:substring(adminSettleLog.makedate, 0, 16) }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>