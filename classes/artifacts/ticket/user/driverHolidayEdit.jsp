<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/driverHolidayEdit.js"></script>
    <title>
        <c:if test="${empty param.id}">
            新增假期
        </c:if>
        <c:if test="${not empty param.id}">
            编辑假期
        </c:if>
    </title>
    <script type="text/javascript">
        window.driverid = '${driver.id}';
    </script>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/user/driverHolidayList.do?driverid=${driver.id}"
               style="color: black"></a>
            <c:if test="${empty driverHoliday}">
                新增假期(${driver.dname})
            </c:if>
            <c:if test="${not empty driverHoliday}">
                编辑假期(${driver.dname})
            </c:if>
        </h2>
    </div>
    <form method="post" id="driverHolidayForm">
        <input type="hidden" name="id" value="${driverHoliday.id}">
        <input type="hidden" name="driverid" value="${driver.id}">

        <div class="row">
            <div style="margin-left: 30px;">
                <label>类型</label>
                <select class="span3" name="type" onchange="selectType();">
                    <option value="1"
                            <c:if test="${driverHoliday.type == 1}">selected</c:if> >按星期休假
                    </option>
                    <option value="2"
                            <c:if test="${driverHoliday.type == 2}">selected</c:if> >指定日期休假
                    </option>
                </select>
            </div>
        </div>
        <div class="row"
             <c:if test="${driverHoliday.type != 2}">style="display: none;" </c:if> >
            <div style="margin-left: 30px;">
                <label><span class="red">*</span> 开始时间</label>
                <input type="text" name="startdate" class="span3" readonly="readonly"
                       value="${fn:substring(driverHoliday.startdate,0,10)}"
                       datatype="*" nullmsg="开始时间不能为空">
            </div>
        </div>
        <div class="row"
             <c:if test="${driverHoliday.type != 2}">style="display: none;" </c:if> >
            <div style="margin-left: 30px;">
                <label><span class="red">*</span> 结束时间</label>
                <input type="text" name="enddate" class="span3" readonly="readonly"
                       value="${fn:substring(driverHoliday.enddate,0,10)}"
                       datatype="*" nullmsg="结束时间不能为空">
            </div>
        </div>
        <div class="row"
             <c:if test="${driverHoliday.type == 2}">style="display: none;" </c:if> >
            <div style="margin-left: 30px;">
                <input type="hidden" name="weekday" value="${driverHoliday.weekday }">
                <label>可选星期 <span style="color: gray;font-size: 12px;">(选中表示休假)</span></label>
                <label class="checkbox inline">
                    <input type="checkbox" value="1"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,0,1)==1}'>checked</c:if> >
                    <span>周一</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="2"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,1,2)==1}'>checked</c:if> >
                    <span>周二</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="4"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,2,3)==1}'>checked</c:if> >
                    <span>周三</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="8"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,3,4)==1}'>checked</c:if> >
                    <span>周四</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="16"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,4,5)==1}'>checked</c:if> >
                    <span>周五</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="32"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,5,6)==1}'>checked</c:if> >
                    <span>周六</span>
                </label>
                <label class="checkbox inline">
                    <input type="checkbox" value="64"
                           <c:if test='${fn:substring(driverHoliday.weekdayStr,6,7)==1}'>checked</c:if> >
                    <span>周日</span>
                </label>
            </div>
        </div>
        <div class="row">
            <div class="span2" style="margin-top:12px;">
                <input type="submit" class="btn" value="保存">
            </div>
        </div>
        <%@include file="../common/footer.jsp" %>
    </form>
</body>
</html>