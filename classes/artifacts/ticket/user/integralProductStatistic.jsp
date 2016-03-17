<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/integralProductStatistic.js"></script>
    <title>兑换统计</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/integralProductStatistic.do" method="get">
        <div class="page-header">
            <h2>
                <a id="main_page" href="<%=basePath%>/admin/integralProductList.do" style="color: black"></a>
                兑换统计
            </h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="startDate" value="${param.startDate}" placeholder="开始日期" style="height:30px;" >
                <input type="text" name="endDate" value="${param.endDate}" placeholder="结束日期" style="height:30px;" >
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">统计</a>
                <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="javascript:void(0)" onclick="exportExcel(this);">导出EXCEL</a>
            </div>
        </div>

        <div class="panel-group" id="accordion">
            <div class="panel panel-default" style="border-bottom: 1px solid #ccc;">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="javascript:void(0);">兑换总数量：</a>
                        <span style="font-size: 14px;font-weight:600;"><span style="color:red;">${countTotal}</span>张</span>&nbsp;&nbsp;&nbsp;&nbsp;
                    </h4>
                </div>
            </div>
            <div class="panel panel-default" style="border-bottom: 1px solid #ccc;">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="javascript:void(0);">消耗总积分：</a>
                        <span style="font-size: 14px;font-weight:600;"><span style="color:red;">${integralTotal}</span>积分</span>&nbsp;&nbsp;&nbsp;&nbsp;
                    </h4>
                </div>
            </div>
            <div class="panel panel-default" style="border-bottom: 1px solid #ccc;">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a href="javascript:void(0);">参与用户数：</a>
                        <span style="font-size: 14px;font-weight:600;"><span style="color:red;">${customerTotal}</span>人</span>&nbsp;&nbsp;&nbsp;&nbsp;
                    </h4>
                </div>
            </div>
        </div>
        <br><br><br>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>面值</th>
                <th>数量</th>
                <th>积分值</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${valueMapList }" var="data">
                <tr class="odd">
                    <td>${data.ProductName }</td>
                    <td>${data.CountTotal }</td>
                    <td>${data.IntegralTotal }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br><br><br>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>消费者ID</th>
                <th>消费者名称</th>
                <th>数量</th>
                <th>积分值</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${customerMapList }" var="data">
                <tr class="odd">
                    <td>${data.CustomerID }</td>
                    <td>${data.CustomerName }</td>
                    <td>${data.CountTotal }</td>
                    <td>${data.IntegralTotal }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
