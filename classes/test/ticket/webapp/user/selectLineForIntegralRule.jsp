<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/selectLine.js"></script>
    <title>选取线路</title>
</head>

<body>
<div class="container main_container">
    <form action="<%=basePath%>/user/selectLineForIntegralRule.do" method="get">
        <div class="row">
            <div class="pull-left" style="padding-right:10px;margin-left: 20px;">
                <input type="hidden" name="irid" value="${param.irid}">
                <input type="text" name="keyword" value="${param.keyword }"
                       placeholder="编号/名称/公司" style="height:30px;">
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
                <th>线路名称</th>
                <th>始发城市</th>
                <th>达到城市</th>
                <th>所属公司</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="line">
                <tr class="odd">
                    <td><input type="checkbox" class="check_single" value="${line.ID }"></td>
                    <td>${line.LineID }</td>
                    <td>${line.LineName }</td>
                    <td>${line.CityStartName }</td>
                    <td>${line.CityArriveName }</td>
                    <td>${line.TransCompany }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
</body>
</html>
