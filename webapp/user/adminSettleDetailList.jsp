<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <title>站务结算详情</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/adminSettleDetailList.do" method="get">
        <input type="hidden" name="userid" value="${param.userid}">

        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/user/adminSettleList.do" style="color: black"></a>

            <h2>站务结算详情(${admin.realName })</h2>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>订单号</th>
                <th>线路名称</th>
                <th>班次号</th>
                <th>站点信息</th>
                <th>发车时间</th>
                <th>顾客信息</th>
                <th>车票数量</th>
                <th>待结算金额</th>
                <th>收款时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dataList }" var="data">
                <tr class="odd">
                    <td>${data.ID }</td>
                    <td>${data.LineName }</td>
                    <td>${data.ShiftNum }</td>
                    <td>${data.STStartName }-${data.STArriveName}</td>
                    <td>${data.RideDate } ${data.StartTime}</td>
                    <td>${data.LName }(${data.LMobile})</td>
                    <td>${data.QuantityTotal }</td>
                    <td>¥${data.PriceTotal }</td>
                    <td>${fn:substring(data.ReceiveDate, 0, 16)}</td>
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
