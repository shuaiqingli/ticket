<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/showtimeList.js"></script>
    <title>线路提示列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/showTimeList.do" method="get">
        <div class="page-header">
            <h2>线路提示列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="内容/线路/管理员" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/user/showTimeEditPage.do">
                    <i class="icon-plus-sign"></i>新增线路提示
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>管理员</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>提示内容</th>
                <th>线路</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${showtimeList }" var="showtime">
                <tr class="odd">
                    <td>${showtime.makename }(${showtime.makeid})</td>
                    <td>${fn:substring(showtime.begindate, 0, 10) }</td>
                    <td>${fn:substring(showtime.enddate, 0, 10) }</td>
                    <td>
                        <span title="${showtime.showcontent}">
                            <c:choose>
                                <c:when test="${fn:length(showtime.showcontent) > 25}">${fn:substring(showtime.showcontent,0,25) }...</c:when>
                                <c:otherwise>${showtime.showcontent }</c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                    <td>
                        <c:forEach items="${fn:split(showtime.lines,'|')}" var="line" varStatus="status">
                            <a href="javascript:void(0)">${line}</a><c:if test="${!status.last }">|</c:if><c:if
                                test="${status.count%8==0 }"><br></c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="<%=basePath%>/user/showTimeEditPage.do?id=${showtime.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn btn-danger" href="javascript:void(0)" onclick="del(${showtime.id})">
                                <i class="icon-remove icon-white"></i> 删除
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

<%@include file="../common/footer.jsp" %>
</body>
</html>
