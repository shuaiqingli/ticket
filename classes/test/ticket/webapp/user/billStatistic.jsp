<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/billStatistic.js"></script>
    <title>对账统计</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/billStatistic.do" method="get">
        <div class="page-header">
            <h2>对账统计</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="startDate" value="${param.startDate}" placeholder="开始日期" style="height:30px;" >
                <input type="text" name="endDate" value="${param.endDate}" placeholder="结束日期" style="height:30px;" >
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="线路/站点,多个关键字以|分隔" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="exportExcel(this);" >导出EXCEL</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>出发日期</th>
                <th>线路</th>
                <th>站点</th>
                <th>原价金额</th>
                <th>原价数量</th>
                <th>优惠价金额</th>
                <th>优惠价数量</th>
                <th>总数量</th>
                <th>代金券总金额</th>
                <th>代金券数量</th>
                <th>实收总金额</th>
                <th>退票数量</th>
                <th>退票金额</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dataList }" var="data">
                <tr class="odd">
                    <td>${data.RideDate }</td>
                    <td>${data.LineName }(${fn:substring(data.ShiftNum,0,3) })</td>
                    <td>${data.STStartName }</td>
                    <td>¥${data.Price }</td>
                    <td>${data.PriceNum }</td>
                    <td>¥${data.VPrice }</td>
                    <td>${data.VPriceNum }</td>
                    <td>${data.PriceNumTotal }</td>
                    <td>¥${data.CouponSum }</td>
                    <td>${data.CouponTotal }</td>
                    <td>¥${data.ActualSum }</td>
                    <td>${data.RefundNum }</td>
                    <td>¥${data.RefundActualSum }</td>
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
