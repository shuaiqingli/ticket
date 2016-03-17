<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/prohibitList.js"></script>
    <title>禁售规则列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/prohibitList.do" method="get">
        <input type="hidden" name="stid" value="${param.stid}">

        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/admin/citystationlist.do?parentID=${station.parentid}&isStation=1&ishot=1" style="color: black;border: none;"></a>
            <h2>禁售规则列表(${station.cityname})</h2>
        </div>
        <div class="row">
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/admin/prohibitEditPage.do?stid=${param.stid}">
                    <i class="icon-plus-sign"></i>新增禁售规则
                </a>
                <a class="btn" href="javascript:void(0)" onclick="apply();">
                    <i class="icon-plus-sign"></i>立即生效
                </a>
                <a class="btn" href="<%=basePath%>/admin/prohibitTicketLineList.do">
                    <i class="icon-plus-sign"></i>禁售行程列表
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>可选星期</th>
                <th>创建日期</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${prohibitList }" var="prohibit">
                <tr class="odd">
                    <td>${fn:substring(prohibit.begindate,0,10)}</td>
                    <td>${fn:substring(prohibit.enddate,0,10)}</td>
                    <td>
                        <c:if test='${fn:substring(prohibit.weekdayStr,0,1)==1}'>星期一</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,1,2)==1}'>星期二</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,2,3)==1}'>星期三</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,3,4)==1}'>星期四</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,4,5)==1}'>星期五</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,5,6)==1}'>星期六</c:if>
                        <c:if test='${fn:substring(prohibit.weekdayStr,6,7)==1}'>星期日</c:if>
                    </td>
                    <td>${prohibit.makedate}</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn"
                               href="<%=basePath%>/admin/prohibitEditPage.do?stid=${param.stid}&id=${prohibit.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn" href="javascript:void(0);" onclick="apply(${prohibit.id})">
                                <i class="icon-pencil"></i> 立即生效
                            </a>
                            <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${prohibit.id})">
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
