<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/prohibitTicketLineList.js"></script>
    <title>禁售行程</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/prohibitTicketLineList.do" method="get">
        <div class="page-header">
            <h2>禁售行程列表</h2>
        </div>
        <div class="row" style="margin-left: 5px;">
            <div class="pull-left" style="padding-right:10px;width: 100%;">
                <span>始发区域:</span>
                <select name="startCityID" class="span2" onchange="chooseCity(this);">
                    <option value="">--请选择城市--</option>
                    <c:forEach items="${cityList}" var="city">
                        <option value="${city.id}" <c:if test="${param.startCityID==city.id}">selected</c:if> >${city.cityname}</option>
                    </c:forEach>
                </select>
                <select name="startStationID" class="span2">
                    <option value="">--请选择站点--</option>
                    <c:forEach items="${startStationList}" var="station">
                        <option value="${station.id}" <c:if test="${param.startStationID==station.id}">selected</c:if> >${station.cityname}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="pull-left" style="padding-right:10px;width: 100%;">
                <span>到达区域:</span>
                <select name="arriveCityID" class="span2" onchange="chooseCity(this);">
                    <option value="">--请选择城市--</option>
                    <c:forEach items="${cityList}" var="city">
                        <option value="${city.id}" <c:if test="${param.arriveCityID==city.id}">selected</c:if> >${city.cityname}</option>
                    </c:forEach>
                </select>
                <select name="arriveStationID" class="span2">
                    <option value="">--请选择站点--</option>
                    <c:forEach items="${arriveStationList}" var="station">
                        <option value="${station.id}" <c:if test="${param.arriveStationID==station.id}">selected</c:if> >${station.cityname}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="pull-left" style="padding-right:10px;width: 100%;">
                <span>起止日期:</span>
                <input type="text" id="beginDate" name="beginDate" value="${param.beginDate}" placeholder="开始日期" style="height:30px;">
                <input type="text" id="endDate" name="endDate" value="${param.endDate}" placeholder="结束日期" style="height:30px;">
            </div>
            <div class="pull-left" style="padding-right:10px;">
                <span>起止时间:</span>
                <input type="text" id="startTime" name="startTime" value="${param.startTime}" placeholder="开始时间" style="height:30px;">
                <input type="text" id="endTime" name="endTime" value="${param.endTime}" placeholder="结束时间" style="height:30px;">
            </div>
            <div class="pull-left" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="线路,多个关键字以|分隔" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0);" onclick="openTicketLineList();">批量解禁</a>
            </div>
        </div><br>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th><input type="checkbox" class="check_all"></th>
                <th>线路</th>
                <th>日期</th>
                <th>班次</th>
                <th>行程</th>
                <th>出发时间</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ticketLineList }" var="ticketLine">
                <tr class="odd">
                    <td><input type="checkbox" class="check_single" value="${ticketLine.ID}"></td>
                    <td>${ticketLine.LineName}(${fn:substring(ticketLine.ShiftCode,0,3)})</td>
                    <td>${ticketLine.RideDate }</td>
                    <td>${ticketLine.ShiftCode }</td>
                    <td>${ticketLine.STStartName }——${ticketLine.STArriveName }</td>
                    <td>${ticketLine.StartTime }</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="javascript:void(0)" onclick="openTicketLineList(this);">
                                <i class="icon-pencil"></i> 解禁
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
