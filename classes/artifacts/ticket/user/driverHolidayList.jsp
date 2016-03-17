<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/driverHolidayList.js"></script>
    <title>假期列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/drivermanage.do" style="color: black"></a>
            假期列表(${driver.dname})
        </h2>
    </div>
    <form action="<%=basePath%>/user/driverHolidayList.do" method="get">
        <input type="hidden" name="driverid" value="${driver.id}">

        <div class="row">
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/user/driverHoliday.do?driverid=${param.driverid}">
                    <i class="icon-plus-sign"></i>新增假期
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>类型</th>
                <th>假期时间</th>
                <th class="pull-right">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="driverHoliday">
                <tr class="odd">
                    <td>
                        <c:if test="${driverHoliday.type == '1'}">按星期休假</c:if>
                        <c:if test="${driverHoliday.type == '2'}">指定日期休假</c:if>
                    </td>
                    <td>
                        <c:if test="${driverHoliday.type == '1'}">
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,0,1)==1}'>星期一</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,1,2)==1}'>星期二</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,2,3)==1}'>星期三</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,3,4)==1}'>星期四</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,4,5)==1}'>星期五</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,5,6)==1}'>星期六</c:if>
                            <c:if test='${fn:substring(driverHoliday.weekdayStr,6,7)==1}'>星期日</c:if>
                        </c:if>
                        <c:if test="${driverHoliday.type == '2'}">
                            <c:choose>
                                <c:when test="${fn:substring(driverHoliday.startdate,0,10) == fn:substring(driverHoliday.enddate,0,10)}">
                                    ${fn:substring(driverHoliday.startdate,0,10)}
                                </c:when>
                                <c:otherwise>
                                    ${fn:substring(driverHoliday.startdate,0,10)}到${fn:substring(driverHoliday.enddate,0,10)}
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn"
                               href="<%=basePath%>/user/driverHoliday.do?driverid=${param.driverid}&id=${driverHoliday.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn btn-danger" href="javascript:void(0)"
                               onclick="delDriverHoliday(${driverHoliday.id});">
                                <i class="icon-remove"></i> 删除
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
