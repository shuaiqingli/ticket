<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/driverStatistic.js"></script>
    <title>司机统计</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/driverStatistic.do" method="get">
        <div class="page-header">
            <h2>司机统计</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="startDate" value="${param.startDate}" placeholder="开始日期" style="height:30px;" readonly>
                <input type="text" name="endDate" value="${param.endDate}" placeholder="结束日期" style="height:30px;" readonly>
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="编号/名称/班次号/线路" style="height:30px;">
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
                <th>司机编号</th>
                <th>司机名称</th>
                <th>班次号</th>
                <th>所属线路</th>
                <th>日期</th>
                <th>人数</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dataList }" var="data">
                <tr class="odd">
                    <td>${data.CommonDriverID }</td>
                    <td>${data.CommonDriverName }</td>
                    <td>${data.ShiftCode }</td>
                    <td>${data.LineName }</td>
                    <td>${data.RideDate }</td>
                    <td>${data.PeopleTotal }</td>
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
