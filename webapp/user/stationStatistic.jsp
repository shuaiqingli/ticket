<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/stationStatistic.js"></script>
    <title>站点管理</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/stationStatistic.do" method="get">
        <div class="page-header">
            <h2>站点管理</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <select name="stationid" class="span2">
                    <option value="">--请选择站点--</option>
                    <c:forEach items="${stationList}" var="station">
                        <option value="${station.ID}" <c:if test="${param.stationid == station.ID}">selected</c:if> >${station.CityName}</option>
                    </c:forEach>
                </select>
                <input type="text" name="startDate" value="${param.startDate}" placeholder="开始日期" style="height:30px;" >
                <input type="text" name="endDate" value="${param.endDate}" placeholder="结束日期" style="height:30px;" >
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="班次号" style="height:30px;">
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
                <th>班次号</th>
                <th>日期</th>
                <th>所属线路</th>
                <th>站点名称</th>
                <th>始发站</th>
                <th>终点站</th>
                <th>托运行李件数</th>
                <th>托运行李金额</th>
                <th>乘客超重行李件数</th>
                <th>乘客超重行李金额</th>
                <th>已卖票数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dataList }" var="data">
                <tr class="odd">
                    <td>${data.ShiftCode }</td>
                    <td>${data.RideDate } ${data.PunctualStart }</td>
                    <td>${data.LineName }</td>
                    <td>${data.CurrStationName }</td>
                    <td>${data.OriginSTName }</td>
                    <td>${data.STArriveName }</td>
                    <td>${data.ConsignQuantity }</td>
                    <td>${data.ConsignSum }</td>
                    <td>${data.PassengerQuantity }</td>
                    <td>${data.PassengerSum }</td>
                    <td>${data.Total }</td>
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
